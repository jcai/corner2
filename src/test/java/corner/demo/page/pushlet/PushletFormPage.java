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

package corner.demo.page.pushlet;

import corner.demo.model.one.A;
import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.util.Constants;

/**
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class PushletFormPage extends AbstractEntityFormPage<A> {

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		super.saveOrUpdateEntity();
		Constants.addMsg("请我吃饭");
	}
	
	

}
