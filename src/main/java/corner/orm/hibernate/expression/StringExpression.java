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

package corner.orm.hibernate.expression;

import java.sql.Types;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.TypedValue;
import org.hibernate.type.Type;

import corner.orm.hibernate.expression.ExpressionParser.ExpPair;

/**
 * 实现对字符串的分析操作.
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class StringExpression implements Criterion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3795821050206936248L;

	private final String propertyName;

	private final String value;

	private boolean ignoreCase;

	private final String op;
	

	private List<ExpPair> exps;

	protected StringExpression(String propertyName, String value, String op) {
		this.propertyName = propertyName;
		exps=ExpressionParser.parseStringExpression(value);
		this.value = value;
		this.op = op;
	}

	protected StringExpression(String propertyName, String value, String op,
			boolean ignoreCase) {
		this.propertyName = propertyName;
		this.value = value;
		exps=ExpressionParser.parseStringExpression(value);
		this.ignoreCase = ignoreCase;
		this.op = op;
	}

	public StringExpression ignoreCase() {
		ignoreCase = true;
		return this;
	}

	/**
	 * @see org.hibernate.criterion.Criterion#getTypedValues(org.hibernate.Criteria,
	 *      org.hibernate.criterion.CriteriaQuery)
	 */
	public TypedValue[] getTypedValues(Criteria criteria,
			CriteriaQuery criteriaQuery) throws HibernateException {
		TypedValue[] r=new TypedValue[exps.size()];
		int i=0;
		for(ExpPair p:exps){
			Object icvalue = ignoreCase ? p.value.toString().toLowerCase() : p.value;
			r[i++]= criteriaQuery.getTypedValue(criteria,
					propertyName, icvalue);
		}
		
		return r;
	}

	/**
	 * @see org.hibernate.criterion.Criterion#toSqlString(org.hibernate.Criteria,
	 *      org.hibernate.criterion.CriteriaQuery)
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {
		String[] columns = criteriaQuery.getColumnsUsingProjection(criteria,
				propertyName);
		Type type = criteriaQuery
				.getTypeUsingProjection(criteria, propertyName);
		StringBuffer fragment = new StringBuffer();
		if (columns.length > 1)
			fragment.append('(');
		SessionFactoryImplementor factory = criteriaQuery.getFactory();
		int[] sqlTypes = type.sqlTypes(factory);
		for (int i = 0; i < columns.length; i++) {
			boolean lower = ignoreCase
					&& (sqlTypes[i] == Types.VARCHAR || sqlTypes[i] == Types.CHAR);
			//表达式是否大于1
			if(exps.size()>1){
				fragment.append('(');
			}
			for(ExpPair p:exps){
				fragment.append(" "+p.exp+" ");
				if (lower) {
					fragment.append(factory.getDialect().getLowercaseFunction())
							.append('(');
				}
				fragment.append(columns[i]);
				if (lower)
					fragment.append(')');
				fragment.append(getOp()).append("?");
				
			}
			if(exps.size()>1){
				fragment.append(')');
			}
			
			
			if (i < columns.length - 1)
				fragment.append(" and ");
		}
		if (columns.length > 1)
			fragment.append(')');
		return fragment.toString();
		
	}
	public String toString() {
		return propertyName + getOp() + value;
	}

	protected final String getOp() {
		return op;
	}

}
