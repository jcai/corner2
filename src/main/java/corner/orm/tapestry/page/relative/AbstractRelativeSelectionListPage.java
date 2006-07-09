package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.AbstractEntityListPage;

/**
 *
 * 抽象的关联关系列表选择页面。
 * <p>通常是提供了选择关联对象的列表页面。
 * @author jcai
 * @version $Revision$
 * @since 2.0.3
 * @param <T> 当前操作的实体。
 * @param <E> 关联的实体。
 */
public abstract class AbstractRelativeSelectionListPage<T,E> extends AbstractEntityListPage<E> implements IPageRooted<T,E>{
	

	/**
	 * 得到抽象many端的表单页面。
	 * @return many端的form表单页面。
	 */
	protected abstract AbstractManyEntityFormPage<T,E> getManyEntityFormPage();


	/**
	 * 新增加关系的操作。
	 * @return 增加关系后的页面。
	 */
	public IPage doNewRelativeAction(){
//		this.getEntityService().saveEntity(this.getRootedObject());
		this.flushHibernate();
		this.getManyEntityFormPage().setEntity(this.getRootedObject());
		return this.getManyEntityFormPage();
	}

}
