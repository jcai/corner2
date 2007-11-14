/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-11
 */

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
