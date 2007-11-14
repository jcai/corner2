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

import java.util.List;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetFactory;
import org.apache.tapestry.markup.MarkupWriterSource;
import org.apache.tapestry.services.RequestLocaleManager;
import org.apache.tapestry.web.WebResponse;

/**
 * 对prototype类型的请求响应.
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PrototypeResponseBuilder extends  org.apache.tapestry.services.impl.PrototypeResponseBuilder {
	
	

	
	public PrototypeResponseBuilder(IRequestCycle cycle, IMarkupWriter writer, List parts) {
		super(cycle, writer, parts);
		
	}

	
	public PrototypeResponseBuilder(IRequestCycle cycle, RequestLocaleManager localeManager, MarkupWriterSource markupWriterSource, WebResponse webResponse, AssetFactory assetFactory, String namespace) {
		super(cycle, localeManager, markupWriterSource, webResponse, assetFactory,
				namespace);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void beginBodyScript(IMarkupWriter writer, IRequestCycle cycle) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void endBodyScript(IMarkupWriter writer, IRequestCycle cycle) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeBodyScript(IMarkupWriter writer, String script,
			IRequestCycle cycle) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeExternalScript(IMarkupWriter normalWriter, String url,
			IRequestCycle cycle) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeImageInitializations(IMarkupWriter writer, String script,
			String preloadName, IRequestCycle cycle) {
		// does nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeInitializationScript(IMarkupWriter writer, String script) {
		// does nothing
	}

	public void addStatusMessage(IMarkupWriter writer, String category, String text) {
		// TODO Auto-generated method stub
		
	}

}
