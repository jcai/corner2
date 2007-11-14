// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-29
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.component.prototype.window;

import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.components.Any;
import org.apache.tapestry.services.LinkFactory;


/**
 * 一个用来处理被选择的实体.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */

public abstract class WinSelectedField extends Any {
	@InjectScript("WinSelectedField.script")
	public abstract IScript getScript();

	/**
	 * @see org.apache.tapestry.components.Any#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String element = getElement();
        
        if (element == null)
            throw new ApplicationRuntimeException("WinSelectedField", this,
                    null, null);

        boolean rewinding = cycle.isRewinding();

        if (!rewinding)
        {
            writer.begin(element);
            
            renderInformalParameters(writer, cycle);
            if (getId() != null && !isParameterBound("id"))
                renderIdAttribute(writer, cycle);
        }
        
        Object[] parameters = getLinkFactory().extractListenerParameters(cycle);
        
        String onSelectFunName = null;
        
        if(parameters.length == 0){
        	onSelectFunName = getOnSelectFunName();
        }else{
        	onSelectFunName = (String) parameters[0];
        }
        
        
        
        if (!rewinding)
        {
            writer.end(element);
        }
        
		PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("id", this.getClientId());
		parms.put("onSelectFunName", onSelectFunName);
		getScript().execute(this, cycle, prs, parms);
	}
	
	@Parameter(defaultValue = "literal:selectRecord")
	public abstract String getOnSelectFunName();
	
	@InjectObject("infrastructure:linkFactory")
	public abstract LinkFactory getLinkFactory();

}
