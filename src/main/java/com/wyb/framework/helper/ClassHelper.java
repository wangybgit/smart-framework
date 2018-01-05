package com.wyb.framework.helper;

import java.util.HashSet;
import java.util.Set;

import com.wyb.framework.annotation.Controller;
import com.wyb.framework.annotation.Service;
import com.wyb.framework.util.ClassUtil;

/**
 * 类操作助手类，获取所有类和各注解下的类
 * @author Administrator
 *
 */
public class ClassHelper {
	
	/*
	 * 定义集合类，用户存放所加载的类
	 */
	private static final Set<Class<?>> CLASS_SET;
	static{
		String base_package=ConfigHelper.getAppBasePackage();
		CLASS_SET=ClassUtil.getClassSet(base_package);
	}
	
	/**
	 * 获取应用包名下所有的类
	 * @return
	 */
	private static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取应用包名下所有Service类
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET){
			if(cls.isAnnotationPresent(Service.class)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包名下所有Controller类
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET){
			if(cls.isAnnotationPresent(Controller.class)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包下所有的类（包括Service类和Controller类等）
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> beanClassSet=new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
	
	
}
