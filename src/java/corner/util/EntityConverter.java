//==============================================================================
// file :       EntityConverter.java
// project:     corner-utils
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision: 35 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================
package corner.util;

/**
 * 转换表的一些常用函数.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision: 35 $
 */
public class EntityConverter {
	/**
	 * 转换表的名称.
	 * @param name 需要转换的值.
	 * @param wimpyCaps 是否需要首字母大写,如果为false,首字母大写,否则小写.
	 * @return 转换后的结果.
	 */
	public static String convertName(String name, boolean wimpyCaps) {
		StringBuffer buffer = new StringBuffer(name.length());
		char[] list = name.toLowerCase().toCharArray();

		for (int i = 0; i < list.length; i++) {
			if ((i == 0) && !wimpyCaps) {
				buffer.append(Character.toUpperCase(list[i]));
			} else if (
				(list[i] == '_') && ((i + 1) < list.length) && (i != 0)) {
				buffer.append(Character.toUpperCase(list[++i]));
			} else {
				buffer.append(list[i]);
			}
		}

		return buffer.toString();
	}
}
