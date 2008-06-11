/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-6
 */

package corner.orm.tapestry.jasper;

import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.orm.tapestry.jasper.ciq.Custom;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 0.8.3
 */
public class JasperReportTest extends Assert{
	
	static Class entity = Custom.class;
	static String simpleName = Custom.class.getSimpleName();
	
	/**
	 * 测试直接打印
	 * @throws JRException 
	 */
//	@Test
	public void testMakePrint() throws JRException{
		JasperPrintManager.printReport(getJasperPrint(), true);
	}
	
	/**
	 * 测试生成rtf
	 * @throws JRException 
	 */
//	@Test
	public void testMakeRtf() throws JRException{
		
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + ".rtf");
		exporter.exportReport();
	}
	
	
	
	/**
	 * 测试生成excel
	 * @throws JRException 
	 */
//	@Test
	public void testMakeJRXls() throws JRException{
		JRXlsExporter exporter = new JRXlsExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + "JRXls.xls");
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

		exporter.exportReport();
	}
	
	/**
	 * 测试生成excel
	 * @throws JRException 
	 */
//	@Test
	public void testMakeJExcelApi() throws JRException{
		JRExporter exporter = new JExcelApiExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + "JExcelApi.xls");
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.exportReport();
	}
	
	/**
	 * 测试生成html
	 * @throws JRException 
	 */
//	@Test
	public void testMakeHtml() throws JRException{
		// Export to Html file.
		JasperExportManager.exportReportToHtmlFile(getJasperPrint(),"target/"+
				simpleName + ".html");
	}
	
	/**
	 * 测试生成pdf
	 * @throws JRException 
	 * @see http://blog.csdn.net/CloneIQ/archive/2007/01/05/1474938.aspx  中文处理
	 * @see http://www.javaeye.com/topic/78678?page=1 JasperReport报表设计总结
	 */
//	@Test
	public void testMakePdf() throws JRException{
		// Export to PDF file.
		JasperExportManager.exportReportToPdfFile(getJasperPrint(),"target/"+
				simpleName + ".pdf");
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws JRException
	 */
	public JasperPrint getJasperPrint() throws JRException {
		if(jasperPrint != null){
			return jasperPrint;
		}
		List bowlerInfo = null;
//		bowlerInfo = session.createQuery("from " + simpleName).list();
		// Fill the parameters
		HashMap parameters = new HashMap();
		
		return jasperPrint = JasperFillManager.fillReport(entity.getResourceAsStream(simpleName + ".jasper"), parameters, new JRBeanCollectionDataSource(bowlerInfo));
	}
	
	JasperPrint jasperPrint = null;
	HashMap parameters = null;
}
