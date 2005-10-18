//==============================================================================
//file :        BeanUtils.java
//project:      corner-utils
//
//last change:	date:       $Date: 2005-06-15 16:52:07 +0800 (Wed, 15 Jun 2005) $
//           	by:         $Author: jcai $
//           	revision:   $Revision: 35 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.util.Map;

import corner.util.ognl.OgnlUtil;



/**
 * 设定bean的常用函数，主要采用了Ognl。
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision: 35 $
 */
public class BeanUtils {
	static {

		//only for opensymphony oscore
		System.setProperty("bean.provider", "ognl");

		//only form apache beanutils
		//		Converter igConverter = new IntegerConverter(null); 
		//		ConvertUtils.register(igConverter, Integer.TYPE); // Native type
		//		ConvertUtils.register(igConverter, Integer.class);
		//		
		//		Converter dateConverter= new DateConverter();
		//		ConvertUtils.register(dateConverter,Timestamp.class);
		//		ConvertUtils.register(dateConverter, Date.class);

	}
	/**
	 * 设定属性
	 * @param bean bean
	 * @param pro 属性名称
	 * @param value 值
	 */
	public static void setProperty(Object bean, String pro, Object value) {
		OgnlUtil.setProperty(bean, pro, value);
		//				com.opensymphony.util.BeanUtils.setValue(bean, pro, value);
		//		if (value == null)
		//			return;
		//		try {
		//			org.apache.commons.beanutils.BeanUtils.setProperty(
		//				bean,
		//				pro,
		//				value);
		//		} catch (IllegalAccessException e) {
		//			e.printStackTrace();
		//		} catch (InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 的奥bean的属性值。
	 * @param bean bean.
	 * @param pro 属性的名称.
	 * @return 属性值.
	 */
	public static Object getProperty(Object bean, String pro) {

				try {
					return org.apache.commons.beanutils.BeanUtils.getProperty(
						bean,
						pro);
				} catch (Exception e) {
					return null;
				}
	}

	/**
	 * 复制一个orig的属性到dest中.
	 * @param dest 需要赋值的bean.
	 * @param orig 原始bean.
	 */
	public static void setProperties(Object dest, Object orig) {
		OgnlUtil.copy(orig, dest, null);
		//		com.opensymphony.util.BeanUtils.setValues(dest, orig,null);
		//		try {
		//			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		//		} catch (IllegalAccessException e) {
		//			e.printStackTrace();
		//		} catch (InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
	}

	

	/**
	 * 从map中得到值进行赋值给bean.
	 * @param bean 需要赋值的bean.
	 * @param map 包含值的map.
	 */
	public static void setProperties(Object bean, Map map) {

		
		OgnlUtil.setProperties(map, bean);

		//		com.opensymphony.util.BeanUtils.setValues(bean, map, null);
		//		try {
		//			org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		//		} catch (IllegalAccessException e) {
		//			e.printStackTrace();
		//		} catch (InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 实例化一个类.
	 * @param clazz 类的名称.
	 * @return 实例化的类.
	 */
	public static Object instantiateClass(String clazz) {
		try {
			return Class.forName(clazz).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}

