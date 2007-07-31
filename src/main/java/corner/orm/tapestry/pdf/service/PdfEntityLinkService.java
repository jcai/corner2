/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfEntityLinkService.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.services.ServiceConstants;

import corner.orm.tapestry.pdf.PdfEntityPage;
import corner.orm.tapestry.pdf.PdfMessages;
import corner.orm.tapestry.utils.ComponentResponseUtils;

/**
 * 一个用来展示pdf的服务类，该类仅仅提供了一个参数来进行获取对应的连接.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfEntityLinkService extends AbstractPdfService implements
		IEngineService {
	/** PDF 实体连接的服务名称 * */
	public static final String SERVICE_NAME = "pdfe";

	/**
	 * 构造连接
	 * 
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean,
	 *      java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		Defense.isAssignable(parameter, Object[].class, "参数");

		Object[] ps = (Object[]) parameter;

		String templatePath = (String) ps[0];
		Defense.notNull(templatePath, "模板路径");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(TEMPLATE_PARAMETER_NAME, templatePath);
		parameters.put(ServiceConstants.PARAMETER, new Object[] { ps[1], ps[2],
				ps[3] });
		parameters.put(PdfResponseContributorImpl.PDF_PARAMETER_IDENTIFIER,
				this.getName());
		return _linkFactory.constructLink(this, false, parameters, false);
	}

	/**
	 * 得到服务的名称
	 * 
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}

	/**
	 * 
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		ResponseBuilder builder = cycle.getResponseBuilder();
		String template = cycle.getParameter(TEMPLATE_PARAMETER_NAME);
		// 激活pdf模板
		cycle.activate(PDF_PAGE_NAMESPACE + ":" + template);

		IPage page = cycle.getPage();
		if (page instanceof PdfEntityPage) {
			Object[] serviceParameters = _linkFactory
					.extractListenerParameters(cycle);
			
			((PdfEntityPage) page).setEntity(serviceParameters[0]);
			((PdfEntityPage) page).setTemplate(template);
			
			boolean saveAsFile = (Boolean) serviceParameters[1];
			if (saveAsFile) {
				String fileName = (String) serviceParameters[2];
				// 构造下载文件的response
				ComponentResponseUtils.constructResponse(fileName,
						PDF_FILE_EXTENSION_NAME, requestCycle, response);
			}
			
			builder.renderResponse(cycle);
		} else {
			throw new ApplicationRuntimeException(PdfMessages
					.invalidateEntityPageInstance(page.getClass().getName()));
		}
	}
}
