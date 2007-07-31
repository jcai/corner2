/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: CreatePdf.java 4838 2007-04-11 09:50:32Z jcai $
 *	created at:2007-4-10
 */



package corner.orm.tapestry.pdf.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.springframework.util.FileCopyUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfTestPage;
import corner.orm.tapestry.pdf.PdfUtils;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CreatePdf extends Assert{

	@Test(groups="pdf")
	public void test_create_entity_pdf() throws Exception{
//		 创建一个pdf文档
		Document document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		document.add(new Phrase("test1"));
		document.add(new Paragraph("本样例演示了中间表格导致溢出表格，同时溢出的部分采用当前页来渲染,譬如：模板有三页，在第二页有一个循环，而且该循环可能导致分页.",PdfUtils.createSongLightFont(12)));
		
		// 用下面的方法能够更加灵活和方便的创建TextField，尤其用来显示的时候，非常不错的说.
		// http://itext.ugent.be/library/com/lowagie/examples/forms/SimpleRegistrationForm.java

		int i=1;
		addField(writer,"test1",i++);
		document.newPage();
		document.add(new Phrase("test2"));
		addField(writer,"",i++);
		addField(writer,"",i++);
		
		addColumn(writer,"",i++);
		
		addPdfTable(writer);
		addPageClass(writer);
		
		addOverPageTable(writer);
		
		document.newPage();
		document.add(new Phrase("test3"));
		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs,new File("target/test.pdf"));
		 
	}
	private void addOverPageTable(PdfWriter writer) throws IOException, DocumentException {
		TextField tf = new TextField(writer, new Rectangle(200, 100, 400,
				200), "@PdfOvTable");

		tf
				.setText("model=\"ognl:pdfTableModel\" source=\"ognl:overflowTableSource\" overflow=\"ognl:true\"");
		
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);
	}
	private void addPageClass(PdfWriter writer) throws IOException, DocumentException {
		TextField tf = new TextField(writer, new Rectangle(50, 100, 50 + 100,
				100+ 50), "page-class");

		tf.setText(PdfTestPage.class.getName());
		
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE|TextField.HIDDEN);
		
		
		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);
		
	}
	private void addPdfTable(PdfWriter writer) throws IOException, DocumentException {
		TextField tf = new TextField(writer, new Rectangle(300, 100, 350,
				500), "@PdfTable");

		tf
				.setText("model=\"ognl:pdfTableModel\" source=\"ognl:TableSource\"");
		
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);
	}
	private void addField(PdfWriter writer,String fieldId,int i) throws IOException, DocumentException{
		TextField tf = new TextField(writer, new Rectangle(50*i, 100*i, 50*i + 100,
				100*i + 50), fieldId+"@PdfText");

		tf
				.setText("value=\"ognl:entity.chnName\"");
		
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);

	}
	private void addColumn(PdfWriter writer,String fieldId,int i) throws IOException, DocumentException{
		TextField tf = new TextField(writer, new Rectangle(300,500,350,550), fieldId+"@ColumnText");

		tf
				.setText("value=\"ognl:entity.chnName\"");
		
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);

	}
}
