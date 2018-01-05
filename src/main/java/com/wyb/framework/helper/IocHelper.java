package com.wyb.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import com.wyb.framework.annotation.Inject;
import com.wyb.framework.util.ArrayUtil;
import com.wyb.framework.util.CollectionUtil;
import com.wyb.framework.util.ReflectionUtil;

/**
 * 依赖注入助手类
 * @author Administrator
 *
 */
public final class IocHelper {
	
	static {
		Map<Class<?>,Object> beanMap=BeanHelper.getBeanMAP();
		if(CollectionUtil.isNotEmpty(beanMap)){
			for(Map.Entry<Class<?>, Object> beanEntry:beanMap.entrySet()){
				Class<?> beanClass=beanEntry.getKey();
				Object beanInstance=beanEntry.getValue();
				
				Field []beanFields=beanClass.getDeclaredFields();
				if(ArrayUtil.isNotEmpty(beanFields)){
					for(Field beanfield:beanFields){
						if(beanfield.isAnnotationPresent(Inject.class)){
							Class<?> beanFieldClass=beanfield.getType();//获取变量的类名
							Object beanFieldInstance=beanMap.get(beanFieldClass);
							if(beanFieldInstance!=null){
								//通过反射初始化beanField值，将已实例化的bean注入到成员变量
								ReflectionUtil.setField(beanClass, beanfield, beanFieldInstance);
							}
						}
					}
				}
			}
			
		}
		
		
	}

}
