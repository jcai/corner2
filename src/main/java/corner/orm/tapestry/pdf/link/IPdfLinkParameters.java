/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-06-13
 */

package corner.orm.tapestry.pdf.link;

import org.apache.tapestry.annotations.Parameter;

/**
 * PdfLink组件通用的参数接口
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfLinkParameters {

	/**
	 * 是否保存为文件 true:保存为文件 false:直接在网页中打开
	 * 
	 * @return 返回一个blooean类型的参数判断是否要保存为文件
	 */
	@Parameter(defaultValue = "true")
	public abstract boolean isSaveAsFile();

	/**
	 * 取得保存为文件时候的文件名称
	 * 
	 * @return 一个String类型的文件名称,不包括扩展名,当该文件名称为空的时候会用当前时间产生一个文件名称
	 */
	@Parameter
	public abstract String getDownloadFileName();

}
