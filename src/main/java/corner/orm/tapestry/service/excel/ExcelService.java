/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id: ExcelService.java 6585 2007-06-14 04:31:01Z ghostbb $
 * created at:2007-6-8
 */

package corner.orm.tapestry.service.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.contrib.table.components.TableView;
import org.apache.tapestry.contrib.table.model.ITableModel;
import org.apache.tapestry.contrib.table.model.simple.SimpleTableColumn;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

import corner.orm.tapestry.utils.ComponentResponseUtils;

/**
 * 实现生成Excel的Service
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 6585 $
 * @since 0.8.5.1
 */
public class ExcelService implements IEngineService {

	/** The content type for an Excel response */
	protected static final String CONTENT_TYPE = "application/vnd.ms-excel";

	protected static final String SERVICE_NAME = "excel";

	/** link factory */
	protected LinkFactory _linkFactory;

	/** response */
	protected WebResponse _response;

	/** request cycle * */
	protected IRequestCycle _requestCycle;

	/** excel 文件后缀的文件名 * */
	protected final static String EXCLE_FILE_EXTENSION_NAME = ".xls";

	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean,
	 *      java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ServiceConstants.PAGE, this._requestCycle.getPage()
				.getPageName());
		parameters.put(ServiceConstants.PARAMETER, parameter);

		return _linkFactory.constructLink(this, post, parameters, true);
	}

	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}

	/**
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {

		String activePageName = cycle.getParameter(ServiceConstants.PAGE);

		Object[] parameters = _linkFactory.extractListenerParameters(cycle);

		String tableviewId = (String) parameters[0];

		String fileName = (String) parameters[1];

		String genType = (String) parameters[2];

		boolean enableTitle = (Boolean) parameters[3];

		IPage page = cycle.getPage(activePageName);

		cycle.activate(page);

		// 取得TableView组件
		TableView comp = (TableView) page.getComponent(tableviewId);
		if (ComponentResponseUtils.EXCEL_DATA_GENERATE_TYPE_FULL
				.equals(genType)) {// 展示所有数据
			comp.getTableModel().getPagingState().setCurrentPage(0);
			comp.getTableModel().getPagingState()
					.setPageSize(Integer.MAX_VALUE);// 改变当前页，和最大值
		}

		if (page != null) {
			HSSFWorkbook workbook = generateExcelData(page, enableTitle, comp
					.getTableModel());

			OutputStream output = _response.getOutputStream(new ContentType(
					CONTENT_TYPE));

			// 构造下载文件的response
			ComponentResponseUtils.constructResponse(fileName,
					EXCLE_FILE_EXTENSION_NAME, _requestCycle, _response);

			workbook.write(output);
		}

	}

	/**
	 * 根据给定的条件查找DB并生成一个HSSFWorkbook实体
	 * FIXME:这里实用HSSFWorkbook.getBytes()方法取得的字节重新构造一个excel文件时候就会出错，初步判断可能是POI的bug,因此采用返回HSSFWorkbook的方法
	 * 
	 * @param page
	 *            调用当前Service的Page类
	 * @param enableTitle            是否显示excel的title
	 * @param model
	 *            当前页面中TableView组件的{@link ITableModel}
	 * @return 一个{@link HSSFWorkbook}的实例
	 */
	protected HSSFWorkbook generateExcelData(IPage page, boolean enableTitle,
			ITableModel model) {


		// create workbook
		HSSFWorkbook wb = new HSSFWorkbook();

		// create sheet
		HSSFSheet sheet1 = wb.createSheet("sheet");

		// create title
		if (enableTitle) {
			createExcelTitleRow(wb,sheet1,model.getColumnModel().getColumns());
		}
		// create excel body
		createExcelDataRows(wb, sheet1, model, enableTitle);
		return wb;
	}

	/**
	 * 创建Excel文件的Title部分
	 */
	protected void createExcelTitleRow(HSSFWorkbook wb, HSSFSheet sheet,Iterator columns) {
		HSSFRow row = sheet.createRow((short) 0);	
		int i=0;
		while(columns.hasNext()){
			SimpleTableColumn column=(SimpleTableColumn) columns.next();
			this.createTitleCell(wb, row, i, column.getDisplayName());
			i++;
		}
	}

	/**
	 * 创建Excel的数据
	 */
	protected void createExcelDataRows(HSSFWorkbook wb, HSSFSheet sheet, ITableModel model, boolean hasTitle) {
		Iterator rows = model.getCurrentPageRows();
		short i=0; //行数
		if(hasTitle){
			i++;
		}
		int j=0;//列数
		Iterator columns;
		while (rows.hasNext()) {
			Object rowObj = rows.next();
			// 创建一个row 如果有title，第0行就是title，如果没有，则从第0行开始
			HSSFRow row = sheet.createRow(i);

			// 向该row中添加数据
			columns = model.getColumnModel().getColumns();
			SimpleTableColumn column;
			j=0;
			while(columns.hasNext()){
				column=(SimpleTableColumn) columns.next();
				Object obj=column.getColumnValue(rowObj);
				String cellValue = obj!=null?obj.toString():"";
				this.createContentCell(wb, row, j, cellValue);
				j++;
			}
			i++;
		}
	}
	
	/**
	 * 取得title使用的Style
	 * 
	 * @return
	 */
	protected HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
		// create title Style
		HSSFCellStyle titleRowStyle = wb.createCellStyle();
		titleRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 12);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleRowStyle.setFont(titleFont);
		return titleRowStyle;
	}
	
	/**
	 * 构造excel的title内容
	 * 
	 * @param wb
	 * @param row
	 * @param index
	 * @param cellValue
	 */
	protected void createTitleCell(HSSFWorkbook wb, HSSFRow row, int index,
			String cellValue) {
		HSSFCell titlecell = row.createCell((short) index);
		titlecell.setCellStyle(this.getTitleStyle(wb));
		titlecell.setCellValue(new HSSFRichTextString(cellValue));
	}
	
	/**
	 * 构造excel的content内容
	 * 
	 * @param wb
	 * @param row
	 * @param index
	 * @param cellValue
	 */
	protected void createContentCell(HSSFWorkbook wb, HSSFRow row, int index,
			String cellValue) {
		HSSFCell contentcell = row.createCell((short) index);
		contentcell.setCellStyle(this.getContentCellStyle(wb));
		contentcell.setCellValue(new HSSFRichTextString(cellValue));
	}
	
	/**
	 * 构造contentCell的样式,默认是不带任何样式
	 * @param wb
	 * @return {@link HSSFCellStyle}
	 */
	protected HSSFCellStyle getContentCellStyle(HSSFWorkbook wb){
		HSSFCellStyle contentRowStyle = wb.createCellStyle();
		return contentRowStyle;
	}
	
	/**
	 * @param cycle
	 *            The _requestCycle to set.
	 */
	public void setRequestCycle(IRequestCycle cycle) {
		_requestCycle = cycle;
	}

	/**
	 * @param factory
	 *            The _linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory factory) {
		_linkFactory = factory;
	}

	/**
	 * @param _response
	 *            The _response to set.
	 */
	public void setResponse(WebResponse _response) {
		this._response = _response;
	}
}
