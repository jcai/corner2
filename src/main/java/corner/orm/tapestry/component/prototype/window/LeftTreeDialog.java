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

import java.util.Map;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONArray;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class LeftTreeDialog extends WindowDialog{
	
	
	/**
	 * @see corner.orm.tapestry.component.prototype.window.WindowDialog#appendScriptParms(java.util.Map, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void appendScriptParms(Map<String, Object> parms,
			IRequestCycle cycle) {
		parms.put("page", cycle.getPage().getPageName());
		if(getDependFields()!=null){
			JSONArray json = jsonDependFields();
			parms.put("dependFields", json.toString().replaceAll("\"", "'"));
		}
	}

	/**
	 * 返回json数组
	 */
	private JSONArray jsonDependFields() {
		JSONArray json = new JSONArray();
		
		String [] dependFields = getDependFields().split(",");
		
		for(String s : dependFields){
			json.put(s);
		}
		
		return json;
	}

	@Parameter(required = true)
	public abstract String getQueryClassName();
	
	@Parameter
	public abstract String getDependFields();
	
	@Parameter(defaultValue = "literal:My")
	public abstract String getTitle();
	
	/**
	 * @see corner.orm.tapestry.component.prototype.window.WindowDialog#getScript()
	 */
	@InjectScript("LeftTreeDialog.script")
	public abstract IScript getScript();
}
