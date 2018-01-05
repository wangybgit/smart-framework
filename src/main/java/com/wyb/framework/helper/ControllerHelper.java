package com.wyb.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wyb.framework.annotation.Action;
import com.wyb.framework.bean.Handler;
import com.wyb.framework.bean.Request;
import com.wyb.framework.util.ArrayUtil;
import com.wyb.framework.util.CollectionUtil;

/**
 * 控制器助手类
 * @author Administrator
 *
 */
public final class ControllerHelper {
	
	/**
	 * 用于存储请求与处理器的映射关系
	 */
	private static Map<Request,Handler> ACTION_MAP=new HashMap<Request,Handler>();
	
	static {
		//获取所有的Controller类
		Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			//遍历Controller类
			for(Class<?> controllerClass:controllerClassSet){
				//获取Controller类下的所有方法
				Method []methods=controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					for(Method method:methods){
						//判断方法是否标记了注解@Action
						if(method.isAnnotationPresent(Action.class)){
							//从Action注解中获取URL映射规则
							Action action=method.getAnnotation(Action.class);
							String mapping=action.value();
							if(mapping.matches("\\w+:/\\w*")){
								String []array=mapping.split(":");
								if((ArrayUtil.isNotEmpty(array))&&(array.length==2)){
									//获取请求方法与请求路径
									String requestMethod=array[0];
									String requestPath=array[1];
									Request request=new Request(requestMethod,requestPath);
									Handler handler=new Handler(controllerClass,method);
									//将该请求与处理器加入ACTION_MAP中
									ACTION_MAP.put(request, handler);
									
								}
							}
						}
					}
				}
			}
		}
	}
	
	//根据请求方法和路径获取Handler
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request=new Request(requestMethod,requestPath);
		return ACTION_MAP.get(request);
	}
}
