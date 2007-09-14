/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper.exporter;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JXlsExporter extends AbstractJasperExporter{

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.AbstractJasperExporter#getRExporter()
	 */
	public JRExporter getExporter() {
		return new JRXlsExporter();
	}

	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getSuffix()
	 */
	public String getSuffix() {
		return ".xls";
	}
	
	/**
	 * @see poison.preplan.tapestry.jasper.exporter.IJasperExporter#getContentType()
	 */
	public String getContentType() {
		return "application/vnd.ms-excel";
	}
}
