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

package corner.orm.tapestry.service.svn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.LinkFactory;

/**
 * Svn服务包
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitService implements IEngineService{
	
	/**
	 * svn信息
	 */
	private Object svnurl;
	private Object username;
	private Object password;
	
	private static final String ENTITY = "entity_svn";
	public static final String SERVICE_NAME = "svn";
	
	/** @since 4.0 */
	private LinkFactory linkFactory;
	
	private DataSqueezer dataSqueezer;
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		String objStr=cycle.getParameter(ENTITY);
		if(objStr!=null){
			ISvnModel svnEntity= (ISvnModel) this.dataSqueezer.unsqueeze(objStr);
			if(svnEntity==null){ //纠正blob对象为空的NPE异常
				return;
			}
			
			return;
		}
		
		System.out.println(svnurl);
		System.out.println(username);
		System.out.println(password);
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		Defense.isAssignable(parameter, Object[].class, "parameter");

		Object[] SvnParameters = (Object[]) parameter;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(SvnParameters.length != 0){
			parameters.put(ENTITY, this.dataSqueezer.squeeze(SvnParameters[0]));
		}
		
		return linkFactory.constructLink(this, false, parameters, false);
	}
	
	/**
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}

	/**
	 * @param dataSqueezer The dataSqueezer to set.
	 */
	public void setDataSqueezer(DataSqueezer dataSqueezer) {
		this.dataSqueezer = dataSqueezer;
	}

	/**
	 * @param linkFactory The linkFactory to set.
	 */
	public void setLinkFactory(LinkFactory linkFactory) {
		this.linkFactory = linkFactory;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(Object password) {
		this.password = password;
	}

	/**
	 * @param svnurl The svnurl to set.
	 */
	public void setSvnurl(Object svnurl) {
		this.svnurl = svnurl;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(Object username) {
		this.username = username;
	}
}