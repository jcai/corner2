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

package corner.orm.tapestry.component.gain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.Submit;
import org.apache.tapestry.spec.IContainedComponent;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class GainPoint extends BaseComponent{
	
	/**
	 * @return
	 */
	@InjectScript("GainPoint.script")
	public abstract IScript getScript();
	
	/**
	 * 
	 */
	@Parameter(defaultValue = "literal:aaa")
	public abstract String getElementName();
	
	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		Set entries = cycle.getPage().getComponents().entrySet();
		
		Iterator iter = entries.iterator();
		
		StringBuffer reNameTemps = new StringBuffer("{\\\"reNames\\\": [");
		
		String reNames = null;
		
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
//			Object key = entry.getKey();
			
			
			if(!(entry.getValue() instanceof IFormComponent) || (entry.getValue() instanceof Submit)){
				continue;
			}
			
			IFormComponent component = (IFormComponent)entry.getValue();
			
			IContainedComponent cc = component.getContainedComponent();
			
//			if(cc.getBinding("id").equals(this.getClientId()) ){
				reNameTemps.append("\\\"").append(component.getClientId()).append("\\\",");
//			}
		}
		
		reNames = reNameTemps.toString().substring(0,reNameTemps.length()-1);
		
		reNames += "]}";
		
		super.renderComponent(writer, cycle);
		
		PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
		
		Map<String, Object> scriptParms = new HashMap<String, Object>();
		
		
		scriptParms.put("gpid",		  this.getClientId());
		scriptParms.put("elementName",		  this.getElementName());
		scriptParms.put("reNames",		  reNames);
		
		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
		
		
	}
	
	

//	/**
//	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
//	 */
//	@Override
//	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
//		
//		
//		
//		String[] values = cycle.getParameters(getName());
//		
//		for(String s : values){
//			System.out.println(s);
//		}
//	}

	
}
