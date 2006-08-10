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

import corner.orm.tapestry.page.AbstractEntityListPage;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 * 抽象实体列表页面
 * <p>扩展了AbstractEntityListPage类,主要提供了从实体列表页面直接跳转的ReflectRelativeEntityFormPage和ReflectRelativeEntityListPage页面的方法
 */
public abstract class AbstractManyEntityListPage<T> extends AbstractEntityListPage<T> implements IRelativeObjectOperatorSupport{

	/**
	 * 返回到关联对象的列表页。
	 * @param t 实体对象。
	 * @param listPath 列表页面。
	 * @return 列表页面。
	 * @since 2.1
	 */
	@SuppressWarnings("unchecked")
	public IPage doViewRelativeEntityListAction(T t,String listPageName){
		IPageRooted<T,Object> page= (IPageRooted<T,Object>) this.getRequestCycle().getPage(listPageName);
		page.setRootedObject(t);
		return page;
	}	
	/**
	 * 通常操作one-to-one时候使用
	 * @param rootObj one 主对象
	 * @param relativeObj 从对象
	 * @param pageName
	 * @return 通常是在编辑rootObj(主对象)的时候进行relativeObj(从对象)操作。当relativeObj不为空的时候，
	 * 就编辑relativeObj，当relativeObj为空的时候就新创建一个relativeObj
	 */
	@SuppressWarnings("unchecked")
	public IPage doNewOrEditRelativeEntityAction(T rootObj,Object relativeObj,String pageName){
		if(relativeObj!=null){
			return this.getRelativeObjectOperator().doEditRelativeEntityAction(rootObj, relativeObj, pageName);
		}
		else{
			return this.getRelativeObjectOperator().doNewRelativeAction(rootObj, pageName);
		}	
	}
}
