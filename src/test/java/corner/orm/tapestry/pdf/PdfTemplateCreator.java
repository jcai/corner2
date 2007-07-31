/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PdfTemplateCreator.java 5444 2007-05-08 10:08:51Z jcai $
 *	created at:2007-5-8
 */

package corner.orm.tapestry.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.util.FileCopyUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfTemplateCreator {
	public static void main(String[] args) throws Exception {
		// 创建一个pdf文档
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();

		// 1
		document.add(new Phrase("page:1"));
		TextField pageClass = new TextField(writer, new Rectangle(100, 400,
				200, 500), "page-class");
		pageClass.setText(PdfTestPage.class.getName());
		writer.addAnnotation(pageClass.getTextField());

		
		// 2
		document.newPage();
		document.add(new Phrase("page:2"));
		TextField ovTable = new TextField(writer, new Rectangle(100, 400, 200,
				500), "testOv@PdfOvTable");
		ovTable.setText("model=\"ognl:pdfTableModel\" source=\"ognl:overflowTableSource\" lp=\"4\" ep=\"6\"");

		writer.addAnnotation(ovTable.getTextField());


		// 3
		document.newPage();
		document.add(new Phrase("page:3"));

		// 4
		document.newPage();
		document.add(new Phrase("page:4"));
		TextField pageType = new TextField(writer, new Rectangle(100, 500, 150,
				480), "page-type");
		pageType.setText("template");
		writer.addAnnotation(pageType.getTextField());

		
		TextField displayTable = new TextField(writer, new Rectangle(150, 400, 250,
				500), "@PdfDisplayArea");
		
		writer.addAnnotation(displayTable.getTextField());
		
		
		// 5
		document.newPage();
		document.add(new Phrase("page:5"));

		//6
		document.newPage();
		document.add(new Phrase("page:6"));
		pageType = new TextField(writer, new Rectangle(100, 500, 150,
				480), "page-type");
		pageType.setText("template");
		writer.addAnnotation(pageType.getTextField());

		
		displayTable = new TextField(writer, new Rectangle(200, 400, 300,
				500), "@PdfDisplayArea");
		
		writer.addAnnotation(displayTable.getTextField());
		
		//7
		document.newPage();
		document.add(new Phrase("page:7"));
		

		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs, new File("target/test.pdf"));
	}
}
