/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfEntityLink.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

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
