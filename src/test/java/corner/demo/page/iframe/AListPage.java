//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.iframe;

import org.apache.tapestry.IExternalPage;
import org.apache.tapestry.IRequestCycle;

import corner.orm.tapestry.page.PoListPage;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AListPage extends PoListPage implements IExternalPage{
	/**
	 * @see org.apache.tapestry.IExternalPage#activateExternalPage(java.lang.Object[], org.apache.tapestry.IRequestCycle)
	 */
	public void activateExternalPage(Object[] parameters, IRequestCycle cycle) {
		System.out.println(parameters[1]);
	}
}
