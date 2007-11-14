// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-19
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

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;

/**
 * 提供一个查询的Dialog,此查询页面的url，通过PageLink来获取
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class PageQueryDialog extends AbstractQueryDialog {

	@Parameter(required = true)
	public abstract String getQueryPageName();
	
	@InjectObject("service:tapestry.services.Page")
	public abstract IEngineService getPageService();

	/**
	 * @see corner.orm.tapestry.component.textfield.AbstractQueryDialog#getUrl()
	 */
	@Override
	protected String getUrl() {
		String url=this.getPageService().getLink(false, this.getQueryPageName()).getAbsoluteURL();
        return url;
	}
}
