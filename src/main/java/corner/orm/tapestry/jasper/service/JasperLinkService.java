//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.jasper.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetSource;
import org.apache.tapestry.binding.BindingSource;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.web.WebResponse;

import corner.model.IBlobModel;
import corner.orm.tapestry.jasper.IJasperParameter;
import corner.orm.tapestry.jasper.JREntityDataSource;

/**
 * 抽象类
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class JasperLinkService implements IEngineService{
	
	private static final String MAIN_REPORT = "main.jasper";
	private static final int BUFFER = 2048;
	private static final String ZIP_SUFFIX = ".zip";
	
	private String templateType = null;


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
		
		String downloadFileName = (String) parameters[0];
		String taskType = (String) parameters[1];
		String templatePath = (String) parameters[2];
		IBlobModel templateEntity = null;
		
		boolean isUsetemplatePath = true;
		
		if(templatePath == null){
			templateEntity = (IBlobModel) parameters[3];
			isUsetemplatePath = false;
		}
		
		String detailEntity = (String) parameters[4];
		String detailCollection = (String) parameters[5];
		
		service(cycle,page,isUsetemplatePath,templatePath,templateEntity,downloadFileName,taskType,detailEntity,detailCollection);
	}
	
	
	/**
	 * 自己的方法
	 */
	protected abstract void service(IRequestCycle cycle, IPage page,
			boolean isUsetemplatePath, String templatePath,
			IBlobModel templateEntity, String downloadFileName,
			String taskType, String detailEntity, String detailCollection)
			throws IOException;

	/**
	 * 读取数据库流
	 * @param page 要返回的页面
	 * @param templateEntity 存放文件的entity
	 * @return
	 */
	protected InputStream getAssetStream(IBlobModel templateEntity) {
		byte[] data = templateEntity.getBlobData();
		
		InputStream is = new ByteArrayInputStream(data);
		
		if(templateEntity.getBlobName().toLowerCase().endsWith(ZIP_SUFFIX)){
			templateType = ZIP_SUFFIX;
		}else{
			templateType = null;
		}
		
		return is;
	}

	/**
	 * 从配置中classpath/context/database中获得jasper
	 * @param page 要返回的页面
	 * @param template 模板
	 * @return
	 */
	protected InputStream getAssetStream(IPage page, String template) {
		
		InputStream is = assetSource.findAsset(page.getLocation().getResource(), template, page.getLocale(), page.getLocation()).getResourceAsStream();
		
		if(template.toLowerCase().endsWith(ZIP_SUFFIX)){
			templateType = ZIP_SUFFIX;
		}else{
			templateType = null;
		}
		
		return is;
	}
	
	/**
	 * 获得制定zip名称的输入流
	 * @param is zip的输入流
	 * @param reportName 要获得的zip输入流名称
	 * @return 流
	 */
	public static void getZipReportInputStreamMap(InputStream is, Map parameters) {
		
		ZipInputStream zis = new ZipInputStream(is);
		BufferedOutputStream dest = null;
		ZipEntry entry = null;
		
		ByteArrayOutputStream fos = null;
		
		try {
			while ((entry = zis.getNextEntry()) != null) {
				
				System.out.println("Extracting: " + entry.getName() + "\t"
						+ entry.getSize() + "\t" + entry.getCompressedSize());
				
				int count;
				byte data[] = new byte[BUFFER];

				// write the files to the disk
				fos = new ByteArrayOutputStream((int)entry.getSize());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				
				dest.flush();
				dest.close();
				
				parameters.put(entry.getName(), new ByteArrayInputStream(fos.toByteArray()));
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得JasperPrint
	 * @param detailCollection 细节的计划名称
	 * @param detailEntity 循环用的entity geter
	 * @param objects
	 * @throws JRException 
	 */
	protected JasperPrint getJasperPrint(InputStream jasperInStream,IPage page,Object templateEntity ,String detailEntity, String detailCollection) throws JRException{
		JasperPrint jasperPrint = null;
		
		Map parameters = null;
		
		//如果要传递参数
		if(page instanceof IJasperParameter){
			parameters = ((IJasperParameter)page).getJasperParameters();
		}
		
		if(parameters == null){
			parameters = new HashMap();
		}
		
		if(templateType != null && templateType.equals(ZIP_SUFFIX)){
			getZipReportInputStreamMap(jasperInStream, parameters);
			jasperInStream = (InputStream) parameters.get(MAIN_REPORT);
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
	protected JRDataSource getDataSource(IPage page, String detailCollection, String detailEntity) {
		JREntityDataSource dataSource = null;
		
		if(detailEntity == null){
			dataSource = new JREntityDataSource(bindingSource,page);
		}else{
			dataSource = new JREntityDataSource(bindingSource,page,detailCollection,detailEntity);
		}
		
		return dataSource;
	}

	/** link factory */
	protected LinkFactory linkFactory;

	/** response */
	protected WebResponse response;

	/** request cycle * */
	protected IRequestCycle requestCycle;
	
	/** assetSource * */
	protected AssetSource assetSource;
	
	/** binding source **/
	protected BindingSource bindingSource;
	
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