// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import corner.service.EntityService;
import corner.util.ognl.OgnlUtil;

/**
 * 设定bean的常用函数，主要采用了Ognl。
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 */
public class BeanUtils {
	static {

		// only for opensymphony oscore
		System.setProperty("bean.provider", "ognl");

		// only form apache beanutils
		// Converter igConverter = new IntegerConverter(null);
		// ConvertUtils.register(igConverter, Integer.TYPE); // Native type
		// ConvertUtils.register(igConverter, Integer.class);
		//
		// Converter dateConverter= new DateConverter();
		// ConvertUtils.register(dateConverter,Timestamp.class);
		// ConvertUtils.register(dateConverter, Date.class);

	}

	/**
	 * 获得对象中的可用的get方面名称集合
	 * @param bean 处理的对象
	 */
	public static List getPropertyMethodNames(Object bean){
		
		Class ObjectClass = EntityService.getEntityClass(bean);
		
		List<String> propertys = new ArrayList<String>();
		
		PropertyDescriptor[] pds = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(ObjectClass);
		
		for(PropertyDescriptor pd : pds){
			propertys.add(pd.getDisplayName());
		}
		
		return propertys;
	}
	
	/**
	 * 设定属性
	 * 
	 * @param bean
	 *            bean
	 * @param pro
	 *            属性名称
	 * @param value
	 *            值
	 */
	public static void setProperty(Object bean, String pro, Object value) {
		OgnlUtil.setProperty(bean, pro, value);
		// com.opensymphony.util.BeanUtils.setValue(bean, pro, value);
		// if (value == null)
		// return;
		// try {
		// org.apache.commons.beanutils.BeanUtils.setProperty(
		// bean,
		// pro,
		// value);
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 得到bean的属性值。
	 * 
	 * @param bean
	 *            bean.
	 * @param pro
	 *            属性的名称.
	 * @return 属性值.
	 */
	public static Object getProperty(Object bean, String pro) {

		if (Character.isUpperCase(pro.charAt(0))) {
			pro = Character.toLowerCase(pro.charAt(0)) + pro.substring(1);
		}
		try {
			return org.apache.commons.beanutils.PropertyUtils.getProperty(bean,
					pro);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 复制一个orig的属性到dest中.
	 * 
	 * @param dest
	 *            需要赋值的bean.
	 * @param orig
	 *            原始bean.
	 */
	public static void setProperties(Object dest, Object orig) {
		OgnlUtil.copy(orig, dest, null);
		// com.opensymphony.util.BeanUtils.setValues(dest, orig,null);
		// try {
		// org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 从map中得到值进行赋值给bean.
	 * 
	 * @param bean
	 *            需要赋值的bean.
	 * @param map
	 *            包含值的map.
	 */
	public static void setProperties(Object bean, Map map) {

		OgnlUtil.setProperties(map, bean);

		// com.opensymphony.util.BeanUtils.setValues(bean, map, null);
		// try {
		// org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 实例化一个类.
	 * 
	 * @param clazz
	 *            类的名称.
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

	/**
	 * 实例化一个类.
	 * 
	 * @param clazz
	 *            类的名称.
	 * @return 实例化的类.
	 */
	public static <T> T instantiateClass(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
