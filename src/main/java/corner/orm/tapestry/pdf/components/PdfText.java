/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfText.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-08
 */

package corner.orm.tapestry.pdf.components;

import java.io.IOException;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfSystemException;
import corner.orm.tapestry.pdf.PdfUtils;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * pdf的一个TextField组件
 * 
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfText extends AbstractPdfComponent {

	/** 是否多行* */
	@Parameter
	public abstract boolean isMultiline();

	@Parameter(required = true)
	public abstract String getValue();

	@Parameter(defaultValue = "0")
	public abstract float getFontSize();
	
	@Parameter(defaultValue ="false")
	public abstract boolean isAlignCenter();


	
	
	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@Override
	public void renderPdf(PdfWriterDelegate writer,Document doc)  {
		Defense.notNull(getRectangle(), "TextField的位置");
		String[] p = getRectangle().split(",");
		Rectangle r = new Rectangle(Float.valueOf(p[0]), Float.valueOf(p[1]),
				Float.valueOf(p[2]), Float.valueOf(p[3]));
		
		
		TextField tf = getFieldCreator().createTextField(writer.getPdfWriter(), r,this.getId());

		if (isMultiline()) {
			tf.setOptions(TextField.MULTILINE);
		}
		
		if(isAlignCenter()){
			tf.setAlignment(Element.ALIGN_CENTER);
		}
		if (getValue() != null) {// 设定文字
			tf.setFont(PdfUtils.createSongLightBaseFont());
			if (getFontSize() > 0)
				tf.setFontSize(getFontSize());
			tf.setText(getValue());
		}
		try {
			writer.getPdfWriter().addAnnotation(tf.getTextField());
		} catch (IOException e) {
			throw new PdfSystemException(e);
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}
	}
}
