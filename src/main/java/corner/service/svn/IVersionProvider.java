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

import org.apache.tapestry.annotations.InjectObject;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IVersionProvider {
	@InjectObject("spring:versionService")
    public abstract SubversionService getSubversionService();
	
	/**
	 * 要显示的版本号1
	 */
	public abstract long getVersionNum();
	
	/***
	 * 要显示的版本号1
	 */
	public abstract  void  setVersionNum(long versionNum);
	
	/**
	 * 要显示的版本号2
	 */
	public abstract long getOtherVersionNum();
	
	/***
	 * 要显示的版本号2
	 */
	public abstract  void  setOtherVersionNum(long versionNum);
}