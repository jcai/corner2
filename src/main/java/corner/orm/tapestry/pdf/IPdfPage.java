/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: IPdfPage.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import org.apache.tapestry.util.ContentType;


/**
 * 对一个pdf页面的定义，通常可以用来缓存pdf解析.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfPage extends IPdfComponent{
	public final static ContentType CONTENT_TYPE=new ContentType("application/pdf");
	/** pdf模板路径 **/
	public abstract String getTemplate();
	public abstract void setTemplate(String pdfTemplate);
}
