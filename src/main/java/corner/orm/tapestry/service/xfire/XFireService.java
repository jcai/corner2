//==============================================================================
//file :        XFireService.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.service.xfire;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IRequestCycle;

/**
 * 提供处理XFire请求的Service
 * 
 * @author <a href=mailto:ghostbb8@gmail.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class XFireService extends AbstractXFireEngineService {

    private static final String XFIRE_SERVICE_NAME = "xfire";

	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return XFIRE_SERVICE_NAME;
	}

	/**
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		this.createController();
		try {
			this.xFireServletController.doService(request, response);
		} catch (ServletException e) {
			throw new ApplicationRuntimeException("调用xFireServletController.doService(request, response)方法时出错");
		}
	}

}
