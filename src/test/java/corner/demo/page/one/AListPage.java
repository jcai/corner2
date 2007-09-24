//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.one;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.annotations.Asset;

import corner.demo.model.one.A;
import corner.orm.tapestry.page.PoListPage;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AListPage extends PoListPage{
	
	
	@Asset("classpath:/jasper/Atest.jasper")
	public abstract IAsset getJasperAsset();
	
	@Asset("classpath:/jasper/Btest.jasper")
	public abstract IAsset getJasperBAsset();
	
	/**
	 * @return
	 */
	public InputStream getReportStream(){
		return this.getJasperAsset().getResourceAsStream();
	}
	
	/**
	 * @return
	 * @throws JRException
	 */
	public JasperPrint getJasperPrint() throws JRException{
		List bowlerInfo = getCollection();
		return JasperFillManager.fillReport(getJasperBAsset().getResourceAsStream(), null, new JRBeanCollectionDataSource(bowlerInfo));
	}
	
	private List getCollection() {
		List<Object> rs = new ArrayList<Object>();
		A c = new A();
		c.setName("beijing");
		
		rs.add(c);
		return rs;
	}
	
	/**
	 * @return
	 */
	public abstract A getAtest();
	
	/**
	 * 测试使用的
	 */
	public List getCollectionTest() {
		List<Object> rs = new ArrayList<Object>();
		A c = new A();
		c.setName("a");
		rs.add(c);
		
		c = new A();
		c.setName("b");
		rs.add(c);
		
		c = new A();
		c.setName("c");
		rs.add(c);
		
		c = new A();
		c.setName("d");
		rs.add(c);
		
		c = new A();
		c.setName("e");
		rs.add(c);
		
		c = new A();
		c.setName("f");
		rs.add(c);
		
		return rs;
	}
}
