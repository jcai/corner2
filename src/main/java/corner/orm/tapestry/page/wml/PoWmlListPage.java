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

package corner.orm.tapestry.page.wml;

import org.apache.tapestry.util.ContentType;
import corner.orm.tapestry.page.AbstractEntityListPage;

/**
 * wml list页面
 * @author <a href=mailto:wlh@bjmaxinfo.com>wlh</a>
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class PoWmlListPage extends AbstractEntityListPage<Object> {

	/**
	 * @see org.apache.tapestry.html.BasePage#getResponseContentType()
	 */
	@Override
	public ContentType getResponseContentType() {
		return new ContentType("text/vnd.wap.wml");
	}
}
