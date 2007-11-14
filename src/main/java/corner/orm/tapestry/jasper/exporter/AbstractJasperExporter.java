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

import java.io.IOException;

import net.sf.jasperreports.engine.JRExporter;

/**
 * 超类用来初始化
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractJasperExporter implements IJasperExporter{

	/**
	 * 整理设置Exporter
	 * @throws IOException
	 */
	public void setupExporter(JRExporter exporter){
		//do nothing
	}
	
	/**
	 * 获得处理类
	 */
	public JRExporter getExporter() {
		throw new UnsupportedOperationException("需要在子类中实现！");
	}
}