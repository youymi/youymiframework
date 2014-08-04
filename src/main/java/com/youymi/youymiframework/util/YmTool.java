package com.youymi.youymiframework.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class YmTool {
	
	/**
	 * 将对象的属性转成多行显示输出字符串
	 * @param o 需要转换的对象
	 * @return 字符串
	 */
	public static String toString(Object o) {
		return ToStringBuilder.reflectionToString(o, ToStringStyle.MULTI_LINE_STYLE);
	}

}
