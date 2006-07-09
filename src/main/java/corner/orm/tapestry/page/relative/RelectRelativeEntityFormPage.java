/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * 通过反射来实现关联表单的页面。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class RelectRelativeEntityFormPage extends
		AbstractRelativeEntityFormPage<Object,Object> {
	/**
	 * 得到关联的对象的属性名称。
	 * @return
	 */
	protected  String getRelativePropertyName(){
		return EntityConverter.getClassNameAsPropertyName(this.getRootedObject());
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
