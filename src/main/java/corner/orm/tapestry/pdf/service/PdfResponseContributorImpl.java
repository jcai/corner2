/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfResponseContributorImpl.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

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
