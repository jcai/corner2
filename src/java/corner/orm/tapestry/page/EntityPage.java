//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import java.io.Serializable;

import org.apache.tapestry.IPage;

public interface EntityPage<T> extends IPage {
	/** ΚµΜε* */
	public abstract T getEntity();

	public abstract void setEntity(T entity);

	public abstract void loadEntity(Serializable key);
}
