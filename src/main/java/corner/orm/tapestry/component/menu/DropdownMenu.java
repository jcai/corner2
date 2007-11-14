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
 * 提供一些导航菜单的组件。
 * 
 * @author jcai
 * @version $Revision:603 $
 * @since 0.0.2
 */
public abstract class DropdownMenu extends BaseComponent {
	/**
	 * Inject java script
	 * 
	 * @return script
	 */
	public abstract IScript getScript();

	/**
	 * 下拉菜单的层的ID。
	 * 
	 * @return 下拉菜单的层的ID。
	 */
	public abstract String getPopMenuDivId();

	/**
	 * 生成的元素
	 * 
	 * @return 元素的名字
	 */
	public abstract String getElement();
	/**
	 * 是否为选中状态
	 * @return 是否选中。
	 */
	public abstract boolean  isSelected();

	@SuppressWarnings("unchecked")
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {

		writer.begin(getElement());
		//设定Javascript执行的参数map
		Map map = new HashMap();
		map.put("popMenuDivId", this.getPopMenuDivId());
		map.put("selected", isSelected());
		if (this.getBinding("id") == null) {
			map.put("menuId", this.getId());
			writer.attribute("id", map.get("menuId").toString());
		} else {
			map.put("menuId", this.getBinding("id").getObject().toString());
		}
		//对信息参数进行render
		renderInformalParameters(writer, cycle);
		renderBody(writer, cycle);
		writer.end(getElement());
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);
		getScript().execute(this,cycle, pageRenderSupport, map);

	}
}
