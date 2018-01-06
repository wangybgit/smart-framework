package com.wyb.framework.bean;

import java.util.Map;

import com.wyb.framework.util.CastUtil;

public class Param {
	
	private Map<String,Object> paramMap;
	
	public Param(Map<String,Object> map){
		this.paramMap=map;
	}
	
	/**
	 * 根据参数名获取long型参数值
	 * @param name
	 * @return
	 */
	public long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}
	
	public Map<String,Object> getMap(){
		return paramMap;
	}
}
