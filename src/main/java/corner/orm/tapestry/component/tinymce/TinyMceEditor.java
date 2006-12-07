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

package corner.orm.tapestry.component.tinymce;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * 提供一个编辑框。
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class TinyMceEditor extends AbstractComponent{
	
	@Parameter(defaultValue="literal:exact")
	public abstract String getMode();
	@Parameter
	public abstract String getElements();
	@Parameter(defaultValue="literal:zh_cn_utf8")
	public abstract String getLanguage();
	
	@Parameter(defaultValue="literal:advanced")
	public abstract String getTheme();
	
	@Parameter(defaultValue="literal:500")
	public abstract String getWidth();
	@Parameter(defaultValue="literal:400")
	public abstract String getHeight();
	@Parameter(defaultValue="literal:table,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,zoom,media,searchreplace,print,contextmenu,paste,directionality,fullscreen")
	public abstract String getPlugins();
	@Parameter
	public abstract String getContentCss();
	@Parameter(defaultValue="ognl:false")
	public abstract boolean isDebug();
	
	
	
	

	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("mode",getMode());
		map.put("elements",getElements());
		map.put("language",getLanguage());
		map.put("theme",getTheme());
		map.put("width",getWidth());
		map.put("height",getHeight());
		map.put("plugins",getPlugins());
		map.put("contentCss",getContentCss());
		map.put("debug",String.valueOf(isDebug()));
		
		PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
		getScript().execute(this,cycle,pageRenderSupport,map);
	}
	@InjectScript("TinyMceEditor.script")
	public abstract IScript getScript();

}
