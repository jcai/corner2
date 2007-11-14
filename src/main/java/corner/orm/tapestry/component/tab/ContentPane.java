// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-27
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

package corner.orm.tapestry.component.tab;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;

/**
 * 一个container的内容tab。
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.2.1
 */
public abstract class ContentPane extends BaseComponent {
	

	/**
	 * inject script
	 * @return script object.
	 */
	public abstract IScript getScript();
	/**
	 * inject element
	 * @return element
	 */
	public abstract String getElement();
	
	/**
	 * 得到tab的label。 
	 * @return label.
	 */
	
	public abstract String getLabel();
	
	public abstract boolean isSelected();
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		writer.begin(this.getElement());
		this.renderIdAttribute(writer, cycle);
		this.renderInformalParameters(writer, cycle);
		this.renderBody(writer, cycle);
		writer.end(this.getElement());
		String widgetId=getClientId()+"Widget";
		if(this.isSelected()){
			cycle.setAttribute(TabConstants.TAB_SELECTED_WIDGET_ID_STR, widgetId);
		}
		JSONArray tabs=(JSONArray) cycle.getAttribute(TabConstants.TAB_WIDGETS_ID_COLLECTION_STR);
		tabs.put(widgetId);
		
		PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
		Map<String,String> map=new HashMap<String,String>();
		map.put("clientId", getClientId());
		
		
		JSONObject json=new JSONObject();
		json.put("label", this.getLabel());
		json.put("widgetId", widgetId);
		map.put("props", json.toString());
		
		getScript().execute(this,cycle, pageRenderSupport, map);
		
	}
}
