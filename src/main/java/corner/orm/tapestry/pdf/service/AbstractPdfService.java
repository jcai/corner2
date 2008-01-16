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

package corner.orm.tapestry.pdf.service;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.web.WebResponse;

/**
 * 抽象的pdf服务类
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class AbstractPdfService {

	/** response */
	protected WebResponse response;

	/** request cycle * */
	protected IRequestCycle requestCycle;

	/** PDF 模板参数 * */
	public static final String TEMPLATE_PARAMETER_NAME = "PDFTMP";

	/** pdf 命名空间 * */
	public static final String PDF_PAGE_NAMESPACE = "pdf";

	/** pdf 文件后缀的文件名 * */
	protected final static String PDF_FILE_EXTENSION_NAME = ".pdf";

	/** 连接的工厂 * */
	protected LinkFactory _linkFactory;

	public void setLinkFactory(LinkFactory linkFactory) {
		_linkFactory = linkFactory;
	}

	/**
	 * @param _response
	 *            The _response to set.
	 */
	public void setResponse(WebResponse response) {
		this.response = response;
	}

	/**
	 * @param cycle
	 *            The requestCycle to set.
	 */
	public void setRequestCycle(IRequestCycle cycle) {
		requestCycle = cycle;
	}

}
