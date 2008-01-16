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

import java.io.IOException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetFactory;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.services.ResponseContributor;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;

/**
 * 对pdf输出文件进行响应.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfResponseContributorImpl implements ResponseContributor {

	public static final String PDF_PARAMETER_IDENTIFIER = "PDF-REQUEST";
    
	protected WebResponse _webResponse;
    protected AssetFactory _assetFactory;

	private WebRequest _webRequest;

	/**
	 * @see org.apache.tapestry.services.ResponseContributor#createBuilder(org.apache.tapestry.IRequestCycle)
	 */
	public ResponseBuilder createBuilder(IRequestCycle cycle)
			throws IOException {
		return new PdfResponseBuilder(_webResponse, _assetFactory, _webResponse.getNamespace());
	}

	/**
	 * @see org.apache.tapestry.services.ResponseContributor#handlesResponse(org.apache.tapestry.IRequestCycle)
	 */
	public boolean handlesResponse(IRequestCycle cycle) {
		//TODO 考虑采用Http Header的方式来判定
		return isPdfRequest(_webRequest);
	}
	public void setWebResponse(WebResponse webResponse)
    {
        _webResponse = webResponse;
    }
    
    public void setAssetFactory(AssetFactory factory)
    {
        _assetFactory = factory;
    }
	public void setWebRequest(WebRequest webRequest) {
		_webRequest = webRequest;
	}
	public static boolean isPdfRequest(WebRequest request) {
		return request.getParameterValue(PdfResponseContributorImpl.PDF_PARAMETER_IDENTIFIER) != null;
		
	}
}
