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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.Type;

import corner.orm.hibernate.expression.annotations.QueryTypeWorker;
import corner.service.EntityService;

/**
 * 尝试新的表达式查询
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class NewExpressionExample extends Example {
	QueryTypeWorker worker;
	private boolean isIgnoreCaseEnabled;
	private boolean isLikeEnabled;
	private MatchMode matchMode;
	/**
	 * 
	 */
	private static final long serialVersionUID = -982149165430643197L;

	protected NewExpressionExample(Object entity, PropertySelector selector) {
		super(entity, selector);
		//首先分析出来类多对应的所有的特列查询
		worker=new QueryTypeWorker(EntityService.getEntityClass(entity));
		
	}


	/**
	 * @see org.hibernate.criterion.Example#addPropertyTypedValue(java.lang.Object, org.hibernate.type.Type, java.util.List)
	 */
	@Override
	protected void addPropertyTypedValue(Object value, Type type, List list) {
		super.addPropertyTypedValue(value, type, list);
	}


	/**
	 * @see org.hibernate.criterion.Example#appendPropertyCondition(java.lang.String, java.lang.Object, org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery, java.lang.StringBuffer)
	 */
	@Override
	protected void appendPropertyCondition(String propertyName, Object propertyValue, Criteria criteria, CriteriaQuery cq, StringBuffer buf) throws HibernateException {
		Criterion crit;
		if ( propertyValue!=null ) {
			boolean isString = propertyValue instanceof String;
			String op = isLikeEnabled && isString ? " like " : "=";
			crit=isLikeEnabled&&isString?Restrictions.like(propertyName, (String) propertyValue, matchMode):Restrictions.like(propertyName, (String) propertyValue, matchMode);
			if(isIgnoreCaseEnabled && isString){
				((SimpleExpression) crit).ignoreCase();
			}

		}
		else {
			crit = Restrictions.isNull(propertyName);
		}
		String critCondition = crit.toSqlString(criteria, cq);
		if ( buf.length()>1 && critCondition.trim().length()>0 ) buf.append(" and ");
		buf.append(critCondition);
		
	}
	
	/**
	 * 
	 * @see org.hibernate.criterion.Example#enableLike(org.hibernate.criterion.MatchMode)
	 */
	public Example enableLike(MatchMode matchMode) {
		super.enableLike(matchMode);
		this.matchMode=matchMode;
		isLikeEnabled = true;
		return this;
	}

	

	/**
	 * 
	 * @see org.hibernate.criterion.Example#ignoreCase()
	 */
	public Example ignoreCase() {
		super.ignoreCase();
		isIgnoreCaseEnabled = true;
		return this;
	}
}
