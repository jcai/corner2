/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id: JasperBeanTest.java 3753 2007-09-13 08:46:35Z jcai $
 * created at:2007-9-10
 */

package corner.orm.tapestry.jasper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IPage;
import org.apache.tapestry.binding.BindingSource;
import org.testng.annotations.Test;

import corner.orm.tapestry.jasper.ciq.Custom;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision: 3753 $
 * @since 0.8.3
 */
public class JasperBeanTest extends BaseComponentTestCase{
	
	static Class entity = Custom.class;
	static String simpleName = Custom.class.getSimpleName();
	

	
//	/**
//	 * 测试生成rtf
//	 * @throws JRException 
//	 */
//	@Test
//	public void testMakeRtf() throws JRException{
//		
//		JRRtfExporter exporter = new JRRtfExporter();
//		
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + ".rtf");
//		exporter.exportReport();
//	}
	
	/**
	 * 测试生成pdf
	 * @throws JRException 
	 */
	@Test
	public void testMakePdf() throws JRException{
		// Export to PDF file.
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, simpleName + ".pdf");
		replay();
		exporter.exportReport();
		verify();
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
		
		
		
		IPage page=(IPage) this.newPage("test");//(IPage.class,"entity",createEntity());
		BindingSource source= newMock(BindingSource.class);
		
		
		
//		EasyMock.expect(page.getLocation()).andReturn(this.newLocation());
		return jasperPrint = JasperFillManager.fillReport(entity.getResourceAsStream("/corner/orm/tapestry/jasper/ciq/classic.jasper"), 
				parameters, new JREntityDataSource(source,page));
		
		
		
	}
	private Custom createEntity(){
		Custom c = new Custom();
		c.setTest("number test");
		return c;
		
	}
	private List getCollection() {
		List<Object> rs = new ArrayList<Object>();
		Custom c = new Custom();
		c.setTest("consultant number");
//		c.setAlexipharmic(Alexipharmic)("在我们的实际工作中，经常需要实现打印功能。但由于历史原因，Java提供的打印功能一直都比较弱。实际上最初的jdk根本不支持打印，直到jdk1.1才引入了很轻量的打印支持。所以，在以前用Java/Applet/JSP/Servlet设计的程序中，较复杂的打印都是通过调用ActiveX/OCX 控件或者VB/VC程序来实现的，非常麻烦。实际上，SUN公司也一直致力于Java打印功能的完善，而Java2平台则终于有了一个健壮的打印模式的开端，该打印模式与Java2D图形包充分结合成一体。更令人鼓舞的是，新发布的jdk1.4则提供了一套完整的Java 打印服务 API  （Java Print Service API），它对已有的打印功能是积极的补充。利用它，我们可以实现大部分实际应用需求，包括打印文字、图形、文件及打印预览等等。本文将通过一个具体的程序实例来说明如何设计Java打印程序以实现这些功能，并对不同版本的实现方法进行分析比较。希望大家能从中获取一些有益的提示。");
		
		rs.add(c);
		return rs;
	}

	String path = null;
	JasperPrint jasperPrint = null;
	HashMap parameters = null;
}
