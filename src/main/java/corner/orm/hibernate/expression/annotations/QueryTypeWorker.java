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

import java.util.HashMap;
import java.util.Map;

import corner.orm.hibernate.expression.annotations.QueryDefinition.QueryField;
import corner.orm.hibernate.expression.annotations.QueryDefinition.QueryType;

/**
 * 对声明的查询类型进行处理，得到查询的类型.
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class QueryTypeWorker {
	
	//得到对应类所有的annotaions.
	private Map<String,QueryType> queryTypes=new HashMap<String,QueryType>();

	public QueryTypeWorker(Class<?> clazz) {
		QueryDefinition qd= clazz.getAnnotation(QueryDefinition.class);
		if(qd!=null){
			for(QueryField field:qd.value()){
				queryTypes.put(field.propertyName(),field.queryType());
			}
		}
	}
	
	public QueryType getQueryTypeByPropertyName(String propertyName) {
		return queryTypes.get(propertyName);
	}

	
}
