/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * <font color="red">适用于one-to-many时候，对many端的实体操作</font>
 * <p>通过反射来实现关联表单的页面。
 * <p>适用于one-to-many时候，通过one来增加many端对象的操作。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class ReflectRelativeEntityFormPage extends
		AbstractRelativeEntityFormPage<Object,Object> {
	
	/**
	 * 得到关联关系的属性名字，此名字可以在.page文件中设置.可以避免多余的Java文件.
	 * @return 关联的属性名字.
	 */
	public abstract String getRelativeProName();
	/**
	 * 得到关联的对象的属性名称。
	 * <P>提供子类覆写该方法。
	 * @return 属性名字。
	 */
	protected  String getRelativePropertyName(){
		if(this.getRelativeProName()==null)
			return EntityConverter.getClassNameAsPropertyName(this.getRootedObject());
		else
			return this.getRelativeProName();
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		BeanUtils.setProperty(getEntity(),getRelativePropertyName(),this.getRootedObject());
		super.saveOrUpdateEntity();
		this.flushHibernate();
	}
	
}
