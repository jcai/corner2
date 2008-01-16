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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.RequestGlobals;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.web.WebResponse;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.orm.tapestry.component.pushlet.IMessageModel;
import corner.orm.tapestry.component.pushlet.IPushletFramePage;
import corner.service.EntityService;

/**
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class PushletService implements IEngineService {
	
	private static final String QUERY_MESSAGE_ENTITY_NAME = "query_message_entity_name";
	
	private static final String QUERY_MESSAGE_LIST_PAGE_NAME = "query_message_list_page_name";
	
	private static final String BLANK_STR = " ";
	
	public static final String START_DOCUMENT =
		"<html><head><meta http-equiv=\"Pragma\" content=\"no-cache\"><meta http-equiv=\"Expires\" content=\"Tue, 31 Dec 1997 23:59:59 GMT; text/html; charset=utf-8\"></head>";
	public static final String END_DOCUMENT = "<body> <span id=\"test\" name=\"test\" /> </body></html>";
	
	/** The content type for an Excel response */
	protected static final String CONTENT_TYPE = "text/html;charset=UTF-8";

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
		
		DirectServiceParameter dsp = (DirectServiceParameter) parameter;
	    IComponent component = dsp.getDirect();
	    
        IPage activePage = _requestCycle.getPage();
        IPage componentPage = component.getPage();
		
	    Object[] objs = dsp.getServiceParameters();
	    
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(ServiceConstants.PAGE, activePage.getPageName());
        parameters.put(ServiceConstants.COMPONENT, component.getIdPath());
        parameters.put(ServiceConstants.CONTAINER, componentPage == activePage ? null
                : componentPage.getPageName());
		parameters.put(QUERY_MESSAGE_ENTITY_NAME, objs[0]);//保存message实体的类名到request cycle中
		parameters.put(QUERY_MESSAGE_LIST_PAGE_NAME, objs[1]);//保存message类表页面名称

		return _linkFactory.constructLink(this, post, parameters, true);
	}

	public String getName() {
		return SERVICE_NAME;
	}

	public void service(IRequestCycle cycle) throws IOException {
		System.out.println("start to push!");
		String componentPageName = cycle.getParameter(ServiceConstants.PAGE);
		String messageEntityName = cycle.getParameter(QUERY_MESSAGE_ENTITY_NAME);
		String messageListPageName = cycle.getParameter(QUERY_MESSAGE_LIST_PAGE_NAME);
		IPushletFramePage page = (IPushletFramePage)cycle.getPage(componentPageName);
		cycle.activate(page);
		
		DetachedCriteria dCriteria = page.addCriteria(DetachedCriteria.forEntityName(messageEntityName));
		EntityService entityService = page.getEntityService();
		
		ShowMessage showMsg = new ShowMessage();
		showMsg.setDCriteria(dCriteria);
		showMsg.setEntityService(entityService);
		showMsg.setMessageListPageName(messageListPageName);
		showMsg.run();
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
		
		/**
		 * 注入并cache查询条件
		 */
		private DetachedCriteria dCriteria;
		
		/**
		 * 注入EntityService
		 */
		private EntityService entityService;
		
		private String messageListPageName;
		
		public void show(HttpServletResponse response, OutputStream os, List<String> messages) throws IOException{
			if(messages != null && messages.size()>0){
				for(String msg:messages){
					StringBuffer outStr = new StringBuffer();
					if(msg != null && msg.length()>0){
						System.out.println("send a message here!");
						outStr.append(START_DOCUMENT);
						outStr.append("<script language='javascript'> parent.onMessageShow('");
						outStr.append(msg);
						outStr.append("','"+messageListPageName+"') </script>");
						outStr.append(END_DOCUMENT);
						System.out.println("outStr is:"+outStr.toString());
					} else{
						outStr.append(BLANK_STR);
					}
					this.flushBuffer(response, os, outStr.toString());
				}
			} else{
				this.flushBuffer(response, os, BLANK_STR);
			}

		}
		
		private void flushBuffer(HttpServletResponse response, OutputStream os, String outStr) throws IOException{
			response.setContentType(CONTENT_TYPE);
			response.setStatus(HttpServletResponse.SC_OK);
			os.write(outStr.getBytes("UTF-8"));
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
			
			List<IMessageModel> messageModelList = (List<IMessageModel>)((HibernateDaoSupport)entityService.getObjectRelativeUtils()).getHibernateTemplate().findByCriteria(this.getDCriteria());
			List<String> messages = null;
			if(messageModelList != null && messageModelList.size()>0){
				messages = new ArrayList<String>();
				for(IMessageModel model:messageModelList){
					messages.add(model.getMessageTitle());
				}
			}
			
			while(true){
				try {
					this.show(response, os, messages);
					Thread.sleep(12000);
				} catch (IOException e) {
					System.out.println("client exit!");
					Thread thread = Thread.currentThread();
					if(!thread.isInterrupted()){
						thread.interrupt();
					}
					break;
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					Thread thread = Thread.currentThread();
					if(!thread.isInterrupted()){
						thread.interrupt();
					}
					break;
				}
			}
		}

		public DetachedCriteria getDCriteria() {
			return dCriteria;
		}

		public void setDCriteria(DetachedCriteria criteria) {
			dCriteria = criteria;
		}

		public EntityService getEntityService() {
			return entityService;
		}

		public void setEntityService(EntityService entityService) {
			this.entityService = entityService;
		}

		/**
		 * @return Returns the messageListPageName.
		 */
		public String getMessageListPageName() {
			return messageListPageName;
		}

		/**
		 * @param messageListPageName The messageListPageName to set.
		 */
		public void setMessageListPageName(String messageListPageName) {
			this.messageListPageName = messageListPageName;
		}
	}
}
