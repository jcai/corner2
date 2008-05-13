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

package corner.orm.tapestry.component.tree.pop;

import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.engine.IEngineService;

import corner.orm.tapestry.component.tree.AbstractLeftTree;



/**
 * 弹出窗
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class PopTree extends AbstractLeftTree{
	/**
	 * @see corner.orm.tapestry.component.tree.AbstractLeftTree#getScript()
	 */
	@InjectScript("PopTree.script")
	public abstract IScript getScript();
	
	/**
	 * @see corner.orm.tapestry.component.tree.AbstractLeftTree#getLeftTreeService()
	 */
	@InjectObject("engine-service:popTree")
	public abstract IEngineService getLeftTreeService();
}
