//==============================================================================
//file :        CornerXFireServletController.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.service.xfire;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.transport.http.XFireServletController;

import corner.util.StringUtils;

/**
 * Corner使用的XFireServletController
 * <p>
 * 复写了从URL中查找暴露的Service名称的方法
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class CornerXFireServletController extends XFireServletController {
	
	private static final String WSDL_SUFFIX_NAME = "wsdl";

	public CornerXFireServletController(XFire xfire){
		super(xfire);
	}
	
	public CornerXFireServletController(XFire xfire, ServletContext servletContext){
		super(xfire, servletContext);
	}

	/**
	 * @see org.codehaus.xfire.transport.http.XFireServletController#getService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getService(HttpServletRequest request) {
		 String pathInfo = request.getRequestURI();

	        if (pathInfo == null)
	            return null;

	        String serviceName;
	        
	        if(pathInfo.lastIndexOf("/")>-1){
	        	int startIndex = pathInfo.lastIndexOf("/")+1;
	        	int endIndex = pathInfo.lastIndexOf(".");
	        	serviceName = pathInfo.substring(startIndex, endIndex);
	        } else {
	        	serviceName = "";
	        }
	        
	        return serviceName;
	}

	/**
	 * @see org.codehaus.xfire.transport.http.XFireServletController#isWSDLRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean isWSDLRequest(HttpServletRequest request) {
		String pathInfo = request.getRequestURI();
		if(StringUtils.notBlank(pathInfo) && pathInfo.lastIndexOf(".")>-1){//请求不为空
			int beginIndex = pathInfo.lastIndexOf(".")+1;
			String suffixName = pathInfo.substring(beginIndex);
			if(WSDL_SUFFIX_NAME.equals(suffixName.toLowerCase())){
				return true;
			}
		}
		
		return false;
	}
	
	
}
