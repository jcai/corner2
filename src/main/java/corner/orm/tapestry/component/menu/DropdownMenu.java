//==============================================================================
//file :        DropdownMenu.java
//
//last change:	date:       $Date:2006-08-07 08:26:15Z $
//           	by:         $Author:jcai $
//           	revision:   $Revision:603 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

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
