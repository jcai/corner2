/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-14
 */

package corner.orm.tapestry.jasper;

import java.applet.Applet;
import java.net.URL;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JRPrinterApplet extends Applet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3127611999010424523L;
	private URL url = null;

	public void init() {
		String strUrl = getParameter("REPORT_URL");
		if (strUrl != null) {
			try {
				System.out.println(getCodeBase());
				url = new URL(getCodeBase(), strUrl);// 从获得html参数中获得报表URL
				System.out.println(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(this, "Source URL not specified");

	}

	public void start() {
		if (url != null) {
			try {
				JOptionPane.showMessageDialog(this, url);
				Object obj = JRLoader.loadObject(url);// 发送对象请求，获得JasperPrint对象
				System.out.println(obj);
				JasperPrintManager.printReport((JasperPrint) obj, true);// 调用方法打印所获得的JasperPrint对象
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
