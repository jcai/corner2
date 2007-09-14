/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper.exporter;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JPrintExporter extends AbstractJasperExporter{
	


	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getSuffix()
	 */
	public String getSuffix() {
		throw new UnsupportedOperationException("不需要定义！");
	}

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getContentType()
	 */
	public String getContentType() {
		throw new UnsupportedOperationException("不需要定义！");
	}
}
