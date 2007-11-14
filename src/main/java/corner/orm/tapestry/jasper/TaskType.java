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
