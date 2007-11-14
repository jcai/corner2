// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-07
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

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.form.TextArea;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class WinTextArea extends TextArea{

	/**
	 * @see org.apache.tapestry.form.TextArea#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderFormComponent(writer, cycle);
		if (!cycle.isRewinding()) {
            Map<String,Object> parms = new HashMap<String,Object>();
            parms.put("component", this);
            
            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
        }
	}
	
	/** injected. */
	@InjectScript("WinTextArea.script")
	public abstract IScript getScript();
}
