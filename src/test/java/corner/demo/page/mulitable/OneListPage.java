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

package corner.demo.page.mulitable;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.PoListPage;
import corner.orm.tapestry.table.MulitPersistentBasicTableModel;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class OneListPage extends PoListPage {

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#getSource()
	 */
	@Override
	public IBasicTableModel getSource() {
		return new MulitPersistentBasicTableModel(this.getEntityService(),this,this.getRequestCycle().isRewinding());
	}

}
