//==============================================================================
//file :        AbstractXFireEngineService.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.service.xfire;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.engine.state.ApplicationScopeManager;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.transport.http.XFireServletController;

/**
 * 抽象的XFireEngineService
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public abstract class AbstractXFireEngineService implements IEngineService {
	
	/**
	 * CornerXFireServletController保存在{@link ApplicationScopeManager}的key
	 */
	public static final String CORNER_XFIRE_SERVLET_CONTROLLER_KEY = "CornerXFireServletController_key";
	
	/**
	 * XFire保存在{@link ApplicationScopeManager}的key
	 */
	public static final String CORNER_XFIRE_KEY = "xfire_key";
	
	private static final Log log = LogFactory.getLog(AbstractXFireEngineService.class);

	protected static Object obj = new Object();
	/**
	 * 注入ServletContext
	 */
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	/** @since 4.0 */
	protected LinkFactory linkFactory;
	protected XFire xfire;
	protected XFireServletController xFireServletController;

	/**
	 * @param linkFactory The linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory linkFactory) {
		this.linkFactory = linkFactory;
	}

	/**
	 * @param servletContext The servletContext to set.
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * @param request The request to set.
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @param response The response to set.
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param xfire The xfire to set.
	 */
	public void setXfire(XFire xfire) {
		this.xfire = xfire;
	}

	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		
	    Defense.isAssignable(parameter, String.class, "parameter");
	
	    Map parameters = new HashMap();
	
	    parameters.put(ServiceConstants.PAGE, parameter);
	
	    return linkFactory.constructLink(this, post, parameters, true);
	}

	/**
	 * 创建XFireServletController实例
	 */
	public void createController() {
		Object xFireServletControllerObj = this.servletContext.getAttribute(CORNER_XFIRE_SERVLET_CONTROLLER_KEY);
		if(xFireServletControllerObj != null){
			return;
		} else{
			synchronized(obj){
				if(xFireServletController==null){
					if (log.isInfoEnabled()) {
						log.info("inital xFireServletController.");
					}
					
					xFireServletController=new CornerXFireServletController(xfire, servletContext);
					//cache xFireServletController instance
					this.servletContext.setAttribute(CORNER_XFIRE_SERVLET_CONTROLLER_KEY, xFireServletController);
				}
			}
		}
	}
}
