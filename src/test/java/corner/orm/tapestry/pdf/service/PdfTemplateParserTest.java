/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id$
 *	created at:2007-4-7
 */

package corner.orm.tapestry.pdf.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfBlock;
import corner.orm.tapestry.pdf.PdfTemplateParser;

/**
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfTemplateParserTest extends Assert {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PdfTemplateParserTest.class);

	@Test(groups="pdf")
	public void test_parse() throws DocumentException, IOException {
		// 创建一个pdf文档
		Document document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		document.add(new Phrase("test"));
		// 用下面的方法能够更加灵活和方便的创建TextField，尤其用来显示的时候，非常不错的说.
		// http://itext.ugent.be/library/com/lowagie/examples/forms/SimpleRegistrationForm.java

		TextField tf = new TextField(writer, new Rectangle(100, 300, 100 + 100,
				300 + 50), "test@TextBlock");

		tf
				.setText("It was the best of times, it was the worst of times, it was the age of wisdom...asdfasdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf ");
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);

		
		
		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
//		 FileCopyUtils.copy(bs,new File("target/tmp.pdf"));
		PdfTemplateParser parser = new PdfTemplateParser(bs);
		Map<Integer, List<PdfBlock>> fields = parser.getPageFields();
		assertTrue(fields.size() == 1);
		assertTrue(fields.get(1).size() == 1);
		assertEquals(fields.get(1).get(0).getName(), "test@TextBlock");

	}

	@Test
	public void test_parse_more_field() throws DocumentException, IOException {
		// 创建一个pdf文档
		Document document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		document.add(new Phrase("test"));
		// 用下面的方法能够更加灵活和方便的创建TextField，尤其用来显示的时候，非常不错的说.
		// http://itext.ugent.be/library/com/lowagie/examples/forms/SimpleRegistrationForm.java

		TextField tf = new TextField(writer, new Rectangle(100, 300, 100 + 100,
				300 + 50), "test@TextBlock");

		tf
				.setText("It was the best of times, it was the worst of times, it was the age of wisdom...asdfasdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf ");
		tf.setAlignment(Element.ALIGN_CENTER);
		tf.setOptions(TextField.MULTILINE);

		PdfFormField field = tf.getTextField();
		writer.addAnnotation(field);

		TextField tf1 = new TextField(writer, new Rectangle(50, 100, 50 + 50,
				100 + 50), "test2@TextBlock");

		writer.addAnnotation(tf1.getTextField());

		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
		// FileCopyUtils.copy(bs,new File("target/tmp.pdf"));
		PdfTemplateParser parser = new PdfTemplateParser(bs);
		Map<Integer, List<PdfBlock>> fields = parser.getPageFields();

		assertTrue(fields.size() == 1);
		assertTrue(fields.get(1).size() == 2);
		assertEquals(fields.get(1).get(0).getName(), "test@TextBlock");
		assertEquals(fields.get(1).get(1).getName(), "test2@TextBlock");
	}

	@Test
	public void test_parse_more_page() throws Exception {
		Document document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		
		int pageNum=10;
		int fieldsNum=5;
		for(int i=1;i<=pageNum;i++){
			
			for(int j=0;j<fieldsNum;j++){
				TextField tf1 = new TextField(writer, new Rectangle(50, 100*j, 50 + 50,
						100*j + 50), "test"+i+"."+j+"@TextBlock");
				tf1.setOptions(TextField.MULTILINE);
				tf1.setText(i+""+j+"aasdf asdf asdf asd f asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf");
				tf1.setBackgroundColor(Color.BLUE);
				writer.addAnnotation(tf1.getTextField());
			}
			if(i<pageNum){
				document.newPage();
			}
		}
		document.close();
		writer.close();
		
		
		
		byte[] bs = baos.toByteArray();
//		 FileCopyUtils.copy(bs,new File("target/tmp.pdf"));
		PdfTemplateParser parser = new PdfTemplateParser(bs);
		Map<Integer, List<PdfBlock>> fields = parser.getPageFields();
		if (logger.isDebugEnabled()) {
			logger.debug("test_parse_more_page() - Map<Integer,List<PdfBlock>> fields=" + fields); //$NON-NLS-1$
		}
		

		

		assertTrue(fields.size()==pageNum);
		
		for(int i=1;i<=pageNum;i++){
			List<PdfBlock> list=fields.get(i);
			
			assertEquals(list.size(),fieldsNum);
		}


	}
}
