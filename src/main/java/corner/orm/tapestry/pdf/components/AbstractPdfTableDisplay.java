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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfSystemException;
import corner.orm.tapestry.pdf.PdfUtils;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 抽象的pdf表格展示区域
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractPdfTableDisplay extends AbstractColumnText {
	/**
	 * 记录当前页面循环的List的位置.
	 */
	public static final String CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME = "pdf-current-overflow-table-seq";

	/**
	 * 记录当前循环的source对象
	 */
	public static final String CURRENT_SOURCE = "pdf-current-source";

	/**
	 * 循环table的模型对象
	 */
	public static final String OV_TABLE_MODE = "pdf-ov-table-model";

	/**
	 * 展示表格的模型对象
	 * 
	 * @return 模型对象
	 */
	protected abstract IPdfTableModel<Object> getDisplayTableModel();


	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractColumnText#renderColumnText(com.lowagie.text.pdf.ColumnText, corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@Override
	protected void renderColumnText(ColumnText ct, PdfWriterDelegate writer,
			Document doc) {
		// 得到当前的序号值
		Integer currentCycleSeqObj = getRecord(
				CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME, Integer.class);
		int currentSeq = 0;
		if (currentCycleSeqObj != null) {
			currentSeq = (Integer) currentCycleSeqObj;
		}

		// 得到当前循环的列表数据
		List sourceObj = getRecord(CURRENT_SOURCE, List.class);

		List source = sourceObj;
		if (sourceObj == null) {
			source = getDefaultSource();
		}

		// 得到循环的步长
		Integer currentTemplateSourceStepObj = getRecord(
				getStepAttributeName(), Integer.class);

		int currentTemplateSourceStep = findCycleStep(
				currentTemplateSourceStepObj, writer, source);

		if (currentTemplateSourceStep == 0) {
			return;
		}

		/**
		 * 判断所有的source已经渲染完毕
		 * 
		 * 如果渲染完毕:true 没有渲染完毕:false
		 */
		boolean isEndOfSource = source.size() <= (currentSeq + currentTemplateSourceStep);

		// System.out.println("current seq "+currentSeq+"
		// step:"+currentTemplateSourceStep);
		// 增加表里面的数据
		addStepTableData(writer, source, ct, currentSeq,
				currentTemplateSourceStep, isEndOfSource);

		if (isEndOfSource) { // 所有的source已经渲染完毕
			return;
		}
		// 记录当前的状态
		record(CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME, currentSeq
				+ currentTemplateSourceStep);
		record(getStepAttributeName(), currentTemplateSourceStep);
		record(CURRENT_SOURCE, source);

		doRenderOverflowContent(writer, currentCycleSeqObj == null);

		// 还原到起始状态
		if (currentCycleSeqObj == null) {// 仅仅当currentCycleSeqObj为空的时候，才回滚，删除这个变量
			rollback(CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME, currentCycleSeqObj);
		}
		rollback(getStepAttributeName(), currentTemplateSourceStepObj);
		rollback(CURRENT_SOURCE, sourceObj);
	}

	protected String getStepAttributeName() {
		throw new UnsupportedOperationException();
	}

	protected List getDefaultSource() {
		throw new UnsupportedOperationException();
	}

	protected void doRenderOverflowContent(IMarkupWriter writer,
			boolean isFirstPage) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 判断下一页是否为最后一页.
	 * 
	 * @param writer
	 *            pdf writer 代理类
	 * @param source
	 *            需要展示的Source
	 * @return 下一页是否为最后一页.是，返回 true,反之亦反
	 */
	protected boolean nextIsLastPage(PdfWriterDelegate writer, List source) {
		// 得到当前的序号值
		Integer currentCycleSeqObj = getRecord(
				CURRENT_CYCLE_SEQ_ATTRIBUTE_NAME, Integer.class);
		int currentSeq = 0;
		if (currentCycleSeqObj != null) {
			currentSeq = (Integer) currentCycleSeqObj;
		}

		Integer currentTemplateSourceStepObj = getRecord(
				getStepAttributeName(), Integer.class);

		int currentTemplateSourceStep = findCycleStep(
				currentTemplateSourceStepObj, writer, source);

		// System.out.println("------------current seq "+currentSeq+"
		// step:"+currentTemplateSourceStep);
		return (currentSeq + currentTemplateSourceStep) >= source.size();

	}

	/**
	 * 创建TextField的事件.
	 * 
	 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
	 * @version $Revision$
	 * @since 0.7.5
	 */
	private class CreateTextFieldCellEvent implements PdfPCellEvent {

		/** the writer with the acroform */
		private PdfWriter writer;

		/** the current fieldname */
		private String fieldname = "NoName";

		private String value;

		public CreateTextFieldCellEvent(PdfWriter writer, String string) {
			this.writer = writer;
			this.value = string;

		}

		/**
		 * @see com.lowagie.text.pdf.PdfPCellEvent#cellLayout(com.lowagie.text.pdf.PdfPCell,
		 *      com.lowagie.text.Rectangle,
		 *      com.lowagie.text.pdf.PdfContentByte[])
		 */
		public void cellLayout(PdfPCell cell, Rectangle position,
				PdfContentByte[] canvases) {
			TextField tf = getFieldCreator().createTextField(writer, position,
					fieldname);
			tf.setFont(PdfUtils.createSongLightBaseFont());
			tf.setOptions(TextField.MULTILINE);
			tf.setText(value);
			try {
				PdfFormField field = tf.getTextField();
				writer.addAnnotation(field);
			} catch (IOException e) {
				throw new PdfSystemException(e);
			} catch (DocumentException e) {
				throw new PdfSystemException(e);
			}
		}
	}

	protected PdfPTable createAllRowsTable(PdfWriter writer, List source) {
		int columnCount = getDisplayTableModel().getColumnCount();

		PdfPTable table = new PdfPTable(columnCount);
		table.setTotalWidth(this.getCurrentColumnTextWidth());
		beginTableHeader(table);
		try {
			table.setWidths(this.getDisplayTableModel()
							.getColumPercentWidths());
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}
		for (int i = 0; i < source.size(); i++) {
			createTableRow(writer, table, source.get(i), columnCount, getDisplayTableModel());
		}
		return table;
	}

	/**
	 * 增加PdfPTable的Header
	 * 
	 * @param table
	 */
	protected void beginTableHeader(PdfPTable table) {
		// 增加头
		if (this.getDisplayTableModel().getHeaders() != null) {
			Font headerFont = this.getDisplayTableModel().getHeadersFont();
			if(headerFont == null){//默认footer字体
				headerFont = PdfUtils.createHeaderSongLightFont(10);
			}
			for (int i = 0; i < getDisplayTableModel().getColumnCount(); i++) {

				// TODO 标题文字设置大小问题，讨论标题字号
				PdfPCell cell = new PdfPCell(
						new Phrase(getDisplayTableModel().getHeaders()[i],headerFont));

				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);// 设置水平居中
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);// 设置竖直居中

				table.addCell(cell);
			}
		}

	}

	/**
	 * 增加PdfPTable的Footer部分
	 * 
	 * @param table
	 */
	protected void beginTableFooter(PdfPTable table, PdfWriter writer) {
		if (this.getDisplayTableModel().getFooters() != null) {
				List<String> cells = this.getDisplayTableModel().getFooters();
				Font footerFont = this.getDisplayTableModel().getFootersFont();
				if(footerFont == null){//默认footer字体
					footerFont = PdfUtils.createHeaderSongLightFont(10);
				}
				Iterator<String> it = cells.iterator();
				while(it.hasNext()){
				String key = it.next();
				// TODO 标题文字设置大小问题，讨论标题字号
				PdfPCell cell = new PdfPCell(
						new Phrase(key,footerFont));
				cell.setColspan(getDisplayTableModel().getColumnCount());
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);// 设置水平对齐方式
				cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);// 设置竖直居中

				table.addCell(cell);
			}
		}
	}

	// 创建一行的数据
	protected void createTableRow(PdfWriter writer, PdfPTable table,
			Object obj, int columnCount, IPdfTableModel model) {
		Font contentFont = model.getContentFont();
		if(contentFont == null){//如果model没有提供字体，默认使用的字体
			contentFont = PdfUtils.createSongLightFont(10);
		}
		for (int i = 0; i < columnCount; i++) {
			// 加入一个空的句子，目的是使得TableRow能够自动计算出来这个高度.
			PdfPCell cell = new PdfPCell(new Phrase("　", contentFont));
			// 设定事件，列值.
			cell.setCellEvent(new CreateTextFieldCellEvent(writer,
					getDisplayTableModel().getCurrentColumnValue(obj, i)));
			table.addCell(cell);
		}

	}

	// 通过给定的额起始位置来创建表格对象
	protected PdfPTable createPdfTable(PdfWriter pdfWriter, List source,
			int start, int rows) {
		int columnCount = getDisplayTableModel().getColumnCount();

		PdfPTable table = new PdfPTable(columnCount);
		table.setTotalWidth(this.getCurrentColumnTextWidth());
		beginTableHeader(table);// 增加TableHeader
		try {
			table.setWidths(this.getDisplayTableModel()
							.getColumPercentWidths());
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}

		for (int i = start; i < start + rows && i < source.size(); i++) {
			createTableRow(pdfWriter, table, source.get(i), columnCount, getDisplayTableModel());
		}
		return table;
	}

	/**
	 * 创建一个带有Footer的PdfPTable
	 * 
	 * @param table
	 * @return
	 */
	protected PdfPTable createPdfFooterTable(PdfWriter pdfWriter, List source,
			int start, int rows) {
		PdfPTable table = this.createPdfTable(pdfWriter, source, start, rows);
		if (this.getDisplayTableModel().getFooters() != null) {
			this.beginTableFooter(table,pdfWriter);
		}
		return table;
	}

	// 递归查询当前的框所能容纳的表格的行数
	protected int findCurrentTemplateSourceStep(List source,
			PdfWriterDelegate writer, int rows, ColumnText ct)
			throws DocumentException {
		if (rows >= source.size()) {
			return rows;
		}
		PdfPTable table = createPdfTable(writer.getPdfWriter(), source, 0, rows);
		ct.addElement(table);
		if (ColumnText.hasMoreText(ct.go(true))) {
			ct.setYLine(this.getColumnTextStartHeight());
			return rows - 1;
		}
		// 回到起始位置
		ct.setYLine(this.getColumnTextStartHeight());
		return findCurrentTemplateSourceStep(source, writer, ++rows, ct);
	}

	// 记录对象
	protected void record(String attrName, Object value) {
		getCycle().setAttribute(attrName, value);
	}

	// 得到记录的对象值
	@SuppressWarnings("unchecked")
	protected <T> T getRecord(String name, Class<T> clazz) {
		return (T) getCycle().getAttribute(name);
	}

	// 回滚记录的数据
	protected void rollback(String name, Object oldObj) {
		if (oldObj != null) {
			getCycle().setAttribute(name, oldObj);
		} else {
			getCycle().removeAttribute(name);
		}

	}

	// 创建临时的ColumnText对象
	// TODO 不知道为什么采用当前的ColumnText对象总是出现莫名奇妙的错误.
	protected ColumnText createTempColumnText(PdfWriterDelegate writer) {
		ColumnText tmpCT = new ColumnText(writer.getPdfWriter()
				.getDirectContentUnder());
		String[] p = getRectangle().split(",");
		tmpCT.setSimpleColumn(Float.valueOf(p[0]), Float.valueOf(p[1]), Float
				.valueOf(p[2]), Float.valueOf(p[3]));
		return tmpCT;
	}

	// 查询当前循环的步长
	protected int findCycleStep(Integer currentTemplateSourceStepObj,
			PdfWriterDelegate writer, List source) {
		if (currentTemplateSourceStepObj != null) {
			return (Integer) currentTemplateSourceStepObj;
		} else {

			try {
				// 当前模板框一共能够存下的字段的最大高度
				ColumnText tmpCT = createTempColumnText(writer);
				return findCurrentTemplateSourceStep(source, writer, 1, tmpCT);
			} catch (DocumentException e) {
				throw new PdfSystemException(e);
			}
		}
	}

	// 加入step高度的数据到当前的pdf页中
	protected void addStepTableData(PdfWriterDelegate writer, List source,
			ColumnText ct, int currentSeq, int currentTemplateSourceStep,
			boolean isEndOfSource) {
		try {
			//如果
			PdfPTable table = isEndOfSource ? createPdfFooterTable(writer
					.getPdfWriter(), source, currentSeq,
					currentTemplateSourceStep) : createPdfTable(writer
					.getPdfWriter(), source, currentSeq,
					currentTemplateSourceStep);
			ct.addElement(table);
			ct.go(false);
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}
	}

	@InjectObject("infrastructure:requestCycle")
	public abstract IRequestCycle getCycle();

}