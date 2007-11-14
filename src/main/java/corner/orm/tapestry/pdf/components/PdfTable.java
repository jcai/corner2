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

import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.ColumnText;

import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 
 * 一个展示PDF表格的组件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfTable extends AbstractPdfTableDisplay {

	/** 对表格的定义 * */
	@Parameter(required = true)
	public abstract IPdfTableModel<Object> getModel();

	@Parameter(required = true)
	public abstract List getSource();

	/**
	 * 对ColumnText组件来设定。
	 * 
	 * @param ct
	 *            ColumnText
	 * @param writer
	 *            pdf writer
	 * @param doc
	 *            document
	 */
	@Override
	protected void renderColumnText(ColumnText ct, PdfWriterDelegate writer,
			Document doc) {
		List source = getSource();
		ct.addElement(createAllRowsTable(writer.getPdfWriter(), source));
	}
	protected IPdfTableModel<Object> getDisplayTableModel() {
		return getModel();
	}
}
