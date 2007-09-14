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