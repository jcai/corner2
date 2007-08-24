/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: IPdfTableModel.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-11
 */

package corner.orm.tapestry.pdf.components;

import java.util.List;

import com.lowagie.text.Font;

/**
 * PDF表格的模型定义.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @param <T>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfTableModel<T> {
	/**
	 * 得到表格头部的文字说明.
	 * 
	 * @return 表格头部文字说明
	 */
	public String[] getHeaders();
	
	/**
	 * 取得表头不分的字体
	 * @return
	 */
	public Font getHeadersFont();

	/**
	 * 得到总共列的数目。
	 * 
	 * @return 表格列的数目。
	 */
	public int getColumnCount();

	/**
	 * 得到列的百分比.
	 * 
	 * @return 百分比宽度。
	 */
	public float[] getColumPercentWidths();

	/**
	 * 得到当前列的值
	 * 
	 * @param obj
	 *            当前循环的实体。
	 * @param seq
	 *            序号，从0开始。
	 * @return 当前类的值.
	 */
	public String getCurrentColumnValue(T obj, int seq);
	
	/**
	 * 取得表内容部分的字体
	 * @return
	 */
	public Font getContentFont();

	/**
	 * 得到结尾的文字说明
	 * 
	 * @return
	 */
	public List<String> getFooters();
	
	/**
	 * 取得表尾部分的字体
	 * @return
	 */
	public Font getFootersFont();
	
}
