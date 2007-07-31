/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfSystemException.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-07
 */

package corner.orm.tapestry.pdf;

import corner.orm.exception.CornerSystemException;


/**
 * PDF处理时候抛出的基础异常类.
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfSystemException extends CornerSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388458930742094569L;

	/**
	 * @param cause
	 */
	public PdfSystemException(Throwable cause) {
		super(cause);
	}

	public PdfSystemException(String message) {
		super(message);
	}
}
