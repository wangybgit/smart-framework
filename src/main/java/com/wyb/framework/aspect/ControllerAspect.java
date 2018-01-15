package com.wyb.framework.aspect;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyb.framework.proxy.AspectProxy;

public class ControllerAspect extends AspectProxy{

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOG.debug("------------begin--------------");
        LOG.debug(String.format("class: %s",cls.getName()));
        LOG.debug(String.format("method: %s",method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOG.debug(String.format("time: %s",System.currentTimeMillis()-begin));
        LOG.debug("------------end--------------");
    }
}
