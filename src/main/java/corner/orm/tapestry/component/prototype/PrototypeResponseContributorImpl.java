// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-06
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

package corner.orm.tapestry.component.prototype;

import java.io.IOException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetFactory;
import org.apache.tapestry.markup.MarkupWriterSource;
import org.apache.tapestry.services.RequestLocaleManager;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.services.ResponseContributor;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;

/**
 * 对 Prototype类型的请求响应.
 * <ul>
 * <li>代码参考Tacos代码，见: http://tacos.sf.net .</li>
 * <li>prototype 站点： http://www.prototypejs.org/</li>
 * </ul>
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PrototypeResponseContributorImpl implements  ResponseContributor{
	/** prototype 发生请求时候给定的header * */
	public static final String PROTOTYPE_VERSION_HEADER = "X-Prototype-Version";

	private RequestLocaleManager _localeManager;

	private MarkupWriterSource _markupWriterSource;

	private WebResponse _webResponse;

	private WebRequest _webRequest;

	private AssetFactory _assetFactory;

	 /**
     * {@inheritDoc}
     */
    public ResponseBuilder createBuilder(IRequestCycle cycle)
            throws IOException
    {
        return new PrototypeResponseBuilder(cycle, _localeManager, _markupWriterSource,
                                            _webResponse, _assetFactory, _webResponse.getNamespace());
    }

    /**
     * {@inheritDoc}
     */
    public boolean handlesResponse(IRequestCycle cycle)
    {
        return _webRequest.getHeader(PROTOTYPE_VERSION_HEADER) != null;
    }

    public void setLocaleManager(RequestLocaleManager localeManager)
    {
        _localeManager = localeManager;
    }

    public void setMarkupWriterSource(MarkupWriterSource markupWriterSource)
    {
        _markupWriterSource = markupWriterSource;
    }

    public void setWebResponse(WebResponse webResponse)
    {
        _webResponse = webResponse;
    }

    public void setWebRequest(WebRequest webRequest)
    {
        _webRequest  = webRequest;
    }

    public void setAssetFactory(AssetFactory factory)
    {
        _assetFactory = factory;
    }

	

}
