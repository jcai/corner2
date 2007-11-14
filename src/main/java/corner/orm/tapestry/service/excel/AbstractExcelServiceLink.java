// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-06-14
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

package corner.orm.tapestry.service.excel;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.link.AbstractLinkComponent;

/**
 * 抽象的ExcelServiceLink
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public abstract class AbstractExcelServiceLink extends AbstractLinkComponent {

	/**
	 * 指定取得数据的TableView组件
	 * 
	 * @return 返回当前页面TableView组件的id
	 */
	@Parameter(defaultValue = "literal:TableView")
	public abstract String getTableViewName();

	/**
	 * 指定下载的文件名称
	 * 
	 * @return 返回下载的excel文件的名称
	 */
	@Parameter
	public abstract String getDownloadFileName();


	/**
	 * 是否展示表格的标题  true:展示  false:不展示
	 * 
	 * @return true或者false,标识是否显示excel的title
	 */
	@Parameter(defaultValue = "true")
	public abstract boolean getEnableTitle();

	@InjectObject("engine-service:excel")
	public abstract IEngineService getExcelService();

	/**
	 * @see org.apache.tapestry.link.AbstractLinkComponent#getLink(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public ILink getLink(IRequestCycle cycle) {
		Object[] parameters = new Object[4];
		parameters[0] = this.getTableViewName();
		parameters[1] = this.getDownloadFileName();
		parameters[2] = this.getGenerateType();
		parameters[3] = this.getEnableTitle();
		return this.getExcelService().getLink(true, parameters);
	}
	
	/**
	 * 取得excel数据生成类型
	 * @return 返回
	 */
	public abstract String getGenerateType();

}
