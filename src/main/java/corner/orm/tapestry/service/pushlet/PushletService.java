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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

/**
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class PushletService implements IEngineService {
	
	/** The content type for an Excel response */
	protected static final String CONTENT_TYPE = "text/html;charset=UTF-8";

	protected static final String SERVICE_NAME = "pushlet";

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
		new ShowMessage(cycle).run();
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
		private IRequestCycle _cycle;
		public ShowMessage(IRequestCycle cycle){
			this._cycle = cycle;
		}
		
		public void show() throws IOException{
			String outStr = "<script language='javascript'>parent.onMessageShow('"+System.currentTimeMillis()+"')</script>";	
			PrintWriter output = _response.getPrintWriter(new ContentType(CONTENT_TYPE));
			System.out.println("outStr is:"+outStr);
			
			output.print(outStr);
			output.flush();
		}

		public void run() {
			while(true){
				try {
					this.show();
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
