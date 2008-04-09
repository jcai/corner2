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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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
	private static final String DETAIL = "detail";
	private static final String PAGE_NUMBER_ADD = "pageNumberAdd";
	
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
		boolean multiPageInRecord = ((Boolean) parameters[6]).booleanValue();
		boolean onlyOnePageInRecort = ((Boolean) parameters[7]).booleanValue();
		
		service(cycle,page,isUsetemplatePath,multiPageInRecord,onlyOnePageInRecort,templatePath,templateEntity,downloadFileName,taskType,detailEntity,detailCollection);
	}
	
	
	/**
	 * 自己的方法
	 */
	protected abstract void service(IRequestCycle cycle, IPage page,
			boolean isUsetemplatePath,boolean multiPageInRecord,boolean onlyOnePageInRecort, String templatePath,
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
	public static void getZipReportInputStreamMap(InputStream is, Map parameters) {
		
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
	 * 获得JasperPrint
	 * @param jasperInStream .jasper文件的流
	 * @param page   报表对应的页面
	 * @param templateEntity  报表实体
	 * @param detailEntity  数据源实体
	 * @param detailCollection  数据源集合
	 * @param pageNumber 用于多张报表时记录前面已填充报表的总页数.在非第一张报表时使用.
	 * <p>在报表中表示当前页数应该为:Integer.valueOf($V{PAGE_NUMBER}.intValue()+$P{pageNumberAdd}.intValue()),报表中需加参数pageNumberAdd并默认值为0.
	 * @return 填充好的JasperPrint对象
	 * @throws JRException
	 */
	protected JasperPrint getJasperPrint(InputStream jasperInStream,IPage page,Object templateEntity ,String detailEntity, String detailCollection,int pageNumber) throws JRException{
		JasperPrint jasperPrint = null;
	
		Map parameters = null;
		
		//如果要传递参数
		if(page instanceof IJasperParameter){
			parameters = ((IJasperParameter)page).getJasperParameters();
		}
		
		if(parameters == null){
			parameters = new HashMap();
		}
		
		if(isZipFile(jasperInStream)){
			getZipReportInputStreamMap(jasperInStream, parameters);
			jasperInStream = (InputStream) parameters.get(MAIN_REPORT);
		}

		//前边已填充报表则设置参数到报表
		if(pageNumber >0){
			parameters.put(PAGE_NUMBER_ADD, pageNumber);
		}
		
		jasperPrint = JasperFillManager.fillReport(jasperInStream, parameters, getDataSource(page,detailCollection,detailEntity));
		return jasperPrint;
	}

	/**
	 * 得到JasperPrint的list.用于一个报表有多页的情况(非循环多页)
	 * <p>
	 * 实现方法:通过读取zip包,将zip包里每一个报表文件进行填充并放入List返回.
	 * <p>
	 * 读取zip包的方法摘自getZipReportInputStreamMap(InputStream,Map)方法
	 * 注意:zip包里报表名命名规则:第一张应该为1.jasper,依次类推;若报表有detail,则在数字后边加上detail如2detail.jasper;
	 * 若报表有子报表则命名时后缀为.zip有detail则加,无则不加.
	 * @param jasperInStream
	 * @param page
	 * @param templateEntity
	 * @param detailEntity
	 * @param detailCollection
	 * @return
	 * @throws JRException
	 */
	protected List<JasperPrint> getJasperPrintList(InputStream jasperInStream,
			IPage page, Object templateEntity, String detailEntity,
			String detailCollection) throws JRException {

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		Map<String, InputStream> jasperInStreamMaps = new HashMap<String, InputStream>();
		JasperPrint jasperPrint = null;
		Map<String, String> detailMap = new HashMap<String, String>();
		int pageNumber = 0;
		
		if (isZipFile(jasperInStream)) {
			ZipInputStream zis = new ZipInputStream(jasperInStream);
			BufferedOutputStream dest = null;
			ZipEntry entry = null;
			String reportName = null;// 报表的名字(.jasper之前的部分)

			ByteArrayOutputStream fos = null;

			try {
				while ((entry = zis.getNextEntry()) != null) {
					int count;
					byte data[] = new byte[BUFFER];

					// write the files to the disk
					fos = new ByteArrayOutputStream((int) entry.getSize());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}

					dest.flush();
					dest.close();
					reportName = entry.getName().split("\\.")[0];

					// 如果报表名是以detail结尾的,即此报表有detail则进行标记
					if (reportName.endsWith(DETAIL)) {

						reportName = reportName.substring(0, reportName
								.indexOf(DETAIL));
						detailMap.put(reportName, DETAIL);
					}
					jasperInStreamMaps.put(reportName,
							new ByteArrayInputStream(fos.toByteArray()));

				}
				zis.close();
			} catch (Exception e) {
				throw new ApplicationRuntimeException(e);
			}
		}

		// 遍历jasperInStreamMaps,将从zip包读出的报表流进行填充,并放入jasperPrintList.
		for (int i = 1;; i++) {

			InputStream jasperInStreamMap = jasperInStreamMaps.get(String
					.valueOf(i));

			// 如果map里没有值了则跳出
			if (jasperInStreamMap == null) {
				break;
			}

			// 如果有detail则给它正确的数据源,否则给null
			if (detailMap.get(String.valueOf(i)) != null) {
				jasperPrint = this.getJasperPrint(jasperInStreamMap, page,
						templateEntity, detailEntity, detailCollection,pageNumber);
			} else {
				jasperPrint = this.getJasperPrint(jasperInStreamMap, page,
						templateEntity, null, null,pageNumber);
			}
            
			int pageSize = jasperPrint.getPages().size();
			pageNumber += pageSize;   //将前边的JasperPrint的页数进行记录
			
			//报表页数>0才添加进List进行导出打印
           if(pageSize>0){
            	jasperPrintList.add(jasperPrint);
           }
            
		}
		//TODO 在这可能因为数据源里没有数据导致pageSize为0,使得jasperPrintList里没数据 导出报表时异常.
		//等待解决"数据源没数据打印出空白页面"时一块解决此问题. 现在先保证没异常,打印出空白页.
		if(jasperPrintList.size() == 0){
			jasperPrintList.add(jasperPrint);
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