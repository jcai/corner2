/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractPdfPage.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import org.apache.tapestry.AbstractPage;
import org.apache.tapestry.util.ContentType;

import com.lowagie.text.Document;

/**
 * 抽象的PDF页面类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractPdfPage extends AbstractPage implements IPdfPage{

	
	
	/**
	 * 默认的ContentType为application/pdf
	 * @see org.apache.tapestry.IPage#getResponseContentType()
	 */
	public ContentType getResponseContentType() {
		return CONTENT_TYPE;
	}

	/**
	 * 逐一的渲染组件.
	 * @see corner.orm.tapestry.pdf.IPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@SuppressWarnings("unchecked")
	public void renderPdf(PdfWriterDelegate writer,Document doc){
		
		//do nothing 能够用来打开pdf之用.
	}
}
