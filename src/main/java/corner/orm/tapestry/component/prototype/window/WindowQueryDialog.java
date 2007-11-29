// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-25
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


import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;

/**
 * 使用prototype的弹出查询框
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class WindowQueryDialog extends WindowDialog{
	
	/**
	 * @see corner.orm.tapestry.component.prototype.window.WindowDialog#getScript()
	 */
	@InjectScript("WindowQueryDialog.script")
	public abstract IScript getScript();
}
