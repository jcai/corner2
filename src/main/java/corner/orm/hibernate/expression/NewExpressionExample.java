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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.TypedValue;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.AbstractComponentType;
import org.hibernate.type.Type;

import corner.orm.hibernate.expression.ExpressionParser.ExpPair;
import corner.orm.hibernate.expression.annotations.QueryTypeWorker;
import corner.orm.hibernate.expression.annotations.QueryDefinition.QueryType;
import corner.service.EntityService;

/**
 * 尝试新的表达式查询
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class NewExpressionExample extends Example {
	QueryTypeWorker worker;

	private boolean isIgnoreCaseEnabled;

	private boolean isLikeEnabled;

	private Object entity;



	/**
	 * 
	 */
	private static final long serialVersionUID = -982149165430643197L;
	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();

	protected NewExpressionExample(Object entity, PropertySelector selector) {
		super(entity, selector);
		this.selector=selector;
		this.entity=entity;
		// 首先分析出来类多对应的所有的特列查询
		worker = new QueryTypeWorker(EntityService.getEntityClass(entity));

	}
	/**
	 * Create a new instance, which includes all non-null properties
	 * by default
	 * @param entity
	 * @return a new instance of <tt>Example</tt>
	 */
	public static Example create(Object entity) {
		if (entity==null) throw new NullPointerException("null example");
		return new NewExpressionExample(entity, NOT_NULL);
	}
	
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
	throws HibernateException {

		EntityPersister meta = criteriaQuery.getFactory()
				.getEntityPersister( criteriaQuery.getEntityName(criteria) );
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		 //TODO: get all properties, not just the fetched ones!
		Object[] values = meta.getPropertyValues(entity , getEntityMode(criteria, criteriaQuery) );
		List<TypedValue> list = new ArrayList<TypedValue>();
		for (int i=0; i<propertyNames.length; i++) {
			Object value = values[i];
			Type type = propertyTypes[i];
			String name = propertyNames[i];

			boolean isPropertyIncluded = i!=meta.getVersionProperty() &&
				isPropertyIncluded(value, name, type);

			if (isPropertyIncluded) {
				if ( propertyTypes[i].isComponentType() ) {
					addComponentTypedValues(name, value, (AbstractComponentType) type, list, criteria, criteriaQuery);
				}
				else {
					addPropertyTypedValue(name,value, type, list);
				}
			}
		}
		return (TypedValue[]) list.toArray(TYPED_VALUES);
	}
	
	protected void addPropertyTypedValue(String propertyName,Object value, Type type, List list) {
		if ( value!=null ) {
			if ( value instanceof String ) {
				QueryType qt=this.worker.getQueryTypeByPropertyName(propertyName);
				if(qt==QueryType.Date){
					String [] values=ExpressionParser.parseDateExpression((String) value);
					for(String v:values){
						super.addPropertyTypedValue(v, type, list);
					}
				}else{
					List<ExpPair> values=ExpressionParser.parseStringExpression((String) value);
					for(ExpPair p:values){
						super.addPropertyTypedValue(p.value, type, list);
					}
					
				}
			}else{
				super.addPropertyTypedValue(value,type,list);
			}
		}
	}

	/**
	 * @see org.hibernate.criterion.Example#appendPropertyCondition(java.lang.String,
	 *      java.lang.Object, org.hibernate.Criteria,
	 *      org.hibernate.criterion.CriteriaQuery, java.lang.StringBuffer)
	 */
	@Override
	protected void appendPropertyCondition(String propertyName,
			Object propertyValue, Criteria criteria, CriteriaQuery cq,
			StringBuffer buf) throws HibernateException {
		Criterion crit;
		if (propertyValue != null) {
			boolean isString = propertyValue instanceof String;
			QueryType qt=this.worker.getQueryTypeByPropertyName(propertyName);
			if(qt==QueryType.Date){//日期
				crit = new DateExpression(propertyName, (String) propertyValue);
			}else if(qt==QueryType.String||isString){//字符串
				String op = isLikeEnabled && isString ? " like " : "=";
				crit = new StringExpression(propertyName, propertyValue.toString(),
						op, isIgnoreCaseEnabled && isString);
			}else{//其他类型
				crit=Restrictions.eq(propertyName, propertyValue);
			}
		} else {
			crit = Restrictions.isNull(propertyName);
		}
		String critCondition = crit.toSqlString(criteria, cq);
		if (buf.length() > 1 && critCondition.trim().length() > 0)
			buf.append(" and ");
		buf.append(critCondition);

	}
//	=============== copy from org.hibernate.criterion.Example
	
	
	/**
	 * 
	 * @see org.hibernate.criterion.Example#enableLike(org.hibernate.criterion.MatchMode)
	 */
	public Example enableLike(MatchMode matchMode) {
		super.enableLike(matchMode);
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
	private static final Object[] TYPED_VALUES = new TypedValue[0];
	
	private EntityMode getEntityMode(Criteria criteria, CriteriaQuery criteriaQuery) {
		EntityPersister meta = criteriaQuery.getFactory()
				.getEntityPersister( criteriaQuery.getEntityName(criteria) );
		EntityMode result = meta.guessEntityMode(entity);
		if (result==null) {
			throw new ClassCastException( entity.getClass().getName() );
		}
		return result;
	}
	private final Set<String> excludedProperties = new HashSet<String>();

	private PropertySelector selector;
	/**
	 * Exclude a particular named property
	 */
	public Example excludeProperty(String name) {
		this.excludedProperties.add(name);
		super.excludeProperty(name);
		
		return this;
	}
	private boolean isPropertyIncluded(Object value, String name, Type type) {
		return !excludedProperties.contains(name) &&
			!type.isAssociationType() &&
			selector.include(value, name, type);
	}
	/**
	 * Set the property selector
	 */
	public Example setPropertySelector(PropertySelector selector) {
		this.selector = selector;
		super.setPropertySelector(selector);
		return this;
	}
	static final class NotNullPropertySelector implements PropertySelector {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1811269721101648345L;

		public boolean include(Object object, String propertyName, Type type) {
			return object!=null;
		}
		
		private Object readResolve() {
			return NOT_NULL;
		}
	}
}
