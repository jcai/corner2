// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-09-02
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
