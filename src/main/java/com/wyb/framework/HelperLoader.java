package com.wyb.framework;


import com.wyb.framework.helper.BeanHelper;
import com.wyb.framework.helper.ClassHelper;
import com.wyb.framework.helper.ControllerHelper;
import com.wyb.framework.helper.IocHelper;
import com.wyb.framework.util.ClassUtil;

public final class HelperLoader {
	
	public static void init(){
		Class<?> []classList={
			ClassHelper.class,
			BeanHelper.class,
			IocHelper.class,
			ControllerHelper.class
		};
		
		for(Class<?> cls:classList){
			ClassUtil.loadClass(cls.getName(),true);//需要加载静态块，原书中没有true？？？
		}
	}
}
