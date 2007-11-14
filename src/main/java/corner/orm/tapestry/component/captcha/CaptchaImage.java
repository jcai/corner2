// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-10
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

package corner.orm.tapestry.component.captcha;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.engine.IEngineService;

/**
 * 输出验证图像的组件
 * 
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public abstract class CaptchaImage extends AbstractComponent {

	@InjectObject("service:corner.service.CaptchaService")
	public abstract IEngineService getCaptchaService();

	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		writer.begin("img");
		writer.attribute("src", getCaptchaService().getLink(false, null)
				.getURL());
		renderInformalParameters(writer, cycle);
		writer.end();
	}

}
