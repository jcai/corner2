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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.AbstractEntityListPage;
import corner.orm.tapestry.page.EntityPage;
import corner.orm.tapestry.table.RelativePersistentBasicTableModel;

/**
 * 关联实体对象的列表，通常通过one端来得到关联对象的列表页。
 * @author jcai
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class AbstractRelativeEntityListPage<T,E> extends AbstractEntityListPage<E> implements IPageRooted<T,E>,IRelativeObjectOperatorSupport{
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @param relativePropertyName 关联的属性名字，通常为复数，譬如：groups,users等。
	 * @return table model
	 */
	public  IBasicTableModel getSource(String relativePropertyName){
		return new RelativePersistentBasicTableModel<T>(this.getEntityService(),this.getRootedObject(),relativePropertyName);
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#getSource()
	 */
	@Override
	public IBasicTableModel getSource() {
		return super.getSource();
	}

	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#doEditEntityAction(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IPage doEditEntityAction(E entity) {
		return this.getRelativeObjectOperator().doEditRelativeEntityAction(this.getRootedObject(),entity,this.getEntityFormPageStr());
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#doNewEntityAction()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IPage doNewEntityAction() {
		return this.getRelativeObjectOperator().doNewRelativeAction(this.getRootedObject(), this.getEntityFormPageStr());
	}
	/**
	 * 从list页面返回根页面。
	 * @return 根页面。
	 */
	public IPage doReturnRootedFormAction(){
		EntityPage<T> page=this.getRootFormPage();
		page.setEntity(this.getRootedObject());
		return page;
	}

	/**
	 * 返回到关联对象的列表页。
	 * @param t 实体对象。
	 * @param listPath 列表页面。
	 * @return 列表页面。
	 */
	@SuppressWarnings("unchecked")
	public IPage doViewRelativeEntityListAction(T t,String listPageName){
		IPageRooted<T,E> page= (IPageRooted<T,E>) this.getRequestCycle().getPage(listPageName);
		page.setRootedObject(t);
		return page;
	}
	
}
