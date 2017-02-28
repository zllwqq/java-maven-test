package com.zlstudy.utils;

import com.google.gson.Gson;

public class JsonUtils {

	private static Gson gson = null;
	/**
	 * 通过单例获取gson
	 * @return
	 */
	public static Gson getGson(){
		if(gson==null){
			return new Gson();
		}
		return gson;
	}

	/**
	 * 把java对象转换为JSON
	 * @param obj 要转换的对象
	 * @return
	 * @throws IOException 
	 */
	public static String toJson(Object obj){
		return getGson().toJson(obj);
	}
	
	/** 
    * json字符串转成对象 
    * @param str   
    * @param type  
    * @return  
    */  
   public static <T> T fromJson(String str, Class<T> type) {  
       return getGson().fromJson(str, type);  
   }
	
	
}
