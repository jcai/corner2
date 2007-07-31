/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: PdfTemplateSourceDelegateTest.java 5432 2007-05-08 09:15:58Z jcai $
 *	created at:2007-5-8
 */

package corner.orm.tapestry.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponentTestCase;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfTemplateSourceDelegateTest extends BaseComponentTestCase {

	@Test(groups="pdf")
	public void test_parse_pdf_pagetype() {
		PdfTemplateSourceDelegate delegate = new PdfTemplateSourceDelegate();
		IPdfPageSpecification spc = newMock(IPdfPageSpecification.class);
		EasyMock.expect(spc.getNumberOfPage()).andReturn(1).anyTimes();

		Map<Integer, List<PdfBlock>> fieldMap = new HashMap<Integer, List<PdfBlock>>();

		List<PdfBlock> blocks = new ArrayList<PdfBlock>();
		fieldMap.put(1, blocks);

		
		PdfBlock block1 = new PdfBlock();
		block1.setName("@PdfText");
		block1.setValue("value=\"ognl:value\"");
		block1.setPosition(new float[] { 1.0f, 1.0f, 2.0f, 3.0f, 4.0f });
		blocks.add(block1);

		PdfBlock block2 = new PdfBlock();
		block2.setName("page-type");
		block2.setValue("template");
		block2.setPosition(new float[] { 1.0f, 1.0f, 2.0f, 3.0f, 4.0f });
		blocks.add(block2);

		
		EasyMock.expect(spc.getPageFieldsMap()).andReturn(fieldMap);
		EasyMock.expect(spc.getNumberOfPage()).andReturn(1).anyTimes();

		replay();
		String str = delegate.parsePdfTemplate(spc);
		assertEquals(
				str,
				"<div><div jwcid=\"page1@TemplatePageFragment\" pageNum=\"1\">\n"
						+ "<span jwcid=\"@PdfText\" rectangle=\"literal:1.0,2.0,3.0,4.0\" value=\"ognl:value\"/>\n"
						+ "</div></div>");
		verify();
	}
	@Test
	public void test_parse_pdf() {
		PdfTemplateSourceDelegate delegate = new PdfTemplateSourceDelegate();
		IPdfPageSpecification spc = newMock(IPdfPageSpecification.class);
		EasyMock.expect(spc.getNumberOfPage()).andReturn(1).anyTimes();

		Map<Integer, List<PdfBlock>> fieldMap = new HashMap<Integer, List<PdfBlock>>();

		List<PdfBlock> blocks = new ArrayList<PdfBlock>();
		fieldMap.put(1, blocks);

		PdfBlock block1 = new PdfBlock();
		block1.setName("@PdfText");
		block1.setValue("value=\"ognl:value\"");
		block1.setPosition(new float[] { 1.0f, 1.0f, 2.0f, 3.0f, 4.0f });
		blocks.add(block1);

		EasyMock.expect(spc.getPageFieldsMap()).andReturn(fieldMap);
		EasyMock.expect(spc.getNumberOfPage()).andReturn(1).anyTimes();

		replay();
		String str = delegate.parsePdfTemplate(spc);
		assertEquals(
				str,
				"<div><div jwcid=\"page1@PageFragment\" pageNum=\"1\">\n"
						+ "<span jwcid=\"@PdfText\" rectangle=\"literal:1.0,2.0,3.0,4.0\" value=\"ognl:value\"/>\n"
						+ "</div></div>");
		verify();
	}

	@Test
	public void test_parse_pdf_ov_table() {
		PdfTemplateSourceDelegate delegate = new PdfTemplateSourceDelegate();
		IPdfPageSpecification spc = newMock(IPdfPageSpecification.class);
		EasyMock.expect(spc.getNumberOfPage()).andReturn(4).anyTimes();

		Map<Integer, List<PdfBlock>> fieldMap = new HashMap<Integer, List<PdfBlock>>();

		List<PdfBlock> blocks = new ArrayList<PdfBlock>();
		fieldMap.put(1, blocks);

		PdfBlock block1 = new PdfBlock();
		block1.setName("@PdfOvTable");
		block1
				.setValue("loopTemplatePage=\"literal:2\" endTemplatePage=\"literal:3\"");
		block1.setPosition(new float[] { 1.0f, 1.0f, 2.0f, 3.0f, 4.0f });
		blocks.add(block1);
		
		List<PdfBlock> blocks2 = new ArrayList<PdfBlock>();
		fieldMap.put(2, blocks2);
		PdfBlock block21 = new PdfBlock();
		block21.setName(PdfTemplateSourceDelegate.PDF_PAGE_TYPE_DEFINE_FIELD_NAME);
		block21.setValue(PdfTemplateSourceDelegate.TEMPLATE_PAGE_TYPE);
		blocks2.add(block21);
		
		List<PdfBlock> blocks3 = new ArrayList<PdfBlock>();
		fieldMap.put(3, blocks3);
		PdfBlock block31 = new PdfBlock();
		block31.setName(PdfTemplateSourceDelegate.PDF_PAGE_TYPE_DEFINE_FIELD_NAME);
		block31.setValue(PdfTemplateSourceDelegate.TEMPLATE_PAGE_TYPE);
		blocks3.add(block31);
		
		for (int i = 0; i < 4; i++) {
			EasyMock.expect(spc.getPageFieldsMap()).andReturn(fieldMap);

			EasyMock.expect(spc.getNumberOfPage()).andReturn(4);

		}

		replay();
		String str = delegate.parsePdfTemplate(spc);
		assertEquals(
				str,
				"<div><div jwcid=\"page1@PageFragment\" pageNum=\"1\">\n"
						+ "<span jwcid=\"@PdfOvTable\" rectangle=\"literal:1.0,2.0,3.0,4.0\" loopTemplatePage=\"literal:2\" endTemplatePage=\"literal:3\"/>\n"
						+ "</div><div jwcid=\"page2@TemplatePageFragment\" pageNum=\"2\">\n"
						+ "</div><div jwcid=\"page3@TemplatePageFragment\" pageNum=\"3\">\n"
						+ "</div><div jwcid=\"page4@PageFragment\" pageNum=\"4\">\n"
						+ "</div></div>");
		verify();
	}

}
