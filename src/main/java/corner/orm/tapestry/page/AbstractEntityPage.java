//==============================================================================
//file :       $Id$
//project:     corner-example
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

import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.html.BasePage;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.orm.tapestry.page.EntityPage;
import corner.util.BeanUtils;

/**
 * 抽象的实体页.
 *
 * @author jcai
 * @version $Revision$
 * @since 2006-3-5
 */
public abstract class AbstractEntityPage<T> extends BasePage implements
		EntityPage<T> {
	/** 对日期类型的格式化 **/
	private SimpleDateFormat _dateFormat;

	/**
	 * 得到主键值
	 *
	 * @return 主键值.
	 */
	@Persist("client:page")
	public abstract Serializable getKey();
	/**
	 * 设定主键值.
	 * @param keyValue 主键值.
	 */
	public abstract void setKey(Serializable keyValue);

	/**
	 * 得到关联的主键Id值.
	 *
	 * @return 关联的主键值
	 */
	@Persist("client:page")
	public abstract Serializable getRelativeId();
	/**
	 * 设定关联对象的主键值
	 * @param relativeId 关联对象的主键值.
	 */
	public abstract void setRelativeId(Serializable relativeId);

	/**
	 * 得到关联的页面.
	 */
	@Persist("client:page")
	public abstract String getRelativePage();
	/**
	 * 设定关联的页面.
	 * @param page 关联页
	 */
	public abstract void setRelativePage(String page);

	/**
	 *
	 * 关联类名称.
	 * @param clazzName 关联的类名.
	 */
	@Persist("client:page")
	public abstract void setRelativeClassName(
			String clazzName);
	public abstract String getRelativeClassName();

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
	 * 重载更新.
	 * 目的提供对关联对象的操作.
	 */
	protected void saveOrUpdateEntity() {
		if(this.getRelativeClassName()!=null&&this.getRelativeId()!=null){
			//得到关联的对象实例.
			Object obj=this.getEntityService().getEntity(this.getRelativeClassName(),this.getRelativeId());
			//设定关联的对象属性.
			BeanUtils.setProperty(this.getEntity(),this.getClazzNameAsPropertyName(this.getRelativeClassName()),obj);
		}
		//save or update
		getEntityService().saveOrUpdateEntity(getEntity());

	}
	private String getClazzNameAsPropertyName(String clazzName) {
		clazzName=clazzName.substring(clazzName.lastIndexOf(".")+1);
		return Character.toLowerCase(clazzName.charAt(0))+clazzName.substring(1);
	}
	/**
	 * 得到日期格式化对象.
	 * @return 日期格式化对象.
	 */
	public Format getDateFormat()
	{
	    if (_dateFormat == null)
	      _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    return _dateFormat;
	}
	/**
	 * 通过给定的日期格式化字符串,来得到一个格式化对象.
	 * @param formatStr 格式化模式.
	 * @return 格式化对象.
	 */
	public Format getDateFormat(String formatStr)
	{
	    return new SimpleDateFormat(formatStr);
	}

	/**
	 *
	 * 对hibernate进行flush操作。
	 * @since 2.0.3
	 */
	protected void flushHibernate(){
		((HibernateObjectRelativeUtils) this.getEntityService().getObjectRelativeUtils()).getHibernateTemplate().flush();
	}
}
