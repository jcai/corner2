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

package corner.orm.tapestry.component.insert;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * 版本显示Insert
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionInsert extends org.apache.tapestry.components.Insert{
	
	/**
	 * @see org.apache.tapestry.components.Insert#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderComponent(writer, cycle);
		
		writer.beginEmpty("link");
		writer.attribute("rel", "stylesheet");
		writer.attribute("type", "text/css");
		writer.attribute("href", getIndicatorAsset().buildURL());
		
		if (!cycle.isRewinding()) {
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("component", this);
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
	}
	
	/**
	 * @see org.apache.tapestry.components.Insert#printText(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, java.lang.String)
	 */
	@Override
	protected void printText(IMarkupWriter writer, IRequestCycle cycle, String value) {
		writer.beginEmpty("span");
		writer.attribute("id", this.getClientId());
		super.printText(writer, cycle, value);
	}

	@Asset("classpath:/corner/orm/tapestry/component/insert/prototip/prototip.css")
	public abstract IAsset getIndicatorAsset();

	@InjectScript("VersionInsert.script")
	public abstract IScript getScript();
	
	/**
	 * 要在json串中显示的属性
	 */
	@Parameter(defaultValue = "literal:entity")
	public abstract String getEntityName();

	/**
	 * 要在json串中显示的属性
	 */
	public abstract String getShowProperty();
	
	/**
	 * 设置的json名字
	 */
	public abstract String getJsonName();
	
	/**
	 * 设置的json名字
	 */
	@Parameter(defaultValue = "literal:flags2")
	public abstract String getJsonOtherName();
}
