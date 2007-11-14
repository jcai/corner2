// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-01
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
		  json.put("inputId", getClientId());
	      json.put("inputName", getName());
		  //构造查询页面的URL
		  json.put("url",this.getPageService().getLink(false, this.getQueryPageName()).getAbsoluteURL());
		  parms.put("props", json.toString());
		 
		 PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
	        getScript().execute(this, cycle, prs, parms);
	}

	@InjectScript("QueryBox.script")
	public abstract IScript getScript();
	
}
