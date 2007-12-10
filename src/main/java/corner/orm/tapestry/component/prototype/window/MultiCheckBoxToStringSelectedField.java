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

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.components.Any;

/**
 * 多选择钮选择返回value
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class MultiCheckBoxToStringSelectedField extends Any{
	@InjectScript("MultiCheckBoxToStringSelectedField.script")
	public abstract IScript getScript();
	
	/**
	 * @see org.apache.tapestry.components.Any#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String element = getElement();
        
        if (element == null)
            throw new ApplicationRuntimeException("MultiCheckBoxToStringSelectedField", this,
                    null, null);

        boolean rewinding = cycle.isRewinding();

        if (!rewinding){
            writer.begin(element);
	            renderInformalParameters(writer, cycle);
	            if (getId() != null && !isParameterBound("id"))
	                renderIdAttribute(writer, cycle);
            writer.end(element);
        }
        
		PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("id", this.getClientId());
		getScript().execute(this, cycle, prs, parms);
	}
}
