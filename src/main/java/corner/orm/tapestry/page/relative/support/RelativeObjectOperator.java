//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Objectechnology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page.relative.support;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;

import corner.orm.tapestry.page.relative.IPageRooted;

/**
 * 封装了一些对关联对象的操作代理，以便复用.
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public class RelativeObjectOperator implements IRelativeObjectOperator<Object, Object>  {
	private IRequestCycle cycle;
	
	/**
	 * @see corner.orm.tapestry.page.relative.support.IRelativeObjectOperator#doNewRelativeAction(Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IPage doNewRelativeAction(Object obj,String pageName){
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.cycle.getPage(pageName);
		page.setRootedObject(obj);
		return page;
	}
	/**
	 * @see corner.orm.tapestry.page.relative.support.IRelativeObjectOperator#doObjectditRelativeObjectntityAction(Object, Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IPage doEditRelativeEntityAction(Object obj,Object e,String pageName){
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.cycle.getPage(pageName);
		page.setRootedObject(obj);
		page.setEntity(e);
		
		return page;
	}
	public void setRequestCycle(IRequestCycle cycle){
		this.cycle=cycle;
	}
}
