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

package corner.orm.tapestry.component.insert;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * 用来Render出相应的版本json串
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionJson extends AbstractComponent {
	
	/**
	 * render出的json串，变量名称的前缀
	 */
	public static final String VERSION_JSON_NAME_FLAG_STR = "flag";
	
	@InjectScript("VersionJson.script")
	public abstract IScript getScript();

	
	/**
	 * 页面render出来的json串变量的名称
	 * @return {@link String}
	 */
	@Parameter(required=true)
	public abstract String getJsonName();
	
	/**
	 * 页面render出来的json串变量
	 * @return {@link String}
	 */
	@Parameter(required=true)
	public abstract String getJsonStr();


	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if (!cycle.isRewinding()) {
			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
			Map<String, String> params = new HashMap<String, String>();
			params.put("json", getJsonStr());
			params.put("jsonName", getJsonName());
			getScript().execute(this, cycle, prs, params);
		}
	}
}
