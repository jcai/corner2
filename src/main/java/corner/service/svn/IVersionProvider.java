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
	 * 得到实体。
	 * @return 实体。
	 */
	public abstract long getVersionNum();
	/***
	 * 设定实体。
	 * @param entity 实体。
	 */
	public abstract  void  setVersionNum(long versionNum);
}