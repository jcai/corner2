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

package corner.orm.tapestry.service.svn;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.engine.IEngineService;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ISvnKitProvider {
	@InjectObject("service:corner.svn.SvnKitService")
    public abstract IEngineService getSvnKitService();
}