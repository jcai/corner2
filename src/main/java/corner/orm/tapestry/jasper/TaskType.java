/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper;

import corner.orm.tapestry.jasper.exporter.IJasperExporter;
import corner.orm.tapestry.jasper.exporter.JHtmlExporter;
import corner.orm.tapestry.jasper.exporter.JPdfExporter;
import corner.orm.tapestry.jasper.exporter.JPrintExporter;
import corner.orm.tapestry.jasper.exporter.JRtfExporter;
import corner.orm.tapestry.jasper.exporter.JTextExporter;
import corner.orm.tapestry.jasper.exporter.JXlsExporter;
import corner.orm.tapestry.jasper.exporter.JXmlExporter;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public enum TaskType {
	
	pdf(JPdfExporter.class),
	rtf(JRtfExporter.class),
	html(JHtmlExporter.class),
	print(JPrintExporter.class),
	txt(JTextExporter.class),
	xls(JXlsExporter.class),
	xml(JXmlExporter.class);
	
	TaskType(Class<? extends IJasperExporter> exporter){
		this.exporter = exporter;
	}
	
	Class<? extends IJasperExporter> exporter;

	/**
	 * @return Returns the exporter.
	 */
	public Class<? extends IJasperExporter> getExporter() {
		return exporter;
	}

	/**
	 * @param exporter The exporter to set.
	 */
	public void setExporter(Class<? extends IJasperExporter> exporter) {
		this.exporter = exporter;
	}
	
	/**
	 * 初始化选择的方法
	 */
	public IJasperExporter newInstance(){
		
		IJasperExporter jasperExporter = null;
		
		try {
			jasperExporter = this.exporter.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return jasperExporter;
	}
}
