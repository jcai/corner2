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

package corner.demo.page.gainPoint;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.EntityPage;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.page.relative.ReflectMultiManyEntityFormPage;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AFormPage extends ReflectMultiManyEntityFormPage{
	
	/**
	 * 编辑实体操作.
	 *
	 * @param entity
	 *            实体.
	 * @return 返回编辑页面.
	 * @since 2.0
	 */

	public IPage doEditEntityAction(Object entity) { // 编辑操作
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.getRequestCycle().getPage("gainPoint/gainPointForm");
		page.setRootedObject(this.getEntity());
		
		return page;
	}
}
