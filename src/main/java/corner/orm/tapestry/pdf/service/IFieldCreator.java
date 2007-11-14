/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-12
 */

package corner.orm.tapestry.pdf.service;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

/**
 * 一个文本域的创建器.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IFieldCreator {

	/**
	 * 创建一个Pdf的{@link TextField},因为在PDF Reader中有的不能查看文本文字
	 * 
	 * @param writer pdfWriter,查看 {@link PdfWriter}
	 * @param rec 文本对象的位置 {@link Rectangle}
	 * @param name 文本的名称.
	 * @return 一个文本域
	 */
	public abstract TextField createTextField(PdfWriter writer, Rectangle rec,
			String name);

	/**
	 * 创建一个文本域，不用于方法 {@link #createTextField(PdfWriter, Rectangle, String)} ,
	 * 不提供方法的名称，自动产生一个不重复的号码.
	 * @param writer pdfWriter
	 * @param rec 文本域的位置
	 * @return 文本域
	 */
	public abstract TextField createTextField(PdfWriter writer, Rectangle rec);

}