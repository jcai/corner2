/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: CodeSelectModel.java 6829 2007-06-21 09:03:09Z jcai $
 * created at:2007-04-24
 */
package corner.orm.tapestry.component.prototype;

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.services.DataSqueezer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import  corner.util.StringUtils;

import corner.orm.tapestry.state.IContext;

/**
 * 默认的filter，主要是对piano中的代码类进行检索
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CodeSelectModel extends AbstractSelectModel implements
		ISelectModel {

	/**
	 * 用于Autocompleter的拼音检索
	 */
	public static final String ABC_STR = "[a-z]";
	
	/**
	 * 拼音字段名称
	 */
	public static final String ABC_Field = "abcCode";
	
	/**
	 * 用于Autocompleter的中文检索
	 */
	public static final String CHN_STR = "[\u4e00-\u9fa5]";
	
	/**
	 * 中文字段名称
	 */
	public static final String CHN_Field = "chnName";
	
	/**
	 * 字典表中,各种字典实体的简写码
	 */
	public static final String INDEX_CODE_STR = "[A-Z]";
	
	public static final String INDEX_CODE_FIELD = "indexCode";
	
	/**
	 * 用于Autocompleter的数字检索
	 */
	public static final String NUM_STR = "[0-9]";
	
	/**
	 * 数字字段名称
	 */
	public static final String NUM_Field = "numCode";
	
	/**
	 * @see corner.orm.tapestry.component.prototype.ISelectModel#search(org.springframework.orm.hibernate3.HibernateTemplate,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      com.bjmaxinfo.piano.model.system.Company)
	 */
	public List search(HibernateTemplate ht, final String queryClassName,
			final String searchString, final IContext context,
			final DataSqueezer squeezer, final String[] dependFieldsValue) {
		return ht.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryClassName);
				criteria.setProjection(Projections.projectionList().add(
						Projections.property("indexCode")).add(
						Projections.property("engName")).add(
						Projections.property("chnName")));
				
				appendContext(criteria,searchString,context);
				
				appendCriteria(criteria, searchString, squeezer,
						dependFieldsValue);
				criteria.setMaxResults(20);// 最多显示20个
				return criteria.list();
			}
		});
	}
	
	/**
	 * 自定义条件
	 */
	protected void appendContext(Criteria criteria, String searchString, IContext context) {
//		if (isCompanyModel() && context.getCompany() != null) {
//			criteria.add(Restrictions.eq(AbstractCompanywareModel.COMPANY_PRO_NAME, context
//					.getCompany()));
//		}
	}

	/**
	 * 是否和公司关联的model
	 * 
	 * @return 是否和公司管理，if true和公司关联,默认为和铜丝关联
	 */
	protected boolean isCompanyModel() {
		return true;
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.ISelectModel#renderResultRow(org.apache.tapestry.IMarkupWriter,
	 *      java.lang.Object, java.lang.String[])
	 */
	public void renderResultRow(IMarkupWriter writer, Object entity,
			String template, DataSqueezer squeezer) {
		Object[] value = (Object[]) entity;
		writer.printRaw(String.format(template, escapeHtml(value[0]),
				escapeHtml(value[1]), escapeHtml(value[2])));
	}

	protected void appendCriteria(Criteria criteria, String match,
			DataSqueezer squeezer, String[] dependFieldsValue) {
		CodeSelectModel.intelligenceAppendCriteria(criteria, match);

	}

	private IComponent component;

	/**
	 * @see corner.orm.tapestry.component.prototype.ISelectModel#getComponent()
	 */
	public IComponent getComponent() {
		return component;
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.ISelectModel#setComponent(org.apache.tapestry.IComponent)
	 */
	public void setComponent(IComponent component) {
		this.component = component;
	}

	/**
	 * 智能查询方法-根据用户的输入字符的Unicode来判断字符类型，从而选择不同的字段进行查询
	 * 
	 * @param criteria
	 * @param match
	 */
	public static void intelligenceAppendCriteria(Criteria criteria,
			String match) {
		if (match == null || match.trim().length() == 0) {
			return;
		} else {
			String matchField = null;
			if (StringUtils.comparedCharacter(match, ABC_STR)) {// 如果是拼音检索，需要去掉'#'
				matchField = ABC_Field;
			} else if (StringUtils.comparedCharacter(match, CHN_STR)) {
				matchField = CHN_Field;
			} else if (StringUtils.comparedCharacter(match,	INDEX_CODE_STR)) {
				matchField = INDEX_CODE_FIELD;

			} else if (StringUtils.comparedCharacter(match, NUM_STR)) {
				matchField = NUM_Field;
			}
			if (matchField != null)
				criteria.add(Restrictions.like(matchField, match.trim() + "%"));
		}

	}
}
