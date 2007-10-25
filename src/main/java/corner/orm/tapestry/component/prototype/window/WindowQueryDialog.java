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

package corner.orm.tapestry.component.prototype.window;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.AbstractWidget;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.link.DirectLink;
import org.apache.tapestry.listener.ListenerInvoker;

/**
 * 使用prototype的弹出查询框
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class WindowQueryDialog extends AbstractWidget implements IDirect{

	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		IActionListener listener = getListener();

		if (listener == null)
			throw Tapestry.createRequiredParameterException(this, "listener");

		getListenerInvoker().invokeListener(listener, this, cycle);
	}
	
	
	/**
	 * @see org.apache.tapestry.dojo.IWidget#renderWidget(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	public void renderWidget(IMarkupWriter writer, IRequestCycle cycle) {
		
		if (!cycle.isRewinding()) {
            writer.begin(getTemplateTagName()); // use element specified
            renderIdAttribute(writer, cycle); // render id="" client id
            renderInformalParameters(writer, cycle);
        }
        
        renderBody(writer, cycle);
        
        if (!cycle.isRewinding()) {
            writer.end();
        }
        
        if (!cycle.isRewinding()) {
//        	{className: 'alphacube', title: 'google',
//				  url: 'http://www.google.com/'}
        	
        	
            JSONObject json = new JSONObject();
            json.put("className", "alphacube");
            json.put("title", "google");
            json.put("url", "http://www.google.com/");
//            json.put("selectFun",getOnSelectFunName());
//            if(this.getTitle()!=null)
//            	json.put("title",this.getTitle());
            
//            json.put("url",getUrl());
            

            Map<String,Object> parms = new HashMap<String,Object>();
            parms.put("component", this);
            parms.put("props", json.toString());
            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
        }
	}
	
	
	/**
	 * @return
	 */
	protected String getUrl() {
		Object[] serviceParameters = DirectLink
				.constructServiceParameters(getParameters());

		DirectServiceParameter dsp = new DirectServiceParameter(this,
				serviceParameters);

		String url = getDirectService().getLink(false, dsp).getAbsoluteURL();
		return url;
	}
	
    /**
	 * 当选中某一条记录的时候，响应的js函数
	 * @return
	 */
	@Parameter
	public abstract String getOnSelectFunName();
	@Parameter
	public abstract String getTitle();
	@Parameter(defaultValue="literal:black")
	public abstract String getBackgroundColor();
	@Parameter(defaultValue="0.4")
	public abstract float getOpacity();
	@Parameter
	public abstract String getBtnImage();
    
	
	
	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();
	
	@Parameter
	public abstract Object getParameters();
	
	/**
	 * 监听调用函数
	 */
	@Parameter(required = true)
	public abstract IActionListener getListener();
	
	/**
	 * 获得监听
	 */
	@InjectObject("infrastructure:listenerInvoker")
	public abstract ListenerInvoker getListenerInvoker();

	/** injected. */
	@InjectScript("WindowQueryDialog.script")
	public abstract IScript getScript();
	
}
