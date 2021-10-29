package com.test.base.util;

import cn.hutool.core.util.StrUtil;

public class StringUtil {
	
	private StringUtil() {
		
	}
	
	/**
	 * 删除前缀所有的0
	 * @param str
	 * @return
	 */
	public static String delPrefixZero(String str) {
		if (StrUtil.isBlank(str)) {
			return null;
		}
		return str.replaceFirst("^0*", "");
	}

}
