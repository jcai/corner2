/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PageFragmentTest.java 6287 2007-06-05 09:05:39Z ghostbb $
 *	created at:2007-5-8
 */

package corner.orm.tapestry.pdf.components;

import static org.easymock.EasyMock.expect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.RequestCycle;
import org.springframework.util.FileCopyUtils;
import org.testng.annotations.Test;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import corner.demo.model.pdf.A;
import corner.orm.tapestry.pdf.PdfEntityPage;
import corner.orm.tapestry.pdf.PdfOutputPageEvent;
import corner.orm.tapestry.pdf.PdfTestPage;
import corner.orm.tapestry.pdf.PdfWriterDelegate;
import corner.orm.tapestry.pdf.service.FieldCreator;
import corner.orm.tapestry.pdf.service.IFieldCreator;
import corner.orm.tapestry.pdf.service.PdfResponseBuilder;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PageFragmentTest extends BaseComponentTestCase {

	 @Test(groups="pdf")
	public void test_parse_page() throws Exception {
		int pageNum = 2;

		// 生成pdf模板文档
		byte[] templateData = createPdfTemplateData(pageNum);
		FileCopyUtils.copy(templateData, new File(
				"target/test_parse_page_tmp.pdf"));

		// 输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfReader reader1 = new PdfReader(templateData);

		assertEquals(reader1.getNumberOfPages(), pageNum + 2);

		PdfOutputPageEvent event = new PdfOutputPageEvent(reader1);
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		writer.setPageEvent(event);

		PdfWriterDelegate delegate = new PdfWriterDelegate(writer);
		document.open();

		// PdfEntityPage page = (PdfEntityPage) newInstance(PdfEntityPage.class,
		// "template", "test.pdf in memory");
		//
		// PdfTestPage testPage=(PdfTestPage) newInstance(PdfTestPage.class);

		IRequestCycle cycle = newCycle();
		PageFragment frag = (PageFragment) newInstance(PageFragment.class,
				"pageNum", 1);

		expect(cycle.renderStackPush(frag)).andReturn(frag);

		// expect(cycle.renderStackPop()).andReturn(frag);
		expect(
				cycle
						.getAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME))
				.andReturn(document);

		expect(
				cycle
						.getAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME))
				.andReturn(null);
		cycle.setAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME,
				frag);
		cycle
				.removeAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME);

		expect(cycle.renderStackPop()).andReturn(frag);

		replay();

		frag.render(delegate, cycle);

		verify();

		document.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs, new File("target/test_parse_page_out.pdf"));

	}

	@Test
	public void test_parse_page_ov_table3() throws Exception {
		int pageNum = 2;

		// 生成pdf模板文档
		byte[] templateData = createPdfTemplateData(pageNum);
		FileCopyUtils.copy(templateData, new File(
				"target/test_parse_page_ov_table3_tmp.pdf"));

		// 输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfReader reader1 = new PdfReader(templateData);

		assertEquals(reader1.getNumberOfPages(), pageNum + 2);

		PdfOutputPageEvent event = new PdfOutputPageEvent(reader1);
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		writer.setPageEvent(event);

		PdfWriterDelegate delegate = new PdfWriterDelegate(writer);
		document.open();

		PdfResponseBuilder builder = new PdfResponseBuilder(null, null, null);
		IRequestCycle cycle = new RequestCycle();
		cycle.setResponseBuilder(builder);
		cycle.setAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME,
				document);

		PdfTestPage testPage = (PdfTestPage) newInstance(PdfTestPage.class,
				"template", "test.pdf in memory");
		IFieldCreator creator = new FieldCreator();

		PageFragment frag = (PageFragment) newInstance(PageFragment.class,
				"container", testPage, "page", testPage, "clientId",
				"clientIdf", "id", "namef", "fieldCreator", creator, "pageNum",
				1);

		TemplatePageFragment tmp1 = (TemplatePageFragment) newInstance(
				TemplatePageFragment.class, "container", testPage, "page",
				testPage, "clientId", "clientIdf", "id",
				"page" + (pageNum + 1), "fieldCreator", creator, "pageNum",
				(pageNum + 1));
		testPage.addComponent(tmp1);
		PdfDisplayArea da = newInstance(PdfDisplayArea.class, "container",
				tmp1, "page", testPage, "clientId", "clientId", "id", "name",
				"cycle", cycle, "fieldCreator", creator);
		da.setRectangle("100,100,200,500");
		tmp1.addBody(da);

		PdfOvTable table = newInstance(PdfOvTable.class, "model", testPage
				.getPdfTableModel(), "source", getOvTableSource(), "container",
				frag, "page", testPage, "clientId", "clientId", "id", "name",
				"cycle", cycle, "fieldCreator", creator, "loopTemplatePage",
				pageNum + 1);
		table.setRectangle("100,100,200,500");
		frag.addBody(table);

		// 第一页
		// begin render fragment

		replay();

		frag.render(delegate, cycle);

		verify();

		document.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs, new File(
				"target/test_parse_page_ov_table3_out.pdf"));

		PdfReader reader2 = new PdfReader(bs);
		assertEquals(reader2.getNumberOfPages(), 5);
	}

	@Test
	public void test_parse_page_ov_table2() throws Exception {
		int pageNum = 2;

		// 生成pdf模板文档
		byte[] templateData = createPdfTemplateData(pageNum);
		FileCopyUtils.copy(templateData, new File(
				"target/test_parse_page_ov_table2_tmp.pdf"));

		// 输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfReader reader1 = new PdfReader(templateData);

		assertEquals(reader1.getNumberOfPages(), pageNum + 2);

		PdfOutputPageEvent event = new PdfOutputPageEvent(reader1);
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		writer.setPageEvent(event);

		PdfWriterDelegate delegate = new PdfWriterDelegate(writer);
		document.open();

		PdfResponseBuilder builder = new PdfResponseBuilder(null, null, null);
		IRequestCycle cycle = new RequestCycle();
		cycle.setResponseBuilder(builder);
		cycle.setAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME,
				document);

		PdfEntityPage page = (PdfEntityPage) newInstance(PdfEntityPage.class,
				"template", "test.pdf in memory");
		PdfTestPage testPage = (PdfTestPage) newInstance(PdfTestPage.class);
		IFieldCreator creator = new FieldCreator();

		PageFragment frag = (PageFragment) newInstance(PageFragment.class,
				"container", page, "page", page, "clientId", "clientIdf", "id",
				"namef", "fieldCreator", creator, "pageNum", 1);
		PdfOvTable table = newInstance(PdfOvTable.class, "model", testPage
				.getPdfTableModel(), "source", getOvTableSource(), "container",
				frag, "page", page, "clientId", "clientId", "id", "name",
				"cycle", cycle, "fieldCreator", creator);
		table.setRectangle("100,100,200,500");
		frag.addBody(table);

		// 第一页
		// begin render fragment

		replay();

		frag.render(delegate, cycle);

		verify();

		document.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs, new File(
				"target/test_parse_page_ov_table2_out.pdf"));

		PdfReader reader2 = new PdfReader(bs);
		assertEquals(reader2.getNumberOfPages(), 5);
	}

	@Test
	public void test_parse_page_ov_table() throws Exception {
		int pageNum = 2;

		// 生成pdf模板文档
		byte[] templateData = createPdfTemplateData(pageNum);
		FileCopyUtils.copy(templateData, new File(
				"target/test_parse_page_ov_table_tmp.pdf"));

		// 输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfReader reader1 = new PdfReader(templateData);

		assertEquals(reader1.getNumberOfPages(), pageNum + 2);

		PdfOutputPageEvent event = new PdfOutputPageEvent(reader1);
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		writer.setPageEvent(event);

		PdfWriterDelegate delegate = new PdfWriterDelegate(writer);
		document.open();

		PdfResponseBuilder builder = new PdfResponseBuilder(null, null, null);
		IRequestCycle cycle = newCycle();

		PdfEntityPage page = (PdfEntityPage) newInstance(PdfEntityPage.class,
				"template", "test.pdf in memory");
		PdfTestPage testPage = (PdfTestPage) newInstance(PdfTestPage.class);
		IFieldCreator creator = new FieldCreator();

		PageFragment frag = (PageFragment) newInstance(PageFragment.class,
				"container", page, "page", page, "clientId", "clientIdf", "id",
				"namef", "fieldCreator", creator, "pageNum", 1);
		PdfOvTable table = newInstance(PdfOvTable.class, "model", testPage
				.getPdfTableModel(), "source", getTableSource(), "container",
				frag, "page", page, "clientId", "clientId", "id", "name",
				"cycle", cycle, "fieldCreator", creator);
		table.setRectangle("100,100,200,500");
		frag.addBody(table);

		// 第一页
		// begin render fragment

		expect(cycle.renderStackPush(frag)).andReturn(frag).anyTimes();
		expect(
				cycle
						.getAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME))
				.andReturn(document);

		expect(
				cycle
						.getAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME))
				.andReturn(null);
		cycle.setAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME,
				frag);

		// begin render table
		expect(cycle.getResponseBuilder()).andReturn(builder).anyTimes();

		expect(cycle.renderStackPush(table)).andReturn(table);

		expect(
				cycle
						.getAttribute(PdfResponseBuilder.PDF_DOCUMENT_ATTRIBUTE_NAME))
				.andReturn(document).anyTimes();

		expect(cycle.getAttribute(PdfOvTable.CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME))
				.andReturn(null);
		expect(cycle.getAttribute(PdfOvTable.CURRENT_SOURCE)).andReturn(null);
		expect(cycle.getAttribute(PdfOvTable.CURRENT_TEMPLATE_OV_STEP))
				.andReturn(null);

		// end render table
		expect(cycle.renderStackPop()).andReturn(table);

		// end render fragment

		cycle
				.removeAttribute(PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME);

		expect(cycle.renderStackPop()).andReturn(frag);

		replay();

		frag.render(delegate, cycle);

		verify();

		document.close();
		byte[] bs = baos.toByteArray();
		FileCopyUtils.copy(bs, new File(
				"target/test_parse_page_ov_table_out.pdf"));

	}

	/**
	 * 总计生成4个页面的PDF文件
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	private byte[] createPdfTemplateData(int pageNum) throws Exception {
		// 创建一个pdf文档,前两个1和2
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		for (int i = 0; i < pageNum; i++) {
			document.newPage();
			document.add(new Phrase("page:" + (i + 1)));
		}
		// 增加两个模板页面

		// page:3
		// page: pageNum+1
		document.newPage();
		document.add(new Phrase("page:" + (pageNum + 1)));
		TextField pageType = new TextField(writer, new Rectangle(100, 500, 150,
				480), "page-type");
		pageType.setText("template");
		writer.addAnnotation(pageType.getTextField());

		TextField displayTable = new TextField(writer, new Rectangle(150, 400,
				250, 500), "@PdfDisplayArea");

		writer.addAnnotation(displayTable.getTextField());

		// page:4
		// page: pageNum+2
		document.newPage();
		document.add(new Phrase("page:" + (pageNum + 2)));
		pageType = new TextField(writer, new Rectangle(100, 500, 150, 480),
				"page-type");
		pageType.setText("template");
		writer.addAnnotation(pageType.getTextField());

		displayTable = new TextField(writer, new Rectangle(200, 400, 300, 500),
				"@PdfDisplayArea");

		writer.addAnnotation(displayTable.getTextField());

		document.close();
		writer.close();
		byte[] bs = baos.toByteArray();
		return bs;
	}

	public List<A> getTableSource() {
		List<A> list = new ArrayList<A>();
		for (int i = 0; i < 6; i++) {
			A c = new A();
			c.setCnName(i + "蔡蔡蔡蔡蔡蔡蔡蔡");
			c.setName(i + "君君君君君君君君君君君");
			list.add(c);
		}
		return list;
	}

	private Object getOvTableSource() {
		List<A> list = new ArrayList<A>();
		for (int i = 0; i < 120; i++) {
			A c = new A();
			c.setCnName(i + "蔡蔡蔡蔡蔡蔡蔡蔡");
			c.setName(i + "君君君君君君君君君君君");
			list.add(c);
		}
		return list;
	}
}
