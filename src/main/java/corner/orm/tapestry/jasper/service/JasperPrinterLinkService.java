/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-13
 */

package corner.orm.tapestry.jasper.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
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
import corner.orm.tapestry.jasper.IJasperParameter;
import corner.orm.tapestry.jasper.JREntityDataSource;

/**
 * 导出实体的服务类.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JasperPrinterLinkService implements IEngineService{


	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		
		Defense.isAssignable(parameter, Object[].class, "参数");
		Object[] ps = (Object[]) parameter;
		
		if(ps[2] == null && ps[3] == null){
			Defense.notNull(ps[2], "模板");
		}
		
		//如果细节配置不完全
		if((ps[4] != null && ps[5] == null)||(ps[4] == null && ps[5] != null)){
			Defense.notNull(ps[4], "细节设置不充分");
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
		
		String templatePath = (String) parameters[2];
		IBlobModel templateEntity = null;
		
		boolean isUsetemplatePath = true;
		
		if(templatePath == null){
			templateEntity = (IBlobModel) parameters[3];
			isUsetemplatePath = false;
		}
		
		String detailEntity = (String) parameters[4];
		String detailCollection = (String) parameters[5];
		
		try {
			//判断是从那里读取流
			InputStream is = isUsetemplatePath ? getAssetStream(page,templatePath) : getAssetStream(templateEntity);
			
			JasperPrint jasperPrint = getJasperPrint(is,page,detailEntity,detailCollection);
			
			ObjectOutputStream os = new ObjectOutputStream(response.getOutputStream(new ContentType()));
			os.writeObject(jasperPrint);
			os.close();
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
	private InputStream getAssetStream(IBlobModel templateEntity) {
		byte[] data=templateEntity.getBlobData();
		return new ByteArrayInputStream(data);
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
	 * @param detailCollection 细节的计划名称
	 * @param detailEntity 循环用的entity geter
	 * @param objects
	 * @throws JRException 
	 */
	private JasperPrint getJasperPrint(InputStream jasperInStream,IPage page, String detailEntity, String detailCollection) throws JRException{
		JasperPrint jasperPrint = null;
		
		Map parameters = null;
		
		//如果要传递参数
		if(page instanceof IJasperParameter){
			parameters = ((IJasperParameter)page).getJasperParameters();
		}
		
		jasperPrint = JasperFillManager.fillReport(jasperInStream, parameters, getDataSource(page,detailCollection,detailEntity));
		return jasperPrint;
	}
	
	/**
	 * 获得数据源
	 * @param page
	 * @param detailCollection
	 * @param detailEntity
	 * @return
	 */
	private JRDataSource getDataSource(IPage page, String detailCollection, String detailEntity) {
		JREntityDataSource dataSource = null;
		
		if(detailEntity == null){
			dataSource = new JREntityDataSource(bindingSource,page);
		}else{
			dataSource = new JREntityDataSource(bindingSource,page,detailCollection,detailEntity);
		}
		
		return dataSource;
	}



	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	public static final String SERVICE_NAME = "jasperPrinter";

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
}
