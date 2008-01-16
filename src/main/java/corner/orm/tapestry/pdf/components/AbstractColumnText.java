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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.ColumnText;

import corner.orm.tapestry.pdf.PdfSystemException;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 抽象的ColumnText组件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractColumnText extends AbstractPdfComponent {

	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@Override
	public void renderPdf(PdfWriterDelegate writer, Document doc) {
		Defense.notNull(getRectangle(), "位置");
		ColumnText ct = new ColumnText(writer.getPdfWriter().getDirectContent());
		String[] p = getRectangle().split(",");
		ct.setSimpleColumn(Float.valueOf(p[0]), Float.valueOf(p[1]), Float
				.valueOf(p[2]), Float.valueOf(p[3]));
//		System.out.println("得到ColumnText位置信息:"+getRectangle());
		setCurrentColumnTextWidth(Float.valueOf(p[2])-Float.valueOf(p[0]));
		setColumnTextStartHeight(Float.valueOf(Float.valueOf(p[3])));
		renderColumnText(ct, writer, doc);
		go(ct);
	}

	/**
	 * 对ColumnText组件来设定。
	 * 
	 * @param ct            ColumnText
	 * @param writer       pdf writer
	 * @param doc          document
	 */
	protected abstract void renderColumnText(ColumnText ct, PdfWriterDelegate writer,
			Document doc);

	/**
	 * 跳转
	 * 
	 * @param ct    ColumnText 组件
	 */
	protected void go(ColumnText ct) {
		try {
			ct.go();
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}
	}
	public abstract float getCurrentColumnTextWidth();
	public abstract void setCurrentColumnTextWidth(float width);
	public abstract float getColumnTextStartHeight();
	public abstract void setColumnTextStartHeight(float height);
}
