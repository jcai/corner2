/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package corner.util.ognl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class that provides common access to the Ognl APIs for
 * setting and getting properties from objects (usually Actions).
 * 针对ognl的特别处理
 * @author $Author$
 * @version $Revision$
 */
public class OgnlUtil {
	//~ Static fields/initializers /////////////////////////////////////////////

	private static final Log log = LogFactory.getLog(OgnlUtil.class);
	private static HashMap expressions = new HashMap();

	//~ Methods ////////////////////////////////////////////////////////////////

	/**
	 * Sets the object's properties using the default type converter, defaulting to not throw exceptions for problems setting the properties.
	 *
	 * @param props the properties being set
	 * @param o the object
	 * @param context the action context
	 */
	public static void setProperties(Map props, Object o, Map context) {
		setProperties(props, o, context, false);
	}

	/**
	 * Sets the object's properties using the default type converter.
	 *
	 * @param props the properties being set
	 * @param o the object
	 * @param context the action context
	 * @param throwPropertyExceptions boolean which tells whether it should throw exceptions for problems setting the properties
	 */
	public static void setProperties(
		Map props,
		Object o,
		Map context,
		boolean throwPropertyExceptions) {
		if (props == null) {
			return;
		}

		

		Object oldRoot = Ognl.getRoot(context);
		Ognl.setRoot(context, o);

		for (Iterator iterator = props.entrySet().iterator();
			iterator.hasNext();
			) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String expression = (String) entry.getKey();

			internalSetProperty(
				expression,
				entry.getValue(),
				o,
				context,
				throwPropertyExceptions);
		}

		Ognl.setRoot(context, oldRoot);
	}

	/**
	 * Sets the properties on the object using the default context, defaulting to not throwing exceptions for problems setting the properties.
	 * @param properties
	 * @param o
	 */
	public static void setProperties(Map properties, Object o) {
		setProperties(properties, o, false);
	}

	/**
	 * Sets the properties on the object using the default context
	 * @param properties the property map to set on the object
	 * @param o the object to set the properties into
	 * @param throwPropertyExceptions boolean which tells whether it should throw exceptions for problems setting the properties
	 */
	public static void setProperties(
		Map properties,
		Object o,
		boolean throwPropertyExceptions) {
		Map context = Ognl.createDefaultContext(o);
		setProperties(properties, o, context, throwPropertyExceptions);
	}

	/**
	 * Sets the named property to the supplied value on the Object, defaults to not throwing property exceptions
	 * @param name the name of the property to be set
	 * @param value the value to set into the named property
	 * @param o the object upon which to set the property
	 * @param context the context which may include the TypeConverter
	 */
	public static void setProperty(
		String name,
		Object value,
		Object o,
		Map context) {
		setProperty(name, value, o, context, true);
	}
	/**
	 * 设定bean的属性.
	 * @param bean 需要设定的bean
	 * @param pro 属性名称
	 * @param value 设定的值.
	 * @author Jun Tsai
	 */
	public static void setProperty(Object bean, String pro, Object value) {
		Map context = Ognl.createDefaultContext(bean);
		setProperty(pro, value, bean, context);
	}

	/**
	 * Sets the named property to the supplied value on the Object
	 * @param name the name of the property to be set
	 * @param value the value to set into the named property
	 * @param o the object upon which to set the property
	 * @param context the context which may include the TypeConverter
	 * @param throwPropertyExceptions boolean which tells whether it should throw exceptions for problems setting the property
	 */
	public static void setProperty(
		String name,
		Object value,
		Object o,
		Map context,
		boolean throwPropertyExceptions) {
		

		Object oldRoot = Ognl.getRoot(context);
		Ognl.setRoot(context, o);

		internalSetProperty(name, value, o, context, throwPropertyExceptions);

		Ognl.setRoot(context, oldRoot);
	}

	@SuppressWarnings("unchecked")
	public static Object compile(String expression) throws OgnlException {
		Object o = null;

		o = expressions.get(expression);

		if (o == null) {
			o = Ognl.parseExpression(expression);
			expressions.put(expression, o);
		}

		return o;
	}

	/**
	 * Copies the properties in the object "from" and sets them in the object "to"
	 * using specified type converter, or {@link com.opensymphony.xwork.util.CornerTypeConverter} if none is specified.
	 *
	 * @param from the source object
	 * @param to the target object
	 * @param context the action context we're running under
	 */
	public static void copy(Object from, Object to, Map context) {
		Map contextFrom = Ognl.createDefaultContext(from);
		

		Map contextTo = Ognl.createDefaultContext(to);
		

		BeanInfo beanInfoFrom = null;

		try {
			beanInfoFrom =
				Introspector.getBeanInfo(from.getClass(), Object.class);
		} catch (IntrospectionException e) {
			log.error("An error occured", e);

			return;
		}

		PropertyDescriptor[] pds = beanInfoFrom.getPropertyDescriptors();

		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor pd = pds[i];

			try {
				Object expr = compile(pd.getName());
				Object value = Ognl.getValue(expr, contextFrom, from);
				Ognl.setValue(expr, contextTo, to, value);
			} catch (OgnlException e) {
				// ignore, this is OK
			}
		} 
	}
	

	static void internalSetProperty(
		String name,
		Object value,
		Object o,
		Map context,
		boolean throwPropertyExceptions) {
		try {
			Ognl.setValue(compile(name), context, o, value);
		} catch (OgnlException e) {
			Throwable reason = e.getReason();
			String msg =
				"Caught OgnlException while setting property '"
					+ name
					+ "' on type '"
					+ o.getClass().getName()
					+ "'.";
			Throwable exception = (reason == null) ? e : reason;

			if (throwPropertyExceptions) {
				log.error(msg, exception);
				throw new RuntimeException(msg);
			} else {
				log.warn(msg, exception);
			}
		}
	}

}
