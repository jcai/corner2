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

package corner.orm.tapestry.component.textfield;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.AbstractWidget;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.json.JSONObject;

/**
 * 提供一个查询的Dialog
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class QueryDialog extends AbstractWidget{
	
	@Parameter(required=true)
	public abstract String getQueryPageName();
    public abstract String getBackgroundColor();
    
    public abstract float getOpacity();
	@InjectObject("service:tapestry.services.Page")
	public abstract IEngineService getPageService();
	
    
        /**
     * {@inheritDoc}
     */
    public void renderWidget(IMarkupWriter writer, IRequestCycle cycle)
    {
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
            JSONObject json = new JSONObject();
            json.put("bgColor", getBackgroundColor());
            json.put("bgOpacity", getOpacity());
            json.put("url",this.getPageService().getLink(false, this.getQueryPageName()).getAbsoluteURL());

            Map<String,Object> parms = new HashMap<String,Object>();
            parms.put("component", this);
            parms.put("props", json.toString());

            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
        }
    }
    
    /** injected. */
    @InjectScript("QueryDialog.script")
    public abstract IScript getScript();
}
