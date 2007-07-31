/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PdfOutputPageEventTest.java 5440 2007-05-08 09:56:41Z jcai $
 *	created at:2007-4-11
 */

package corner.orm.tapestry.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.util.FileCopyUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfOutputPageEventTest extends Assert {

	@Test(groups="pdf")
	public void test_add_template() throws Exception {
		//创建模板文件
		int pageNum=2;
		byte[] bs=createPdfPage(pageNum);
		FileCopyUtils.copy(bs,new File("target/tmp1.pdf"));
		
		//测试event事件机制
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document=new Document();
		 PdfReader reader1 = new PdfReader(bs);
		
		 assertEquals(reader1.getNumberOfPages(),pageNum);
		
		PdfOutputPageEvent event=new PdfOutputPageEvent(reader1);
		PdfWriter writer=PdfWriter.getInstance(document, baos);
		writer.setPageEvent(event);
		
		document.open();
		
		for(int i=0;i<pageNum;i++){
			document.newPage();
			event.addTemplateData(writer, document, i+1);
		}
		
		document.close();
		bs=baos.toByteArray();
		FileCopyUtils.copy(bs,new File("target/tmp2.pdf"));
		//校验
		PdfReader reader = new PdfReader(bs);
		assertEquals(pageNum,reader.getNumberOfPages());
		
	}

	private byte[] createPdfPage(int pageNum) throws Exception {
		// 创建一个pdf文档
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		for(int i=0;i<pageNum;i++){
			document.newPage();
			document.add(new Phrase("page:"+(i+1)));
		}
		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
		return bs;
	}
}
