/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: PrototypeResponseBuilder.java 7001 2007-07-02 01:01:16Z jcai $
 * created at:2007-04-23
 */
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
