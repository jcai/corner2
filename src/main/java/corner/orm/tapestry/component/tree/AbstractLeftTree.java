package corner.orm.tapestry.component.tree;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;

/**
 * 左邻树
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class AbstractLeftTree extends BaseComponent {

	public abstract IScript getScript();

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		writer.begin("span");
		writer.attribute("id", this.getClientId());
		super.renderComponent(writer, cycle);
		writer.end("span");
		
		if (!cycle.isRewinding()) {
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("component", this);
			parms.put("linkUrl", getLinkUrl());
			parms.put("title", getTitle());
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
	}

	/**
	 * @return
	 */
	public String getLinkUrl(){
		Object [] parameters = new Object[1];
		parameters[0] = getReturnValues();
		
		ILink link = this.getLeftTreeService().getLink(true, parameters);
		return link.getURL();
	}
	
	/** 名称 * */
	@Parameter
	public abstract String getTitle();
	
	/**
	 * 设置返回值的名称和需要返回的内容
	 * json的形式如：{"htmlAttribute1":"entityGetName1","htmlAttribute2":"entityGetName2"}
	 */
	@Parameter
	public abstract String getReturnValues();

	/**
	 * @return
	 */
	public abstract IEngineService getLeftTreeService();

}