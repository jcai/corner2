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