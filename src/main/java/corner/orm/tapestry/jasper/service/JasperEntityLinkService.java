// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-14
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.util.ContentType;

import corner.model.IBlobModel;
import corner.orm.tapestry.jasper.TaskType;
import corner.orm.tapestry.jasper.exporter.IJasperExporter;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.utils.ComponentResponseUtils;

/**
 * 导出实体的服务类.
 * @author <a href="mailto:renais@bjmaxinfo.com">renais</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JasperEntityLinkService extends JasperLinkService{
	private static final String TEMPLATE_PAGE = "jasper.properties";
	private static final String PAGE = "page";
	private static final String MULTI_PAGE = "multiPage";
	private static final String FETCH_FIRST_PAGE = "fetchFirstPage";
	
	/**
	 * @see corner.orm.tapestry.jasper.service.JasperLinkService#service(org.apache.tapestry.IRequestCycle, org.apache.tapestry.IPage, boolean, java.lang.String, corner.model.IBlobModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void service(IRequestCycle cycle, IPage page,boolean isUsetemplatePath, 
			String templatePath,IBlobModel templateEntity, String downloadFileName,
			String taskType, String detailEntity, String detailCollection) throws IOException{
		IJasperExporter jasperAction = TaskType.valueOf(taskType).newInstance();
		try {			
			//判断是从哪里读取流
			InputStream is = isUsetemplatePath ? getAssetStream(page,templatePath) : getAssetStream(templateEntity);			
			JRExporter exporter = jasperAction.getExporter();
			//初始化
			jasperAction.setupExporter(exporter);
			
			Map<Object, Object> parameters = new HashMap<Object, Object>(); 	
			getJasperParameters(is, parameters);
			
			IPageRooted<Object, Object> activePage = (IPageRooted<Object, Object>) page;
			boolean multiPageInReport = false;
			boolean onlyOnePageInRecort = false;
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
				
					if(json.has(MULTI_PAGE))
						multiPageInReport = Boolean.valueOf(json.get(MULTI_PAGE).toString());
					if(json.has(FETCH_FIRST_PAGE))
						onlyOnePageInRecort = Boolean.valueOf(json.get(FETCH_FIRST_PAGE).toString());
				}
			}
		   
			//如果是一个报表有多页 非循环分页
			if(multiPageInReport){
				List<JasperPrint> jasperPrintList = getJasperPrintList(cycle,is,activePage,parameters,detailEntity,detailCollection);	
				//处理多页时打印出多个空白页
				for(int i = 0,j = 0; i < jasperPrintList.size(); i++) {		
					j += ((JasperPrint)jasperPrintList.get(i)).getPages().size();
					if(j == 0 || (i == jasperPrintList.size() - 1 && ((JasperPrint)jasperPrintList.get(i)).getPages().size()==0 && j > 0)){
						jasperPrintList.remove(i);
					}
				}
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			}else{
				JasperPrint jasperPrint = getJasperPrint(is,activePage,parameters,detailEntity,detailCollection,0);
				//如果只要第一页,设置导出页数参数为0
				if(onlyOnePageInRecort){
					exporter.setParameter(JRExporterParameter.PAGE_INDEX,Integer.valueOf(0));
				}
				//准备参数
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			}
			
			//设定下载文件名
			ComponentResponseUtils.constructResponse(downloadFileName, jasperAction.getSuffix(),cycle, response);
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream(new ContentType(jasperAction.getContentType())));
		
			//导出报表.
			exporter.exportReport();
			
		} catch (Exception e) {
			throw new ApplicationRuntimeException(e);
		} 
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	
	public static final String SERVICE_NAME = "jasper";
}
