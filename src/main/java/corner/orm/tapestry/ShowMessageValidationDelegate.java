// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
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

package corner.orm.tapestry;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.FieldTracking;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;
/**
 * 能够显示错误消息的ValidationDelegate.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-10-21
 */
public class ShowMessageValidationDelegate extends ValidationDelegate {

	
	private static final long serialVersionUID = 8758959678347504102L;

	/**
	 * 
	 * @see org.apache.tapestry.valid.IValidationDelegate#writePrefix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writePrefix(IMarkupWriter writer, IRequestCycle cycle, IFormComponent component,
            IValidator validator) {
		if(this.isInError(component)){
			FieldTracking ft=this.findCurrentTracking();
			writer.printRaw("&nbsp;");
            writer.begin("font");
            writer.attribute("color", "red");
            
            writer.print("**");
            writer.end();
            ft.getErrorRenderer().render(writer,cycle);
		}
	}
	
	

}
