/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper.exporter;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JPdfExporter extends AbstractJasperExporter{

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.AbstractJasperExporter#getRExporter()
	 */
	public JRExporter getExporter() {
		return new JRPdfExporter();
	}

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getSuffix()
	 */
	public String getSuffix() {
		return ".pdf";
	}

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getContentType()
	 */
	public String getContentType() {
		return "application/pdf";
	}
}
