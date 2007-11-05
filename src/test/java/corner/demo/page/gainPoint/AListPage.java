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

import corner.orm.tapestry.page.PoListPage;
import corner.service.svn.IVersionable;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AListPage extends PoListPage{
	/**
	 * @param entity
	 * @return
	 */
	public IPage doVersionPage(Object entity){
		AVersionListPage page = (AVersionListPage) this.getRequestCycle().getPage("gainPoint/AVersionList");
		page.setVersionEntity((IVersionable) entity);
		return page;
	}
}
