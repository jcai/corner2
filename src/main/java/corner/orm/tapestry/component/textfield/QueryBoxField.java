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

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.json.JSONObject;


/**
 * 提供一个查询窗体
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class QueryBoxField extends TextField  {

	
	@Parameter(required=true)
	public abstract String getQueryPageName();
	
	@InjectObject("service:tapestry.services.Page")
	public abstract IEngineService getPageService();
	
	/**
	 * @see corner.orm.tapestry.component.textfield.TextField#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderFormComponent(writer, cycle);
		 Map<String,String> parms=new HashMap<String,String>(); 
		  parms.put("id", getClientId());
		  JSONObject json=new JSONObject();
		  //widgetID
		  json.put("widgetId",getClientId());
		  //构造查询页面的URL
		  json.put("url",this.getPageService().getLink(false, this.getQueryPageName()));
		  parms.put("props", json.toString());
		 
		 PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
	        getScript().execute(this, cycle, prs, parms);
	}

	@InjectScript("QueryBoxy.script")
	public abstract IScript getScript();
	
}
