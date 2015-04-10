package com.youymi.youymiframework.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class HttpServletProxy implements InvocationHandler {

	private Object target;
	
	private Map<String, String[]> params =  null;
	
	
	public static HttpServletRequest newProxyInstance(HttpServletRequest req){
		HttpServletProxy p = new HttpServletProxy();
		return (HttpServletRequest)p.bind(req);
		
		
	}

	/**
	 * 绑定委托对象并返回一个代理类
	 * 
	 * @param target
	 * @return
	 */
	public Object bind(Object target) {
		this.target = target;
		
		//
		initRequest((HttpServletRequest)target);
		
		// 取得代理对象
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this); // 要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object result = null;
	 
		
		
		switch(method.getName()) {
		case "getParameter" :
		case "getParameterMap" :
		case "getParameterValues" :
			//System.out.println(method.getName());
			Method thisMethod =	this.getClass().getMethod(method.getName(), method.getParameterTypes());
		return result = thisMethod.invoke(this, args);
		 
		}
		
		// 执行方法
		result = method.invoke(target, args);
	 
		return result;
	}
	
	    public String getParameter(String name) {
	        String result = "";
	         
	        Object v = params.get(name);
	        if (v == null) {
	            result = null;
	        } else if (v instanceof String[]) {
	            String[] strArr = (String[]) v;
	            if (strArr.length > 0) {
	                result =  strArr[0];
	            } else {
	                result = null;
	            }
	        } else if (v instanceof String) {
	            result = (String) v;
	        } else {
	            result =  v.toString();
	        }
	         
	        return result;
	    }
	 
 
	    public Map<String, String[]> getParameterMap() {
	        return params;
	    }
	 
 
	    public Enumeration<String> getParameterNames() {
	        return new Vector<String>(params.keySet()).elements();
	    }
	 
	 
	    public String[] getParameterValues(String name) {
	        String[] result = null;
	         
	        Object v = params.get(name);
	        if (v == null) {
	            result =  null;
	        } else if (v instanceof String[]) {
	            result =  (String[]) v;
	        } else if (v instanceof String) {
	            result =  new String[] { (String) v };
	        } else {
	            result =  new String[] { v.toString() };
	        }
	         
	        return result;
	    }
	    
	
	   private void initRequest(HttpServletRequest req) {
		   if(params == null) {
			   params = new HashMap<String,String[]>(req.getParameterMap());  
		   }
	        String queryString = req.getQueryString();
	 
	        if (queryString != null && queryString.trim().length() > 0) {
	            String[] params = queryString.split("&");
	 
	            for (int i = 0; i < params.length; i++) {
	                int splitIndex = params[i].indexOf("=");
	                if (splitIndex == -1) {
	                    continue;
	                }
	                 
	                String key = params[i].substring(0, splitIndex);
	 
	                if (!this.params.containsKey(key)) {
	                    if (splitIndex < params[i].length()) {
	                        String value = params[i].substring(splitIndex + 1);
	                        this.params.put(key, new String[] { value });
	                    }
	                }
	            }
	        }
	    }

}
