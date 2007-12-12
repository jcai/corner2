// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-04
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

package corner.orm.tapestry.component.textfield;

import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;

import corner.orm.tapestry.component.AbstractAny;


/**
 * 一个用来处理被选择的实体.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */

public abstract class SelectedField extends AbstractAny {
	@InjectScript("SelectedField.script")
	public abstract IScript getScript();

	/**
	 * @see corner.orm.tapestry.component.AbstractAny#exceptionMessage()
	 */
	@Override
	protected String exceptionMessage() {
		return "SelectedField";
	}
}
