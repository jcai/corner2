/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-11
 */

package corner.orm.tapestry.pdf.components;

import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;

import corner.orm.tapestry.pdf.PdfUtils;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 提供pdf的Column书写方式.
 * <P>
 * 通常此类可以用来扩展为其他组件的展示.作为基础组件展示.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfColumnText extends AbstractColumnText {

	@Parameter(required = true)
	public abstract String getValue();
	

	/**
	 * 对ColumnText对象进行渲染.
	 * 
	 * @param ct
	 */
	@Override
	protected void renderColumnText(ColumnText ct, PdfWriterDelegate writer,
			Document doc) {
		ct.addText(new Phrase(getValue(), PdfUtils.createSongLightFont(0)));
	}
}
