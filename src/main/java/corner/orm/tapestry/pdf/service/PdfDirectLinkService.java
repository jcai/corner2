/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-12
 */

package corner.orm.tapestry.pdf.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.EngineMessages;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.services.ServiceConstants;

import corner.orm.tapestry.pdf.IPdfDirect;
import corner.orm.tapestry.pdf.link.PdfDirectServiceParameter;
import corner.orm.tapestry.utils.ComponentResponseUtils;

/**
 * 一个响应PDF跳转的连接服务类.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfDirectLinkService extends AbstractPdfService implements
		IEngineService {

	/** 服务的名称 * */
	public static final String SERVICE_NAME = "pdfd";

	/**
	 * 是否保存为文件
	 */
	private static final String IS_SAVE_AS_FILE = "is_save_as_file";

	/**
	 * 下载文件名称
	 */
	private static final String DOWNLOAD_FILE_NAME = "download_file_name";

	/**
	 * 构造连接
	 * 
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean,
	 *      java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		Defense.isAssignable(parameter, PdfDirectServiceParameter.class,
				"parameter");

		PdfDirectServiceParameter pdsp = (PdfDirectServiceParameter) parameter;

		IComponent component = pdsp.getDirect();

		IPage activePage = requestCycle.getPage();

		IPage componentPage = component.getPage();

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(ServiceConstants.PAGE, activePage.getPageName());
		parameters.put(ServiceConstants.COMPONENT, component.getIdPath());
		parameters.put(ServiceConstants.CONTAINER,
				componentPage == activePage ? null : componentPage
						.getPageName());
		// 是否采用pdf服务的标识，由此标识才能确定采用的ResponseBuilder
		parameters.put(PdfResponseContributorImpl.PDF_PARAMETER_IDENTIFIER,
				this.getName());

		parameters.put(IS_SAVE_AS_FILE, String.valueOf(pdsp.isSaveAsFile()));
		parameters.put(DOWNLOAD_FILE_NAME, pdsp.getDownLoadFileName());

		parameters.put(ServiceConstants.PARAMETER, pdsp.getServiceParameters());

		return _linkFactory.constructLink(this, false, parameters, false);
	}

	/**
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

		// 得到响应的参数
		String componentId = cycle.getParameter(ServiceConstants.COMPONENT);
		String componentPageName = cycle
				.getParameter(ServiceConstants.CONTAINER);
		String activePageName = cycle.getParameter(ServiceConstants.PAGE);

		IPage page = cycle.getPage(activePageName);

		cycle.activate(page);

		IPage componentPage = componentPageName == null ? page : cycle
				.getPage(componentPageName);

		// 得到对应组件，好获取对应的参数
		IComponent component = componentPage.getNestedComponent(componentId);

		IPdfDirect direct = null;

		try {
			direct = (IPdfDirect) component;
		} catch (ClassCastException ex) {
			throw new ApplicationRuntimeException(EngineMessages
					.wrongComponentType(component, IPdfDirect.class),
					component, null, ex);
		}

		// 反序列化服务参数
		Object[] parameters = _linkFactory.extractListenerParameters(cycle);
		// 响应Listener
		triggerComponent(cycle, direct, parameters);

		boolean saveAsFile = Boolean.parseBoolean(cycle.getParameter(IS_SAVE_AS_FILE));
		if (saveAsFile) {// 如果保存为文件
			// 构造下载文件的response
			ComponentResponseUtils.constructResponse(cycle
					.getParameter(DOWNLOAD_FILE_NAME), PDF_FILE_EXTENSION_NAME,
					requestCycle, response);
		}

		// 渲染pdf页面.
		ResponseBuilder builder = cycle.getResponseBuilder();
		builder.renderResponse(cycle);

	}

	// 响应事件
	protected void triggerComponent(IRequestCycle cycle, IPdfDirect direct,
			Object[] parameters) {
		cycle.setListenerParameters(parameters);
		direct.trigger(cycle);
	}
}
