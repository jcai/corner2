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

package corner.demo.page.many2many2;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.relative.ReflectRelativeEntityListPage;
import corner.util.BeanUtils;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class ABListPage extends ReflectRelativeEntityListPage {

	/**
	 * @see corner.orm.tapestry.page.relative.AbstractRelativeEntityListPage#doEditEntityAction(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IPage doEditEntityAction(Object entity) {
		ABFormPage page = (ABFormPage)this.getRelativeObjectOperator().doEditRelativeEntityAction(this.getRootedObject(),entity,this.getEntityFormPageStr());
		page.setValue(BeanUtils.getProperty(entity,"message"));
//		return super.doEditEntityAction(entity);
		return page;
	}

}
