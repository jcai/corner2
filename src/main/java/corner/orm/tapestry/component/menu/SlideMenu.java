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

package corner.orm.tapestry.component.menu;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;

/**
 * 左侧滑动的菜单.
 * @author jcai
 * @version $Revision:603 $
 * @since 0.2.1
 */
public abstract class SlideMenu extends BaseComponent {
	/**
	 * 滑动菜单的的ID值。
	 * @return 滑动菜单的ID值。
	 */
	public abstract String getSlideMenuId();
	/**
	 * 得到菜单状态条的ID。
	 * @return 菜单状态条的ID。
	 */
	public abstract String getMenuBarId();
	/**
	 * 得到距离上面的值。
	 * @return 距离上面的值。
	 */
	public abstract int getTop();
	/**
	 * 得到菜单的移动速度。
	 * @return 菜单移动速度。
	 */
	public abstract int getSpeed();
	
	/**
	 * Inject java script
	 * 
	 * @return script
	 */
	public abstract IScript getScript();

	/**
	 * 生成的元素
	 * 
	 * @return 元素的名字
	 */
	public abstract String getElement();
	@SuppressWarnings("unchecked")
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		writer.begin(getElement());

		Map map = new HashMap();
		
		map.put("menuBarId", this.getMenuBarId());
		map.put("top", getTop());
		map.put("speed", this.getSpeed());
		
		if (this.getBinding("id") == null) {
			map.put("slideMenuId", this.getSlideMenuId());
			writer.attribute("id", map.get("slideMenuId").toString());
		} else {
			map.put("slideMenuId", this.getBinding("id").getObject().toString());
		}

		renderInformalParameters(writer, cycle);
		renderBody(writer, cycle);
		writer.end(getElement());
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);
		getScript().execute(this,cycle, pageRenderSupport, map);

	}
}
