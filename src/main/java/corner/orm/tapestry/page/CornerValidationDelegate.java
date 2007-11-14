// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-18
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

package corner.orm.tapestry.page;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.FieldTracking;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;

/**
 * 
 * 对验证的窗体回填信息的代理类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class CornerValidationDelegate extends ValidationDelegate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4047923735009997385L;

	
	/**
	 * @see org.apache.tapestry.valid.ValidationDelegate#writePrefix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writePrefix(IMarkupWriter writer, IRequestCycle cycle, IFormComponent component, IValidator validator) {
		if(this.isInError(component)){
			FieldTracking ft=this.findCurrentTracking();
			writer.begin("div");
			writer.attribute("class","error-div");
			ft.getErrorRenderer().render(writer,cycle);
			writer.end("div");
            
		}
		
	}
	/**
	 * @see org.apache.tapestry.valid.ValidationDelegate#writeSuffix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writeSuffix(IMarkupWriter arg0, IRequestCycle arg1, IFormComponent arg2, IValidator arg3) {
		//do nothing
	}
}
