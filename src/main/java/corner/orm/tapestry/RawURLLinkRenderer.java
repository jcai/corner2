// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-11
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
import org.apache.tapestry.components.ILinkComponent;
import org.apache.tapestry.link.ILinkRenderer;

/**
 * 扩展tapestry的renderer,供JavaScript中产生URL.
 * 
 * @author Jun Tsai
 * @version $Revision:3677 $
 * @since 2006-4-18
 */
public class RawURLLinkRenderer implements ILinkRenderer {
	
	public void renderLink(IMarkupWriter writer, IRequestCycle cycle,
			ILinkComponent linkComponent) {
		writer.print(linkComponent.getLink(cycle).getAbsoluteURL(), true);
	}
}
