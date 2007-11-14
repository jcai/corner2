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
		return new CornerPdfExporter();
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
