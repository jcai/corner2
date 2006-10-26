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

package corner.orm.tapestry.validator;

import java.sql.SQLException;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidationStrings;
import org.apache.tapestry.valid.ValidatorException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 判断实体是否唯一.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class UniqueEntity extends BaseValidator {
	private String entityClassName;

	private String property;

	public UniqueEntity() {
		super();
	}

	public UniqueEntity(String arg0) {
		super(arg0);
	}

	/**
	 * 
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent,
	 *      org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages,
			final Object object) throws ValidatorException {
		if (object == null) {
			return;
		}
		long rowCount = (Long) ((HibernateDaoSupport) getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session
								.createCriteria(entityClassName);
						criteria.setProjection(Projections.rowCount());
						criteria.add(Restrictions.eq(property, object));
						return criteria.list().get(0);
					}
				});
		if (rowCount == 1) {
			throw new ValidatorException(messages.formatValidationMessage(
					"${1}已经存在", ValidationStrings.REQUIRED_FIELD,
					new Object[] { field.getDisplayName() }));
		}

	}
	/**
	 * 设定待查询的类名
	 * @param entityClassName
	 */
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	/**
	 * 设定查询的属性名称。
	 * @param property 属性名称.
	 */
	public void setCriterionProName(String property) {
		this.property = property;
	}

	private EntityService getEntityService() {
		return (EntityService) SpringContainer.getInstance()
				.getApplicationContext().getBean("entityService");
	}
}
