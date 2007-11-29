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

package corner.orm.tapestry.component.prototype.window;

import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class LeftTreeDialog extends WindowDialog{
	
	@Parameter(required = true)
	public abstract String getQueryClassName();
	
	@Parameter
	public abstract String getDependField();
	
	@Parameter(defaultValue = "literal:My")
	public abstract String getTitle();
	
	/**
	 * @see corner.orm.tapestry.component.prototype.window.WindowDialog#getScript()
	 */
	@InjectScript("LeftTreeDialog.script")
	public abstract IScript getScript();
}
