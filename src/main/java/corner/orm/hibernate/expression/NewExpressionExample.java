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
import org.hibernate.criterion.Example;
import org.hibernate.type.AbstractComponentType;
import org.hibernate.type.Type;

/**
 * 尝试新的表达式查询
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class NewExpressionExample extends Example {

	/**
	 * 
	 */
	private static final long serialVersionUID = -982149165430643197L;

	protected NewExpressionExample(Object entity, PropertySelector selector) {
		super(entity, selector);
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
		super.appendPropertyCondition(propertyName, propertyValue, criteria, cq, buf);
	}
	

}
