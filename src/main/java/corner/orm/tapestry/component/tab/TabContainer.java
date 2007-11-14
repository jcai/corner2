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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;

/**
 * 实现一个tab container组件。
 * 
 * @author jcai
 * @version $Revision$
 * @since 0.2.1
 */
public abstract class TabContainer extends BaseComponent {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(TabContainer.class);
	
	
	/**
	 * Inject java script
	 * @return script
	 */
	public abstract IScript getScript();
	/**
	 * element
	 * @return
	 */
	public abstract String getElement();
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		//元素开始
		writer.begin(this.getElement());
		//写ID
		super.renderIdAttribute(writer, cycle);
		
		
		
		this.renderInformalParameters(writer, cycle);
		String widgetId=getClientId()+"Widget";
		
		JSONArray oldTabs=(JSONArray) cycle.getAttribute(TabConstants.TAB_WIDGETS_ID_COLLECTION_STR);
		if(oldTabs!=null){
			oldTabs.put(widgetId);
		}
		//所有tab的集合.
		JSONArray tabs=new JSONArray();
		cycle.setAttribute(TabConstants.TAB_WIDGETS_ID_COLLECTION_STR, tabs);
		
		String oldSelectedWidgetId=(String) cycle.getAttribute(TabConstants.TAB_SELECTED_WIDGET_ID_STR);
		
		//render body
		super.renderBody(writer, cycle);
		String selectedWidgetId=(String) cycle.getAttribute(TabConstants.TAB_SELECTED_WIDGET_ID_STR);
		cycle.setAttribute(TabConstants.TAB_SELECTED_WIDGET_ID_STR, oldSelectedWidgetId);
		//set old tabs
		cycle.setAttribute(TabConstants.TAB_WIDGETS_ID_COLLECTION_STR,oldTabs);
		//元素结束
		writer.end(this.getElement());
		
		PageRenderSupport pageRenderSupport = TapestryUtils
		.getPageRenderSupport(cycle, this);
		
		//JavaScript参数
		Map<String,String> map=new HashMap<String,String>();
				 
		map.put("clientId", getClientId());
		
		
		//设定一些json属性.
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("widgetId",widgetId);
		map.put("props", jsonObj.toString());
		
		map.put("tabs", tabs.toString());
		
		map.put("selectedWidgetId", selectedWidgetId);

		if (logger.isDebugEnabled()) {
			logger.debug("containerid::["+getClientId()+"]"); //$NON-NLS-1$
		}

		getScript().execute(this,cycle, pageRenderSupport, map);
		
	}

	
	
}
