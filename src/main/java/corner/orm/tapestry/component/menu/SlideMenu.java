//==============================================================================
//file :        SlideMenu.java
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
