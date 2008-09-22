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

import corner.orm.tapestry.component.AbstractAny;

/**
 * 多选择钮选择返回value
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class MultiCheckBoxToStringSelectedField extends AbstractAny{
	@InjectScript("MultiCheckBoxToStringSelectedField.script")
	public abstract IScript getScript();

	/**
	 * @see corner.orm.tapestry.component.AbstractAny#exceptionMessage()
	 */
	@Override
	protected String exceptionMessage() {
		return "MultiCheckBoxToStringSelectedField";
	}
	
	/**
	 * @see corner.orm.tapestry.component.AbstractAny#getQueryBox()
	 */
	@Parameter(defaultValue = "literal:window.queryBox")
	public abstract String getQueryBox();
}
