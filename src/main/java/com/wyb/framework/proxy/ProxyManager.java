package com.wyb.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理管理器
 * @author Administrator
 *
 */
public class ProxyManager {
	
    @SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
        	
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, 
            		MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,methodParams,proxyList).doProxyChain();
            }
            
        });
    }
}