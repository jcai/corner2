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
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.util.ContentType;

import corner.model.IBlobModel;
import corner.orm.tapestry.jasper.TaskType;
import corner.orm.tapestry.jasper.exporter.IJasperExporter;
import corner.orm.tapestry.utils.ComponentResponseUtils;
import corner.util.VectorUtils;

/**
 * 导出实体的服务类.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JasperEntityLinkService extends JasperLinkService{
	
	/**
	 * @see corner.orm.tapestry.jasper.service.JasperLinkService#service(org.apache.tapestry.IRequestCycle, org.apache.tapestry.IPage, boolean, java.lang.String, corner.model.IBlobModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void service(IRequestCycle cycle, IPage page, boolean isUsetemplatePath,boolean multiPageInRecord,boolean onlyOnePageInRecort, String templatePath, IBlobModel templateEntity, String downloadFileName, String taskType, String detailEntity, String detailCollection) throws IOException {
		IJasperExporter jasperAction = TaskType.valueOf(taskType).newInstance();
		try {
			
			//判断是从那里读取流
			InputStream is = isUsetemplatePath ? getAssetStream(page,templatePath) : getAssetStream(templateEntity);
			
			JRExporter exporter = jasperAction.getExporter();
			//初始化
			jasperAction.setupExporter(exporter);
		   
			//如果是一个报表有多页 非循环分页
			if(multiPageInRecord){
				
				List<JasperPrint> jasperPrintList = getJasperPrintList(is, page, templateEntity, detailEntity, detailCollection);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			}else{
				JasperPrint jasperPrint = getJasperPrint(is,page,templateEntity,detailEntity,detailCollection);
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
			
		} catch (JRException e) {
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
