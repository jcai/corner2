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

/**
 * 所有的jasper操作类都实现此接口
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IJasperExporter {
	/**
	 * 设置后缀
	 * @return
	 */
	public String getSuffix();
	
	/**
	 * 设置httpheader返回类型
	 * @return
	 */
	public String getContentType();
	/**
	 * 得到jasper的report对象.
	 * @return jasper report object
	 */
	public JRExporter getExporter();

	/**
	 * 对exporter的初始操作.
	 * @param exporter JRExporter
	 */
	public void setupExporter(JRExporter exporter);
}
