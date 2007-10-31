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

package corner.service.svn;


/**
 * Svn服务包
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitService<T extends ISvnModel>{
	
	/**
	 * svn信息
	 */
	private Object svnurl;
	private Object username;
	private Object password;
	
	/**
	 * 
	 */
	public void saveOrUpdateSvn(T o){
		System.out.println(svnurl);
		System.out.println(username);
		System.out.println(password);
	}
	
	public void deleteSvn(T o){
		
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