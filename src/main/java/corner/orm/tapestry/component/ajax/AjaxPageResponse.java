//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.ResponseBuilder;

/**
 * Ajax请求返回，直接从该页面中获得返回值
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AjaxPageResponse extends BaseComponent implements IDirect{
	
	@InjectScript("AjaxPageResponse.script")
	public abstract IScript getScript();
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		//处理ajax请求
		if (!cycle.isRewinding() && getResponse().isDynamic()) {
			renderPrototypeComponent(writer, cycle);
			return;
		}
		
		if (!cycle.isRewinding()) {
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			ILink link = getDirectService().getLink(true,
					new DirectServiceParameter(this));
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("clientId", this.getClientId());
			parms.put("url", link.getURL());
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
	}
	
	/**
	 * 获得ajax返回值
	 * @param writer
	 * @param cycle
	 */
	private void renderPrototypeComponent(IMarkupWriter writer,
			IRequestCycle cycle) {
		String json = ((IAjaxResponsePage)this.getPage()).getJsonData(cycle);
		writer.printRaw(json);
	}

	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		
	}
	
	/**
	 * @see org.apache.tapestry.IDynamicInvoker#getUpdateComponents()
	 */
	public List getUpdateComponents() {
		List<String> list = new ArrayList<String>();
		list.add(this.getClientId());
		return list;
	}
	
	/**
	 * Injected.
	 */
	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();
	
	/**
     * Injected response builder for doing specific XHR things.
     *
     * @return ResponseBuilder for this request. 
     */
    public abstract ResponseBuilder getResponse();
}