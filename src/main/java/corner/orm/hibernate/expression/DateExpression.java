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

package corner.orm.hibernate.expression;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;

/**
 * 对日期查询进行分析.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class DateExpression implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5019480687227031949L;
	private String[] exps;
	private String propertyName;

	public DateExpression(String propertyName, String value) {
		this.propertyName=propertyName;
		exps=ExpressionParser.parseDateExpression(value);
		if(exps.length<1||exps.length>2){
			throw new IllegalArgumentException("查询日期的表达式错误["+value+"]");
		}
	}

	/**
	 * @see org.hibernate.criterion.Criterion#getTypedValues(org.hibernate.Criteria,
	 *      org.hibernate.criterion.CriteriaQuery)
	 */
	public TypedValue[] getTypedValues(Criteria criteria,
			CriteriaQuery criteriaQuery) throws HibernateException {
		TypedValue[] r=new TypedValue[exps.length];
		int i=0;
		for(String v:exps){
			Object icvalue = v;
			r[i++]= criteriaQuery.getTypedValue(criteria,
					propertyName, icvalue);
		}
		
		return r;
	}
	/**
	 * @see org.hibernate.criterion.Criterion#toSqlString(org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {
		String[] columns = criteriaQuery.getColumnsUsingProjection(criteria,
				propertyName);
		StringBuffer fragment = new StringBuffer();
		if (columns.length > 1)
			fragment.append('(');
		for (int i = 0; i < columns.length; i++) {
			if(exps.length==2){
				
				
				fragment.append('(');
				
				fragment.append(columns[i]).append(">").append("?");
				fragment.append(" and ");
				fragment.append(columns[i]).append("<").append("?");
				
				fragment.append(')');
				
			}else{
				fragment.append(columns[i]).append("=").append("?");
			}
			
			
			if (i < columns.length - 1)
				fragment.append(" and ");
		}
		if (columns.length > 1)
			fragment.append(')');
		return fragment.toString();
	}

}
