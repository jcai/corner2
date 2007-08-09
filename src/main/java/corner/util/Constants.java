//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

/**
 * 保存Corner中一些的常量
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class Constants {
	/**
	 * PdfImage组件中，图片缩放的方式
	 * percent:百分比缩放 border:边框缩放
	 */
	public static final String PDFIMAGE_COMPONENT_SCALE_TYPE_PERCENT = "percent";
	public static final String PDFIMAGE_COMPONENT_SCALE_TYPE_BORDER = "border";
	
	/**
	 * {@link AbstractPdfPage}显示StringCheckbox组件的时候，使用的常量
	 */
	public static final String PDF_PAGE_STRING_CHECKBOX_TRUE_FLAG = "1";
	public static final String PDF_PAGE_STRING_CHECKBOX_FALSE_FLAG = "0";
	
	/**
	 * {@link AbstractPdfPage}显示Checkbox组件的时候，使用的常量
	 */
	public static final boolean PDF_PAGE_BOOLEAN_CHECKBOX_TRUE_FLAG = true;
	public static final boolean PDF_PAGE_BOOLEAN_CHECKBOX_FALSE_FLAG = false;
	
	/**
	 * {@link org.apache.tapestry.form.Checkbox}和{@link StringCheckbox}使用的回显字符串
	 */
	public static final String PDF_PAGE_CHECKBOX_TRUE_STR = "是";
	public static final String PDF_PAGE_CHECKBOX_FALSE_STR = "否";
	
}
