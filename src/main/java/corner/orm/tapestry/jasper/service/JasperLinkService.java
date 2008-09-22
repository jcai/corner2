// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-26
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

package corner.orm.tapestry.jasper.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.web.WebResponse;

import corner.model.IBlobModel;
import corner.orm.tapestry.jasper.IJasperParameter;
import corner.orm.tapestry.jasper.JREntityDataSource;
import corner.orm.tapestry.page.relative.IPageRooted;

/**
 * 抽象类
 * @author <a href="mailto:renais@bjmaxinfo.com">renais</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class JasperLinkService implements IEngineService{	
	
	/**
	 * 报表包含子报表时,指定的主报表名.将对名为main.jasper的报表进行填充.
	 */
	private static final String MAIN_REPORT = "main.jasper";
	
	private static final int BUFFER = 2048;
	
	/**
	 * 多张报表时,用于标识是否需要detail.用于zip包报表名称如:1detail.jasper.
	 */
	private static final String DETAIL = "detail";
	
	/**
	 * 多张报表时,用于记录前几张报表的页数.将它传入下一个报表从而知道正确的当前页数.
	 * 在非第一张报表里使用:$P{pageNumberAdd}+$V{PAGE_NUMBER}
	 */
	private static final String PAGE_NUMBER_ADD = "pageNumberAdd";
	
	/**
	 * 针对报表有特殊的要求而写的一个属性文件.现在主要是:page,multiPage,fetchFirstPage,isIgnoreDetail.
	 * page:指定使用的页面类
	 * multiPage:是否是多报表.
	 * fetchFirstPage:是否只要第一页.
	 * isIgnoreDetail:是否忽略detail.使detailEntity,detailCollection为null.
	 * reportDownloadName:指定下载的报表的名称.
	 * 例:{"page":"sample/SampleMainMaterial","multiPage":"true","fetchFirstPage":"true","isIgnoreDetail":"true","reportDownloadName":"客户报价单"} 
	 */
	protected static final String TEMPLATE_PAGE = "jasper.properties";
	
	/**
	 * 用于jasper.properties里指定此报表使用的页面类. 
	 */
	protected static final String PAGE = "page";
	
	/**
	 * 用于jasper.properties里判断是否是多报表.
	 */
	protected static final String MULTI_PAGE = "multiPage";
	
	/**
	 * 用于jasper.properties里判断是否只要第一页.
	 */
	protected static final String FETCH_FIRST_PAGE = "fetchFirstPage";
	
	/**
	 * 用于jasper.properties里判断是否忽略detail.
	 */
	protected static final String IS_IGNORE_DETAIL = "isIgnoreDetail";
	
	/**
	 *  用于jasper.properties里指定下载的报表的名称.
	 */
	protected static final String REPORT_DOWNLOAD_NAME = "reportDownloadName";
	
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
		Object reportEntity = parameters[6];
		
		IBlobModel templateEntity = null;
		
		boolean isUsetemplatePath = true;
		
		if(templatePath == null){
			templateEntity = (IBlobModel) parameters[3];
			isUsetemplatePath = false;
		}
		
		//设置reportEntity到page的rootedObject.
		if(reportEntity != null && page instanceof IPageRooted){
			((IPageRooted<Object,Object>)page).setRootedObject(reportEntity);
		}
		
		String detailEntity = (String) parameters[4];
		String detailCollection = (String) parameters[5];
		
		service(cycle,page,isUsetemplatePath,templatePath,templateEntity,downloadFileName,taskType,detailEntity,detailCollection);
	}
	
	/**
	 * 自己的方法
	 * @param cycle
	 * @param page 与特定报表对应的页面类
	 * @param isUsetemplatePath 判断是从哪里读取流
	 * @param templatePath 报表模板的路径
	 * @param templateEntity 存放报表的实体类
	 * @param downloadFileName 报表保存的文件名
	 * @param taskType 报表保存的文件格式
	 * @param detailEntity 报表detail域用于循环的实体类
	 * @param detailCollection 报表detail域用于循环的集合
	 * @throws IOException
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
		return new ByteArrayInputStream(data);
	}

	/**
	 * 从配置中classpath/context/database中获得jasper
	 * @param page 要返回的页面
	 * @param template 模板
	 * @return
	 */
	protected InputStream getAssetStream(IPage page, String template) {
		return assetSource.findAsset(page.getLocation().getResource(), template, page.getLocale(), page.getLocation()).getResourceAsStream();
	}
	
	/**
	 * 通过输入流判断是否是zip文件
	 * @param readStream
	 * @return
	 */
	public static boolean isZipFile(InputStream readStream) {
		byte[] magic = new byte[2];
		readStream.mark(2048);
		try {
			if (readStream.read(magic) > 1) {
				if ((magic[0] == 'P') && (magic[1] == 'K')) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//最终重置流状态
			try {
				readStream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * 获得制定zip名称的输入流
	 * 
	 * @param is
	 *            zip的输入流
	 * @param reportName
	 *            要获得的zip输入流名称
	 * @return 流
	 */
	@SuppressWarnings("unchecked")
	public static void getZipReportInputStreamMap(InputStream is, Map<Object, Object> parameters) {
		
		ZipInputStream zis = new ZipInputStream(is);
		BufferedOutputStream dest = null;
		ZipEntry entry = null;
		
		ByteArrayOutputStream fos = null;
		
		try {
			while ((entry = zis.getNextEntry()) != null) {
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
			throw new ApplicationRuntimeException(e);
		}
	}

	/**
	 * @param jasperInStream 获取打印对象的流
	 * @param parameters 报表的参数
	 * @return
	 * @throws JRException
	 */
	@SuppressWarnings("unchecked")
	protected JasperPrint getJasperPrint(InputStream jasperInStream, IPage page, Map parameters, String detailEntity, String detailCollection, int pageNumber) throws JRException{					
		//如果要传递参数(子报表等)
		if(page instanceof IJasperParameter){			
			Map param = ((IJasperParameter)page).getJasperParameters();
			if(param.size()!=0){
				for(Iterator i = (Iterator) param.keySet().iterator(); i.hasNext();){
					String key = (String) i.next();
					parameters.put(key, param.get(key));
				}
			}
		}
		//前边已填充报表则设置参数到报表
		if(pageNumber >0){
			parameters.put(PAGE_NUMBER_ADD, pageNumber);
		}
		if(parameters.containsKey(MAIN_REPORT)){		
			jasperInStream = (InputStream) parameters.get(MAIN_REPORT);
		}

		return JasperFillManager.fillReport(jasperInStream, parameters, getDataSource(page,detailCollection,detailEntity));
	}
	
	/**
	 * @param jasperInStream 获取打印对象的流
	 * @param parameters 报表的参数
	 * @throws JRException
	 * @throws IOException
	 */
	protected void getJasperParameters(InputStream jasperInStream, Map<Object, Object> parameters) throws JRException, IOException{		
		if(isZipFile(jasperInStream)){				
			getZipReportInputStreamMap(jasperInStream, parameters);
		}
	}
	
	/**
	 * 获取参数文件的內容
	 * @param is
	 * @return 返回properties文件的內容
	 * @throws IOException
	 */
	protected static String getLinkParameter(InputStream is) throws IOException{
		InputStreamReader stream = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(stream);		
		return br.readLine();
	}

	/**
	 * 得到JasperPrint的list.用于一个报表有多页的情况(非循环多页)
	 * <p>
	 * 实现方法:通过读取zip包,将zip包里每一个报表文件进行填充并放入List返回.
	 * <p>
	 * 注意:zip包里报表名命名规则:第一张应该为1.jasper,依次类推;若报表有detail,则在数字后边加上detail如2detail.jasper;
	 * 若报表有子报表则命名时后缀为.zip有detail则加,无则不加.
	 * @param jasperInStream
	 * @param page
	 * @param parameters
	 * @param detailEntity
	 * @param detailCollection
	 * @return
	 * @throws JRException
	 * @throws IOException
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	protected List<JasperPrint> getJasperPrintList(IRequestCycle cycle, InputStream jasperInStream,
			IPage page, Map<Object, Object> parameters, String detailEntity, String detailCollection) throws JRException, IOException, ParseException {

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		
		int pageNumber = 0;	
		JasperPrint jasperPrint = null;	
		Object[] jasperName = parameters.keySet().toArray();
		Arrays.sort(jasperName);
		
		for(int i = 0; i < jasperName.length; i++) {		
		
			jasperInStream = (InputStream) parameters.get(jasperName[i]);
			getJasperParameters(jasperInStream, parameters);
			IPageRooted<Object, Object> activePage = (IPageRooted<Object, Object>) page;
			
			if(parameters.containsKey(TEMPLATE_PAGE)) {	
				
				InputStream propStream = (InputStream)parameters.get(TEMPLATE_PAGE);			
				String jsonParam = getLinkParameter(propStream);	
				parameters.remove(TEMPLATE_PAGE);
				
				if(jsonParam!=null){
					JSONObject json = new JSONObject(jsonParam);	
					
					String activePageName = null;
					if(json.has(PAGE)){
						activePageName = (String)json.get(PAGE);	
						activePage = (IPageRooted<Object, Object>)cycle.getPage(activePageName);		
						((IPageRooted<Object, Object>) activePage).setRootedObject(((IPageRooted<Object, Object>)page).getRootedObject());			
						cycle.activate(activePage);
					}
				}
			}
			
			if(((String)jasperName[i]).substring(0, ((String)jasperName[i]).indexOf(".")).endsWith(DETAIL)){
				
				jasperPrint = this.getJasperPrint(jasperInStream, activePage, parameters, detailEntity, detailCollection, pageNumber);
			}else{
				
				jasperPrint = this.getJasperPrint(jasperInStream, activePage, parameters, null, null, pageNumber);	
			}
			
			jasperPrintList.add(jasperPrint);
			
			int pageSize = jasperPrint.getPages().size();
			
			pageNumber += pageSize;   //将前边的JasperPrint的页数进行记录
			
		}
	
		return jasperPrintList;
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