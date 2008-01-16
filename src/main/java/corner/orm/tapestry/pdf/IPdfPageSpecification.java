// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
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

package corner.orm.tapestry.pdf;

import java.util.List;
import java.util.Map;

import org.apache.tapestry.spec.IComponentSpecification;

/**
 * 对Pdf模板定义的接口
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfPageSpecification extends IComponentSpecification{

	/**
	 * 设定页数
	 * @param numberOfPages
	 */
	void setNumberOfPage(int numberOfPages);

	/**
	 * 设定页面中包含的字段。
	 * @param pageFields 页面包含的字段
	 */
	void setPageFieldsMap(Map<Integer, List<PdfBlock>> pageFields);

	/**
	 * @return Returns the numberOfPage.
	 */
	public int getNumberOfPage();

	/**
	 * @return Returns the pageFieldsMap.
	 */
	public Map<Integer, List<PdfBlock>> getPageFieldsMap();
	

}
