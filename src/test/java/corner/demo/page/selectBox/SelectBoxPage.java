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

package corner.demo.page.selectBox;

import java.util.ArrayList;
import java.util.List;

import corner.orm.tapestry.component.selectBox.ISelectBox;
import corner.orm.tapestry.page.PoFormPage;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class SelectBoxPage extends PoFormPage{
	/**
	 * @return
	 */
	public List<ISelectBox> getFromList(){
		return new ArrayList<ISelectBox>();
	}
}
