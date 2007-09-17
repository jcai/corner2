/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-13
 */

package corner.orm.tapestry.jasper.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetSource;
import org.apache.tapestry.binding.BindingSource;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

import corner.model.IBlobModel;
import corner.orm.tapestry.jasper.JREntityDataSource;
import corner.orm.tapestry.jasper.TaskType;
import corner.orm.tapestry.jasper.exporter.IJasperExporter;
import corner.orm.tapestry.service.blob.BlobAsset;
import corner.orm.tapestry.service.blob.BlobService;
import corner.orm.tapestry.utils.ComponentResponseUtils;
import corner.util.VectorUtils;

/**
 * 导出实体的服务类.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JasperEntityLinkService implements IEngineService{


	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		
		Defense.isAssignable(parameter, Object[].class, "参数");
		Object[] ps = (Object[]) parameter;
		
		if(ps[2] == null && ps[3] == null){
			Defense.notNull(ps[2], "模板");
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ServiceConstants.PAGE, this.requestCycle.getPage()
				.getPageName());
		parameters.put(ServiceConstants.PARAMETER, parameter);
		
		return linkFactory.constructLink(this, post, parameters, true);
	}


	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		String activePageName = cycle.getParameter(ServiceConstants.PAGE);
		IPage page = cycle.getPage(activePageName);
		cycle.activate(page);
		
		Object[] parameters = linkFactory.extractListenerParameters(cycle);
		
		String downloadFileName = (String) parameters[0];
		String taskType = (String) parameters[1];
		String templatePath = (String) parameters[2];
		IBlobModel templateEntity = null;
		
		boolean isUsetemplatePath = true;
		
		if(templatePath == null){
			templateEntity = (IBlobModel) parameters[3];
			isUsetemplatePath = false;
		}
		
		IJasperExporter jasperAction = TaskType.valueOf(taskType).newInstance();
		try {
			
			//判断是从那里读取流
			InputStream is = isUsetemplatePath ? getAssetStream(page,templatePath) : getAssetStream(page,templateEntity);
			
			JasperPrint jasperPrint = getJasperPrint(is,page);
			
			JRExporter exporter = jasperAction.getExporter();
			//初始化
			jasperAction.setupExporter(exporter);
			
			//准备参数
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, VectorUtils.getCollection(jasperPrint));
			
			//设定下载文件名
			ComponentResponseUtils.constructResponse(downloadFileName, jasperAction.getSuffix(),cycle, response);
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream(new ContentType(jasperAction.getContentType())));
		
		
			//导出报表.
			exporter.exportReport();
			
		} catch (JRException e) {
			throw new ApplicationRuntimeException(e);
		}
		
	}
	
	/**
	 * 读取数据库流
	 * @param page 要返回的页面
	 * @param templateEntity 存放文件的entity
	 * @return
	 */
	private InputStream getAssetStream(IPage page, IBlobModel templateEntity) {
		BlobAsset asset = new BlobAsset(blobService,page.getRequestCycle(),templateEntity);
		return asset.getResourceAsStream();
	}

	/**
	 * 从配置中classpath/context/database中获得jasper
	 * @param page 要返回的页面
	 * @param template 模板
	 * @return
	 */
	private InputStream getAssetStream(IPage page, String template) {
		return assetSource.findAsset(page.getLocation().getResource(), template, page.getLocale(), page.getLocation()).getResourceAsStream();
	}

	/**
	 * 获得JasperPrint
	 * @param objects
	 * @throws JRException 
	 */
	private JasperPrint getJasperPrint(InputStream jasperInStream,IPage page) throws JRException{
		JasperPrint jasperPrint = null;
		jasperPrint = JasperFillManager.fillReport(jasperInStream, null, new JREntityDataSource(bindingSource,page));
		return jasperPrint;
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	public static final String SERVICE_NAME = "jasper";

	/** link factory */
	private LinkFactory linkFactory;

	/** response */
	private WebResponse response;

	/** request cycle * */
	private IRequestCycle requestCycle;
	
	/** assetSource * */
	private AssetSource assetSource;
	
	/** binding source **/
	private BindingSource bindingSource;
	
	/**
	 * 得到blob服务。
	 * @return blob服务
	 */
	private BlobService blobService;

	/**
	 * @param linkFactory The linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory linkFactory) {
		this.linkFactory = linkFactory;
	}

	/**
	 * @param requestCycle The requestCycle to set.
	 */
	public void setRequestCycle(IRequestCycle requestCycle) {
		this.requestCycle = requestCycle;
	}

	/**
	 * @param response The response to set.
	 */
	public void setResponse(WebResponse response) {
		this.response = response;
	}

	/**
	 * @param assetSource The assetSource to set.
	 */
	public void setAssetSource(AssetSource assetSource) {
		this.assetSource = assetSource;
	}



	/**
	 * @param bindingSource The bindingSource to set.
	 */
	public void setBindingSource(BindingSource bindingSource) {
		this.bindingSource = bindingSource;
	}

	/**
	 * @param blobService The blobService to set.
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}
}
