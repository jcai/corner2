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

package corner.orm.tapestry.component.ajax;

import org.apache.tapestry.IRequestCycle;

/**
 * AjaxResponse组件返回接口
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IAjaxResponsePage {
	
	/**
	 * 
	 * @param cycle
	 * @return
	 */
	public String getJsonData(IRequestCycle cycle);
}
