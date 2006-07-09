package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.AbstractEntityFormPage;
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
	 * 新增加关系的操作。
	 * @return 增加关系后的页面。
	 */
	public IPage doNewRelativeAction(){
		this.flushHibernate();
		AbstractEntityFormPage<T> page=this.getRootFormPage();
		page.setEntity(this.getRootedObject());
		return page;
	}

}
