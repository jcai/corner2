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

package corner.orm.tapestry.jasper.link;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.link.AbstractLinkComponent;

import corner.orm.tapestry.jasper.IBeforePrintSaveEntity;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class JasperEntityLink extends AbstractLinkComponent{
	
	/**
	 * @see org.apache.tapestry.link.AbstractLinkComponent#getLink(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public ILink getLink(IRequestCycle cycle) {
		Object[] parameters = new Object[7];
		parameters[0] = getDownloadFileName();
		parameters[1] = getTaskType().toLowerCase();
		parameters[2] = getTemplatePath();
		parameters[3] = getTemplateEntity();
		parameters[4] = getDetailEntity();
		parameters[5] = getDetailCollection();
		parameters[6] = getReportEntity(); 
		
		//在提交之前，保存实体
		if(cycle.getPage() instanceof IBeforePrintSaveEntity&&getReportEntity() != null){  
			parameters[6] = ((IBeforePrintSaveEntity)cycle.getPage()).getBeforePrintSaveEntity();
		}
	
		return this.getJasperService().getLink(true, parameters); 
	}
	
	/**
	 * 细节循环用的page，geter方法名
	 */
	@Parameter
	public abstract String getDetailEntity();
	
	/**
	 * 细节循环的集合
	 */
	@Parameter
	public abstract String getDetailCollection();
	
	/**
	 * 模版放置的位置classpath/context
	 */
	@Parameter
	public abstract String getTemplatePath();
	
	/**
	 * 模板使用的entity
	 */
	@Parameter
	public abstract Object getTemplateEntity();
	
	/**
	 * 指定下载的文件名称
	 */
	@Parameter
	public abstract String getDownloadFileName();
	
	/**
	 * 取得jasper数据生成类型
	 */
	@Parameter(defaultValue = "literal:pdf")
	public abstract String getTaskType();
	
	/**
	 * 取得要打印的实体.此属性应与templatePath结合使用,试用于只有一个此类报表的情况.
	 * @return 打印的实体
	 */
	@Parameter
	public abstract Object getReportEntity();
	
	/**
	 * @return
	 */
	@InjectObject("engine-service:jasper")
	public abstract IEngineService getJasperService();
}
