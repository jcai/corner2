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

import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 对pdf模板文件进行解析
 * 
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfOutputPageEvent extends PdfPageEventHelper {

	/** 模板文件的读取器 * */
	private PdfReader reader = null;

	/** 用来处理pdf的字节内容 * */
	private PdfContentByte canvas;
	public PdfOutputPageEvent(PdfReader reader) {
		this.reader = reader;
	}

	public PdfOutputPageEvent(IPdfPage pdfComponent) {
		try {
			reader = new PdfReader(pdfComponent.getSpecification()
					.getSpecificationLocation().getResourceURL());
		} catch (IOException e) {
			throw new PdfSystemException(e);
		}
	}

	/**
	 * 当打开文档的时候，同时也开始处理
	 * 
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onOpenDocument(com.lowagie.text.pdf.PdfWriter,
	 *      com.lowagie.text.Document)
	 */
	@Override
	public void onOpenDocument(PdfWriter writer, Document doc) {
		canvas = writer.getDirectContentUnder();
	}
	//创建一个空文字，目的是提供itext不要误认为该页面没有pages
	private void createBurdenText(PdfWriter writer) {
		PdfContentByte content = writer.getDirectContent();
		content.beginText();
		content.endText();
	}
	public void addTemplateData(PdfWriter writer,Document doc,int templatePageNum){
		PdfImportedPage templateData = writer
				.getImportedPage(reader, templatePageNum);
		createBurdenText(writer);
		canvas.addTemplate(templateData, 0, 0);
	}
}
