//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.hibernate.expression.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 定义一个查询字段的注释.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryDefinition {
	/**
	 * 得到所有定义的查询字段。
	 * @return 定义的查询字段数组.
	 */
	public QueryField[] value();

	/**
	 * 定义查询的字段
	 * @author Jun Tsai
	 * @version $Revision$
	 * @since 2.1
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface QueryField{
		String propertyName();
		QueryType queryType() default QueryType.String;
	}
	
	/**
	 * 枚举类型，定义字段查询的类型.
	 * @author Jun Tsai
	 * @version $Revision$
	 * @since 2.1
	 */
	public static enum QueryType {
		Date,String
	}

}
