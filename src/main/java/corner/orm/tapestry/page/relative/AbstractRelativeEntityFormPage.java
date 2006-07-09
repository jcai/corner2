/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.AbstractEntityFormPage;

/**
 * 增加关联关系对象时候用到的表单页。
 * 适用于one-to-many时候，通过one来增加many端对象的操作。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class AbstractRelativeEntityFormPage<T, E> extends AbstractEntityFormPage<E> implements IRelativePage<T>{

	public abstract IPage getRootFormPage();
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityFormPage#getEntityListPage()
	 */
	@Override
	protected IPage getEntityListPage() {
		return getRootFormPage();
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		// TODO Auto-generated method stub
		super.saveOrUpdateEntity();
	}
	
	
}
