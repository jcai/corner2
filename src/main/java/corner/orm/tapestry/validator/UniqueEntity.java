// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-10-26
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

package corner.orm.tapestry.validator;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.orm.spring.SpringContainer;
import corner.orm.tapestry.page.EntityPage;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 判断实体是否唯一.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class UniqueEntity extends BaseValidator {
	private static final String REGEX_PATTERN = "^\\{([\\w\\.]+):(\\w+)\\}$";

	private String entityClassName;

	private String property;

	private String uniqueStr;

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
		Object obj = ((EntityPage) field.getPage()).getEntity();

		// 判断是否为更新操作.
		if (getEntityService().isPersistent(obj)) {// 是否为持久化
			if(this.property != null){//修正了当没有提供属性字段时，发生异常的bug
				Object properyValue = BeanUtils.getProperty(obj, this.property);
				if (object.equals(properyValue)) {// 加入属性没变化，则不进行校验.
					return;
				}
			}
		}
		// 得到行数.
		int rowCount = this.getRowCount(field, object, entityClassName,
				property);
		// 抛出验证异常
		if (rowCount > 0) {
			throw new ValidatorException(messages.formatValidationMessage(
					"{0}为" + object + "的记录已经存在", null, new Object[] { field
							.getDisplayName() }));
		}

	}

	public void setUniqueEntity(String uniqueStr) {

		if (uniqueStr == null) {
			exceptionAdvertiser(uniqueStr);
		}
		this.uniqueStr = uniqueStr;

		initProperty();
	}

	protected void initProperty() {
		Matcher matcher = Pattern.compile(REGEX_PATTERN)
				.matcher(this.uniqueStr);
		if (matcher.groupCount() != 2) {
			exceptionAdvertiser(uniqueStr);
		}
		if (matcher.find()) {
			this.entityClassName = matcher.group(1);
			this.property = matcher.group(2);
		}

	}




	protected EntityService getEntityService() {
		return (EntityService) SpringContainer.getInstance()
				.getApplicationContext().getBean("entityService");
	}



	/**
	 * 查询给定条件的实体是否存在(这个方法可以方便用户自己扩展自己的查询条件)
	 * 
	 * @param obj
	 * @return
	 */
	protected int getRowCount(final IFormComponent field, final Object inputObj,
			final String entityClassName, final String property) {
		return (Integer) getEntityService().execute(new HibernateCallback() {
			
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(entityClassName);
						criteria.setProjection(Projections.rowCount());
						criteria.add(Restrictions.eq(property, inputObj));
						return criteria.list().get(0);
					}
				});
	}
	
	/**
	 * 根据给定的uniqueStr抛出一个ApplicationRuntimeException
	 * @param uniqueStr
	 */
	protected void exceptionAdvertiser(String uniqueStr){
		throw new ApplicationRuntimeException("UniqueEntity validator的参数'"
				+ uniqueStr + "'不对，应该为'{className:propertyName}'");
	}

	String getEntityClassName() {
		return this.entityClassName;
	}
	
	String getPropertyName() {
		return this.property;
	}

}
