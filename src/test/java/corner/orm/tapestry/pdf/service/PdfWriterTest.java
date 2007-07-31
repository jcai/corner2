/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PdfWriterTest.java 4821 2007-04-11 05:28:09Z jcai $
 *	created at:2007-4-6
 */

package corner.orm.tapestry.pdf.service;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.testng.annotations.Test;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * http://www.nabble.com/Insert-a-table-in-a-pdf-form-field-t3484987.html
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfWriterTest {

	@Test(groups="pdf")
	public void test_write() throws Exception {

		OutputStream os = new FileOutputStream("target/test.pdf");

		Document document = new Document();

		PdfWriter writer = PdfWriter.getInstance(document, os);
		document.open();

		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.EMBEDDED);
		com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(
				bfChinese, 12, com.lowagie.text.Font.NORMAL);
		Paragraph pragraph = new Paragraph("你好asdf", FontChinese);

		document.add(pragraph);

		/* chapter07/ColumnControl.java */
		PdfContentByte cb = writer.getDirectContent();

		ColumnText ct = new ColumnText(cb);
		float pos;
		String line;
		Phrase p;
		int status = ColumnText.START_COLUMN;

		ct.setSimpleColumn(36, 36, PageSize.A4.width() - 36, PageSize.A4
				.height() - 36, 18, Element.ALIGN_JUSTIFIED);
		int i = -1;

		while (i++ < 100) {
			PdfPTable table = createTestTable(i);
			ct.addElement(table);
			pos = ct.getYLine();

			status = ct.go(true);
			System.err.println("Lines written:" + ct.getLinesWritten()
					+ " Y-positions: " + pos + " - " + ct.getYLine());
			if (!ColumnText.hasMoreText(status)) {
				ct.addElement(table);
				ct.setYLine(pos);
				ct.go(false);
			} else {

				document.newPage();

				ct.addElement(table);
				ct.setYLine(PageSize.A4.height() - 36);
				ct.go();
			}
		}

		document.close();
		writer.close();
	}

	private PdfPTable createTestTable(int i) throws Exception {
		PdfPTable table = new PdfPTable(10);
		int[] COLUMNS = { 5, 18, 15, 10, 10, 10, 10, 10, 10, 12 };
		table.setWidths(COLUMNS);
		table.setWidthPercentage(100);
		PdfPCell cell;

		table.addCell(String.valueOf(i + 1));
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.EMBEDDED);
		com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(
				bfChinese, 12, com.lowagie.text.Font.NORMAL);

		table.addCell(new Paragraph("我们", FontChinese));
		table.addCell("DPT" + (int) (1 + Math.random() * 5));
		int n = 0;
		for (int j = 0; j < 6; j++) {
			if (Math.random() < 0.5) {
				n++;
				cell = new PdfPCell(new Phrase("true"));
				// cell.setCellEvent(new RandomTable(true));
			} else {
				cell = new PdfPCell(new Phrase("false"));
				// cell.setCellEvent(new RandomTable(false));
			}
			table.addCell(cell);
		}
		table.addCell(String.valueOf(n));

		return table;
	}
}
