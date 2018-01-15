package com.wyb.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理链
 * @author Administrator
 *
 */
public class ProxyChain {
    private final Class<?> targetClass;//目标类
    private final Object targetObject;//目标对象
    private final Method targetMethod;//目标方法
    private final MethodProxy methodProxy;//方法代理
    private final Object[] methodParams;
    private List<Proxy> proxyList = new ArrayList<Proxy>();
    private int proxyIndex = 0;//代理对象计数器

    /**
     * 构造函数
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodProxy
     * @param methodParams
     * @param proxyList
     */
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, 
    		MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }
    
    public Object doProxyChain()throws Throwable{
        Object methodResult;
        if (proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);//执行doProxy()后会再跳转到doProxyChain()
            														 //方法,进行下一个切面代理的处理
        }else {
        	//所有的切面代理处理完成后，进入实际的方法
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}
