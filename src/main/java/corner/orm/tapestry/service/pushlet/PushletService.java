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

package corner.orm.tapestry.service.pushlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.RequestGlobals;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.web.WebResponse;

import corner.util.Constants;

/**
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class PushletService implements IEngineService {
	
	public static final String START_DOCUMENT =
		"<html><head><meta http-equiv=\"Pragma\" content=\"no-cache\"><meta http-equiv=\"Expires\" content=\"Tue, 31 Dec 1997 23:59:59 GMT\"></head>";
	public static final String END_DOCUMENT = "<body> <span id=\"test\" name=\"test\" /> </body></html>";
	
	/** The content type for an Excel response */
	protected static final String CONTENT_TYPE = "text/plain";

	protected static final String SERVICE_NAME = "pushlet";

	private RequestGlobals requestGlobals;
 
    public void setRequestGlobals(RequestGlobals requestGlobals){
    	this.requestGlobals = requestGlobals;
    }
	
	

	/** link factory */
	protected LinkFactory _linkFactory;

	/** response */
	protected WebResponse _response;

	/** request cycle * */
	protected IRequestCycle _requestCycle;

	public ILink getLink(boolean post, Object parameter) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ServiceConstants.PAGE, this._requestCycle.getPage()
				.getPageName());
		parameters.put(ServiceConstants.PARAMETER, parameter);

		return _linkFactory.constructLink(this, post, parameters, true);
	}

	public String getName() {
		return SERVICE_NAME;
	}

	public void service(IRequestCycle cycle) throws IOException {
		System.out.println("start to push!");
		new ShowMessage().run();
	}
	
	/**
	 * @param cycle
	 *            The _requestCycle to set.
	 */
	public void setRequestCycle(IRequestCycle cycle) {
		_requestCycle = cycle;
	}

	/**
	 * @param factory
	 *            The _linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory factory) {
		_linkFactory = factory;
	}

	/**
	 * @param _response
	 *            The _response to set.
	 */
	public void setResponse(WebResponse _response) {
		this._response = _response;
	}
	
	class ShowMessage implements Runnable{
		
		public void show(HttpServletResponse response, OutputStream os) throws IOException{
			StringBuffer outStr = new StringBuffer();
			String msg = Constants.getMsg();			
			
			if(msg != null && msg.length()>0){
				outStr.append(START_DOCUMENT);
				outStr.append("<script language='javascript'> parent.onMessageShow('");
				outStr.append(msg);
				outStr.append("') </script>");
				outStr.append(END_DOCUMENT);
				System.out.println("outStr is:"+outStr.toString());
			} else{
				outStr.append(" ");
			}

			response.setContentType(CONTENT_TYPE);
			response.setStatus(HttpServletResponse.SC_OK);
			os.write(outStr.toString().getBytes());
			os.flush();
			response.flushBuffer();
		}

		public void run() {
			//tapestry.globals.
			HttpServletResponse response = requestGlobals.getResponse();
			OutputStream os = null;
			try {
				os = response.getOutputStream();
			} catch (IOException e1) {
				System.out.println("Thread-"+Thread.currentThread().getId()+" exit!");
				e1.printStackTrace();
			}
			while(true){
				try {
					this.show(response, os);
					Thread.sleep(5000);
				} catch (IOException e) {
					System.out.println("client exit!");
					break;
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					break;
				}
			}
		}
	}
}
