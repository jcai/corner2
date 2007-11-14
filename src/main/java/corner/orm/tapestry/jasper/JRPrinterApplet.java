// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-14
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.jasper;

import java.applet.Applet;
import java.net.URL;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 打印使用的小程序
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
//		String strUrl = "http://168.168.168.65/jasperPrinter.svc?page=upload%2FAPersonList&sp=X&sp=X&sp=X&sp=HB%3Acorner.demo.model.one.A%3A%3AS282828c11351b340011351b40a9d0001&sp=Satest&sp=ScollectionTest";
		if (strUrl != null) {
			try {
				url = new URL(strUrl);// 从获得html参数中获得报表URL
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
//				JOptionPane.showMessageDialog(this, url);
				Object obj = JRLoader.loadObject(url);// 发送对象请求，获得JasperPrint对象
				JasperPrintManager.printReport((JasperPrint) obj, true);// 调用方法打印所获得的JasperPrint对象
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

