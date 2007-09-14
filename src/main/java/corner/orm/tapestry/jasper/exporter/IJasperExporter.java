/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-10
 */

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
