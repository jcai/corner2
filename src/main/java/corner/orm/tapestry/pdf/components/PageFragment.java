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

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;

import corner.orm.tapestry.pdf.PdfOutputPageEvent;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 定义一个pdf分页使用的组件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PageFragment extends AbstractPdfComponent {

	/**
	 * 记录当前页面实体类.
	 */
	public static final String CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME = "pdf-current-page-frag";

	
	
	@Parameter(required=true)
	public abstract int getPageNum();

	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	public void renderPdf(PdfWriterDelegate writer, Document doc) {
		doc.newPage();
		PdfOutputPageEvent event = (PdfOutputPageEvent) writer.getPdfWriter()
				.getPageEvent();
		event.addTemplateData(writer.getPdfWriter(), doc, getPageNum());
	}

	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		Defense.isAssignable(writer, PdfWriterDelegate.class, "writer");
		super.renderComponent(writer, cycle);
		// 得到老的对象值
		Object oldObj = cycle
				.getAttribute(CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME);
		cycle.setAttribute(CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME, this);
		renderBody(writer, cycle);
		cycle.removeAttribute(CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME);
		// 还原到其实状态.
		if (oldObj != null) {
			cycle.setAttribute(CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME, oldObj);
		}
		
	}
}
