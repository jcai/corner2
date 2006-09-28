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

import corner.demo.model.one.A;
import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class OnePageWithBlob extends AbstractEntityFormPage<A> {

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {

		super.saveOrUpdateEntity();

		if (isEditBlob()) {
			IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
					A.class, getUploadFile(), this.getEntity(), this
							.getEntityService());
			delegate.save();
		}
	}

}
