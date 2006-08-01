//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang&Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.page;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.services.DataSqueezer;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;

/**
 * 抽象的实体类.
 * <p>
 * 实现了抽象实体基本的方法。
 *
 * @author jcai
 * @version $Revision$
 * @since 2006-3-5
 * @param <T>
 *            当前抽象的实体。
 */
public abstract class AbstractEntityPage<T> extends BasePage implements
		EntityPage<T> {
	/** 对日期类型的格式化 * */
	private SimpleDateFormat _dateFormat;

	/**
	 * 得到主键值
	 *
	 * @return 主键值.
	 * @deprecated 将在2.1中删除
	 */
	@Persist("client:page")
	public abstract Serializable getKey();

	/**
	 * 设定主键值.
	 *
	 * @param keyValue
	 *            主键值.
	 * @deprecated 将在2.1中删除
	 */
	public abstract void setKey(Serializable keyValue);

	/**
	 * 得到关联的主键Id值.
	 *
	 * @deprecated 将在2.1中删除
	 * @return 关联的主键值
	 */
	@Persist("client:page")
	public abstract Serializable getRelativeId();

	/**
	 * 设定关联对象的主键值
	 *
	 * @param relativeId
	 *            关联对象的主键值.
	 * @deprecated 将在2.1中删除
	 */
	public abstract void setRelativeId(Serializable relativeId);

	/**
	 * 得到关联的页面.
	 *
	 * @deprecated 将在2.1中删除
	 */
	@Persist("client:page")
	public abstract String getRelativePage();

	/**
	 * 设定关联的页面.
	 *
	 * @param page
	 *            关联页
	 * @deprecated 将在2.1中删除
	 */
	public abstract void setRelativePage(String page);

	/**
	 *
	 * 关联类名称.
	 *
	 * @param clazzName
	 *            关联的类名.
	 * @deprecated 将在2.1中删除
	 */
	@Persist("client:page")
	public abstract void setRelativeClassName(String clazzName);

	/**
	 * @deprecated 将在2.1中删除
	 * @return
	 */
	public abstract String getRelativeClassName();

	/**
	 * @deprecated 将在2.1中删除
	 * @see corner.orm.tapestry.page.EntityPage#loadEntity(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public void loadEntity(Serializable key) {
		T tmpObj = getEntityService().getEntity(
				(Class<T>) this.getEntity().getClass(), key);
		if (tmpObj != null) {
			this.setEntity(tmpObj);
			this.setKey(key);
		}
	}

	/**
	 * 保存和更新实体。
	 */
	protected void saveOrUpdateEntity() {
		getEntityService().saveOrUpdateEntity(getEntity());

	}

	/**
	 * 得到日期格式化对象.
	 *
	 * @return 日期格式化对象.
	 */
	public Format getDateFormat() {
		if (_dateFormat == null)
			_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return _dateFormat;
	}

	/**
	 * 通过给定的日期格式化字符串,来得到一个格式化对象.
	 *
	 * @param formatStr
	 *            格式化模式.
	 * @return 格式化对象.
	 */
	public Format getDateFormat(String formatStr) {
		return new SimpleDateFormat(formatStr);
	}

	/**
	 *
	 * 对hibernate进行flush操作。
	 *
	 * @since 2.0.3
	 */
	protected void flushHibernate() {
		((HibernateObjectRelativeUtils) this.getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().flush();
	}

	/**
	 * 得到tapestry的datasqueezer。
	 *
	 * @return datasqueezer
	 * @since 2.0.3
	 */
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	public String getCurrentPagePath() {
		String thisPageName = this.getPageName();
		StringBuffer sb = new StringBuffer();
		sb.append(thisPageName.substring(0, thisPageName.lastIndexOf("/")));
		sb.append("/");
		return sb.toString();
	}

}
