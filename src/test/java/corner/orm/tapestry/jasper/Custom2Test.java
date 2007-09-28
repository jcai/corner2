/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.orm.tapestry.jasper.ciq.Custom2;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 0.8.3
 */
public class Custom2Test extends Assert{
	
	static Class entity = Custom2.class;
	static String simpleName = Custom2.class.getSimpleName();
	
//	@Test
	public void testPath(){
		
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println(System.getProperty("java.class.path"));
	}
	
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
	@Test
	public void testMakeRtf() throws JRException{
		
		JRRtfExporter exporter = new JRRtfExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + ".rtf");
		exporter.exportReport();
	}
	
	/**
	 * 测试生成pdf
	 * @throws JRException 
	 */
	@Test
	public void testMakePdf() throws JRException{
		// Export to PDF file.
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path + simpleName + ".pdf");
		exporter.exportReport();
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
		
//		File f = new File("");

		path =  "target/jasper/";
		
		File nF = new File(path);
		
		nF.mkdirs();
		
		List bowlerInfo = getCollection();
		
		// Fill the parameters
		HashMap<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("port", "beijing");
		parameters.put("test", "在我们的实际工作中，经常需要实现打印功能。但由于历史原因，Java提供的打印功能一直都比较弱。实际上最初的jdk根本不支持打印，直到jdk1.1才引入了很轻量的打印支持。所以，在以前用Java/Applet/JSP/Servlet设计的程序中，较复杂的打印都是通过调用ActiveX/OCX 控件或者VB/VC程序来实现的，非常麻烦。实际上，SUN公司也一直致力于Java打印功能的完善，而Java2平台则终于有了一个健壮的打印模式的开端，该打印模式与Java2D图形包充分结合成一体。更令人鼓舞的是，新发布的jdk1.4则提供了一套完整的Java 打印服务 API  （Java Print Service API），它对已有的打印功能是积极的补充。利用它，我们可以实现大部分实际应用需求，包括打印文字、图形、文件及打印预览等等。本文将通过一个具体的程序实例来说明如何设计Java打印程序以实现这些功能，并对不同版本的实现方法进行分析比较。希望大家能从中获取一些有益的提示。");
		parameters.put("pic1", entity.getResourceAsStream("/corner/orm/tapestry/jasper/ciq/pic/CIQ_2_01.gif"));
		parameters.put("pic2", entity.getResourceAsStream("/corner/orm/tapestry/jasper/ciq/pic/CIQ_2_03.gif"));
		
	
		
		return jasperPrint = JasperFillManager.fillReport(entity.getResourceAsStream(simpleName + ".jasper"), parameters, new JRBeanCollectionDataSource(bowlerInfo));
	}
	
	private List getCollection() {
		List<Object> rs = new ArrayList<Object>();
		Custom2 c = new Custom2();
		c.setText("1");
		rs.add(c);
		
		c = new Custom2();
		c.setText("2");
		rs.add(c);
		
		c = new Custom2();
		c.setText("3");
		rs.add(c);
		
		c = new Custom2();
		c.setText("4");
		rs.add(c);
		
		c = new Custom2();
		c.setText("5");
		rs.add(c);
		return rs;
	}

	String path = null;
	JasperPrint jasperPrint = null;
	HashMap parameters = null;
}
