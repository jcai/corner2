/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfMessages.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-10
 */

package corner.orm.tapestry.pdf;

import org.apache.hivemind.impl.MessageFormatter;

/**
 * pdf包的消息文件
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfMessages {
	private static final MessageFormatter _formatter = new MessageFormatter(PdfMessages.class);

    /* defeat instantiation */
    private PdfMessages() { }

	public static String outputPdfProblem(Throwable e) {
		return _formatter.format("output.pdf.problem", e);
	}

	public static String invalidateEntityPageInstance(String name) {
		
		return _formatter.format("invlidate.page.instance", name);
	}
	
    

}
