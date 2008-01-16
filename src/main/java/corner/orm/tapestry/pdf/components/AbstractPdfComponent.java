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
import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

import com.lowagie.text.Document;

import corner.orm.tapestry.pdf.IPdfComponent;
import corner.orm.tapestry.pdf.IPdfPage;
import corner.orm.tapestry.pdf.PdfWriterDelegate;
import corner.orm.tapestry.pdf.service.PdfResponseBuilder;

/**
 * 抽象的pdf组件类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractPdfComponent extends AbstractComponent implements IPdfComponent{
	/**
	 * @see corner.orm.tapestry.pdf.IPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	public void renderPdf(PdfWriterDelegate writer,Document doc){
		
	}
	/**
	 * @see org.apache.tapestry.AbstractComponent#getExtendedId()
	 */
	@Override
	public String getExtendedId() {
		IPdfPage page = (IPdfPage) this.getPage();
		if(page == null){
			return null;
		}
		if(page.getTemplate()==null){
			return null;
		}
		return page.getTemplate()+"/"+this.getIdPath();
	}
	/**
	 * 
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		Defense.isAssignable(writer,PdfWriterDelegate.class,"writer");
		renderPdf((PdfWriterDelegate) writer,(Document)cycle.getAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME));
	}
	public boolean isTemplateFragment(){
		return false;
	}
	
}
