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

import corner.orm.tapestry.utils.ComponentResponseUtils;


/**
 * 实现生成Excel链接的ServiceLink
 * <p>
 * excel生成的数据为:当前查询，当前页面显示的数据
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public abstract class ExcelServicePageLink extends AbstractExcelServiceLink {

	/**
	 * @see corner.orm.tapestry.service.excel.AbstractExcelServiceLink#getGenerateType()
	 */
	@Override
	public String getGenerateType() {
		return ComponentResponseUtils.EXCEL_DATA_GENERATE_TYPE_PAGE;
	}

}
