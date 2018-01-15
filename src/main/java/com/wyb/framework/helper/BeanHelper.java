package com.wyb.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wyb.framework.util.ReflectionUtil;

/**
 * bean助手类
 * @author Administrator
 *
 */
public class BeanHelper {

	private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>,Object>();
	
	static{
		Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();//获取基础包下的所有类
		for(Class<?> beanClass:beanClassSet){
			Object obj=ReflectionUtil.newInstance(beanClass);//将所有包下的类实例化成bean
			BEAN_MAP.put(beanClass, obj);
		}
	}
	
	/**
	 * 获取所有bean
	 * @return
	 */
	public static Map<Class<?>,Object> getBeanMAP(){
		return BEAN_MAP;
	}
	
	/**
	 * 获取对应Class的bean实例
	 * @param cls
	 * @return
	 */
	public static <T>T getBean(Class<?> cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by Class:"+cls);
		}
		return (T)BEAN_MAP.get(cls);
	}
	
	/**
	 * 设置bean实例
	 * @param cls
	 * @param object
	 */
	public static void setBean(Class<?> cls,Object object){
		BEAN_MAP.put(cls, object);
	}
}
