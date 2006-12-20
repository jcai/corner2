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

package corner.orm.tapestry.component.textfield;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;

/**
 * 提供一个查询的Dialog,此查询页面的url，通过PageLink来获取
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class PageQueryDialog extends AbstractQueryDialog {

	@Parameter(required = true)
	public abstract String getQueryPageName();
	
	@InjectObject("service:tapestry.services.Page")
	public abstract IEngineService getPageService();

	/**
	 * @see corner.orm.tapestry.component.textfield.AbstractQueryDialog#getUrl()
	 */
	@Override
	protected String getUrl() {
		String url=this.getPageService().getLink(false, this.getQueryPageName()).getAbsoluteURL();
        return url;
	}
}
