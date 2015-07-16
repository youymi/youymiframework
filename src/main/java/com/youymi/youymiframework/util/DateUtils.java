package com.youymi.youymiframework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final String FORMAT_YYYMMMDD = "yyyyMMdd";
	
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	
	public static String format(Date date){
		if (date ==  null) {
			return null;
		}
		return formater.format(date);
	}
	public static String format(Date date,String format){
		if (date ==  null || format == null) {
			return null;
		}
		
		return new SimpleDateFormat(format).format(date);
	}
	
	
	
	
}
