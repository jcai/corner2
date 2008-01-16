// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
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

package corner.orm.tapestry.pdf.link;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.link.AbstractLinkComponent;

/**
 * 一个展示pdf连接的组件 <code>
 * 	&lt;a href="xxx" jwcid="pdf:EntityLink" template="classpath:xxx.pdf" entity="ognl:entity"/&gt;
 * </code>
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfEntityLink extends AbstractLinkComponent implements
		IPdfLinkParameters {

	@InjectObject("engine-service:pdfe")
	public abstract IEngineService getPdfService();

	@Parameter(required = true)
	public abstract String getTemplate();

	@Parameter(required = true)
	public abstract Object getEntity();

	/**
	 * 构造pdf连接
	 * 
	 * @see org.apache.tapestry.link.AbstractLinkComponent#getLink(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public ILink getLink(IRequestCycle cycle) {
		Object[] parameters = new Object[] { getTemplate(), getEntity(),
				isSaveAsFile(), getDownloadFileName() };
		return getPdfService().getLink(false, parameters);
	}
}
