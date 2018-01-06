package com.wyb.framework.util;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {
    /*
    * 判断字符串是否为空
    * */
    public static boolean isEmpty(String str){
        if(str != null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    /*
    * 判断字符串是否非空
    * */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    
    /**
     * 分割字符串
     * @param str
     * @return
     */
    public static String[] splitString(String str,String s) {
        if (isNotEmpty(str)){
            return StringUtils.split(str,s);
        }
        return null;
    }

 }
