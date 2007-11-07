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

import corner.orm.hibernate.IPersistModel;

/**
 * 可进行版本控制的model接口.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IVersionable extends IPersistModel{
	/**
	 * 得到需要进行版本控制的属性数组列表.
	 * @return 需要版本控制的属性数组列表.
	 */
	public String[]  getNeedVersionableProperties();
	/**
	 * 得到提交时候的注释.
	 * @return 注释信息
	 */
	public String getSvnLog();
	/**
	 * 得到提交时候的作者信息.
	 * @return 作者信息
	 */
	public String getSvnAuthor();
	
}
