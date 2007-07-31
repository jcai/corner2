/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfEntityPage.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-08
 */

package corner.orm.tapestry.pdf;




/**
 * 
 * 展示pdf的基础页面。
 * 此类通常只是简单展示一个pdf，有一个参数叫做entity，在pdf的页面中可以通过ognl调用。
 * 
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfEntityPage extends AbstractPdfPage {
	public abstract void setEntity(Object entity);
	public abstract Object getEntity();
}
