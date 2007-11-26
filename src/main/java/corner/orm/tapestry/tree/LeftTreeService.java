//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

/**
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public class LeftTreeService implements IEngineService{
	
	/**
	 * 
	 */
	public static final String SERVICE_NAME = "leftTree";
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ServiceConstants.PAGE, this.requestCycle.getPage()
				.getPageName());
		parameters.put(ServiceConstants.PARAMETER, parameter);
		
		return linkFactory.constructLink(this, post, parameters, true);
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		
//		String date = ;

//		JSONObject json = new JSONObject("{\"nodes\":[{\"id\":\"test1\",\"type\":\"leftTreeSite\",\"data\":\"Ajax Element 1\"},{\"id\":\"test2\",\"type\":\"leftTreeSite\",\"data\":\"Ajax Element 2\"}]}");
		
		String date = "{\"nodes\":[{\"id\":\"test1\",\"type\":\"leftTreeSite\",\"data\":{name:\"Ajax Element 1\",\"left\":22,\"right\":33}},{\"id\":\"test2\",\"type\":\"leftTreeSite\",\"data\":{name:\"Ajax Element 2\",left:23,right:24}}]}";
		
		String activePageName = cycle.getParameter(ServiceConstants.PAGE);
		IPage page = cycle.getPage(activePageName);
		cycle.activate(page);
		
		String left = cycle.getParameter("left");
		String right = cycle.getParameter("right");
		
		System.out.println("left : " + left);
		System.out.println("right : " + right);
		
		PrintWriter pw = response.getPrintWriter(new ContentType("text/html"));
		
		pw.write(date);
		
	}
	
	/** link factory */
	protected LinkFactory linkFactory;

	/** response */
	protected WebResponse response;

	/** request cycle * */
	protected IRequestCycle requestCycle;
	
	/**
	 * @param linkFactory The linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory linkFactory) {
		this.linkFactory = linkFactory;
	}

	/**
	 * @param requestCycle The requestCycle to set.
	 */
	public void setRequestCycle(IRequestCycle requestCycle) {
		this.requestCycle = requestCycle;
	}

	/**
	 * @param response The response to set.
	 */
	public void setResponse(WebResponse response) {
		this.response = response;
	}
}
