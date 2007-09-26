/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-13
 */

package corner.orm.tapestry.jasper.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.util.ContentType;

import corner.model.IBlobModel;

/**
 * 导出实体的服务类.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JasperPrinterLinkService extends JasperLinkService{
	
	/**
	 * @see corner.orm.tapestry.jasper.service.JasperLinkService#service(org.apache.tapestry.IRequestCycle, org.apache.tapestry.IPage, boolean, java.lang.String, corner.model.IBlobModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void service(IRequestCycle cycle, IPage page, boolean isUsetemplatePath, String templatePath, IBlobModel templateEntity, String downloadFileName, String taskType, String detailEntity, String detailCollection) throws IOException {
		try {
			//判断是从那里读取流
			InputStream is = isUsetemplatePath ? getAssetStream(page,templatePath) : getAssetStream(templateEntity);
			
			JasperPrint jasperPrint = getJasperPrint(is,page,detailEntity,detailCollection);
			
			ObjectOutputStream os = new ObjectOutputStream(response.getOutputStream(new ContentType()));
			os.writeObject(jasperPrint);
			os.close();
		} catch (JRException e) {
			throw new ApplicationRuntimeException(e);
		}
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	
	/**
	 * 
	 */
	public static final String SERVICE_NAME = "jasperPrinter";
}
