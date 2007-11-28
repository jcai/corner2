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
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;

/**
 * 左邻树
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class LeftTree extends BaseComponent{
	@InjectScript("LeftTree.script")
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
			
			Object[] parameters = getLinkFactory().extractListenerParameters(cycle);
	        
	        String onSelectFunName = (String) parameters[0];
	        String queryClassName = (String) parameters[1];
	        
	        String dependField = null;
	        
	        if(parameters.length > 2){
	        	dependField = (String) parameters[2];
	        }
			
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("component", this);
			parms.put("parentAction", onSelectFunName);
			parms.put("queryClassName", queryClassName);
			parms.put("dependField", dependField);
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
	}
	
	public String getUrl(){
		ILink link = this.getLeftTreeService().getLink(true, new Object[1]);
		return link.getURL();
	}
	
	/** 名称 * */
	@Parameter(defaultValue = "literal:My Tree")
	public abstract String getTitle();
	
	/**
	 * @return
	 */
	@InjectObject("engine-service:leftTree")
	public abstract IEngineService getLeftTreeService();
	
	@InjectObject("infrastructure:linkFactory")
	public abstract LinkFactory getLinkFactory();
}
