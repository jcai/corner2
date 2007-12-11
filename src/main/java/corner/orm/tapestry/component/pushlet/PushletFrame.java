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

package corner.orm.tapestry.component.pushlet;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;

/**
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class PushletFrame extends AbstractComponent implements IDirect{
	
	/**
	 * 查询消息使用的消息实体名称
	 * @return 保存消息的实体类名
	 */
	@Parameter(required=true)
	public abstract String getMessageClassName();
	
	/**
	 * 取得点击消息时候跳转到的消息列表
	 * @return {@link String}
	 */
	@Parameter(required=true)
	public abstract String getMessageListPageName();
	
    @InjectObject("service:corner.pushlet.PushletService")
    public abstract IEngineService getPushletService();
    
	@InjectScript("PushletFrame.script")
	public abstract IScript getScript();
	
	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		
	}
	
	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		boolean rewinding = cycle.isRewinding();
	   if (!rewinding)
	    {
		   writer.begin("span");
		   writer.appendAttribute("id", "_PublishFrameDiv");
		   writer.appendAttribute("name", "_PublishFrameDiv");
		   writer.end("span");
		   
		   ILink link = getPushletService().getLink(true, new DirectServiceParameter(this,new Object[]{this.getMessageClassName(),this.getMessageListPageName()}));		   
	        Map<String,Object> parms = new HashMap<String,Object>();
	        parms.put("url", link.getAbsoluteURL());
	        getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
	    }

	}

}
