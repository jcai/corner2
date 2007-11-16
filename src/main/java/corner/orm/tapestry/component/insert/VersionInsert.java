// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-02
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

package corner.orm.tapestry.component.insert;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * 版本显示Insert
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionInsert extends org.apache.tapestry.components.Insert{
	
	private static String DIFF_POSTFIX = "_diff";
	
	/**
	 * @see org.apache.tapestry.components.Insert#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		writer.begin("span");
		writer.attribute("id", this.getClientId());
		super.renderComponent(writer, cycle);
		writer.end("span");
		
		writer.begin("span");
		writer.attribute("class", "view");
		writer.attribute("id", this.getClientId() + DIFF_POSTFIX);
		writer.end("span");
		
		if (!cycle.isRewinding()) {
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("component", this);
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
		
	}

	@InjectScript("VersionInsert.script")
	public abstract IScript getScript();
	
	/**
	 * 要在json串中显示的属性
	 */
	@Parameter(defaultValue = "literal:entity")
	public abstract String getEntityName();

	/**
	 * 要在json串中显示的属性
	 */
	public abstract String getShowProperty();
	
	/**
	 * 设置的json名字
	 */
	@Parameter(defaultValue = "literal:flags")
	public abstract String getJsonName();
	
	/**
	 * 设置的json名字
	 */
	@Parameter(defaultValue = "literal:flags2")
	public abstract String getJsonOtherName();
}
