package com.wyb.framework.bean;

import java.lang.reflect.Method;

/**
 * 处理器Action
 * @author Administrator
 *
 */
public class Handler {
	
	/**
	 * Controller类
	 */
	private Class<?> controllerClass;
	
	/**
	 * Action方法
	 */
	private Method actionMethod;
	
	public Handler(Class<?> controllerClass,Method actionMethod){
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

}
