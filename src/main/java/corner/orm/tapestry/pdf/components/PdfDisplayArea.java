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

package corner.orm.tapestry.pdf.components;

import java.util.List;

import org.apache.tapestry.IMarkupWriter;

import corner.orm.tapestry.pdf.IPdfComponent;
import corner.orm.tapestry.pdf.PdfSystemException;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 提供一个显示区域,没有任何参数，值是为了得到区域的边界并把内容显示在这里
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfDisplayArea extends AbstractPdfTableDisplay {



	/**
	 * 组件的名称
	 */
	public static final String COMPONENT_NAME = "PdfDisplayArea";
	/**
	 * 记录循环步长的值
	 */
	static final String CURRENT_TEMPLATE_OV_STEP = "pdf-display-area-step";

	

	
	@Override
	protected String getStepAttributeName(){
		return CURRENT_TEMPLATE_OV_STEP;
	}
	@Override
	protected List getDefaultSource() {
		throw new PdfSystemException("错误的组件,source object is null!");
	}

	@Override
	protected void doRenderOverflowContent(IMarkupWriter writer,boolean isFirstPage) {
		List sourceObj = getRecord(CURRENT_SOURCE, List.class);
		
		if(nextIsLastPage((PdfWriterDelegate) writer, sourceObj)){ //下面将要达到最后一页，跳出
			return;
		}
		//当前组件所在的页面
		IPdfComponent currentComponent = getRecord(
				PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME,
				IPdfComponent.class);
		currentComponent.render(writer, this.getCycle());// 自身作为模板递归渲染
	}
	@SuppressWarnings("unchecked")
	protected IPdfTableModel<Object> getDisplayTableModel() {
		return getRecord(PdfOvTable.OV_TABLE_MODE, IPdfTableModel.class);
	}

}
