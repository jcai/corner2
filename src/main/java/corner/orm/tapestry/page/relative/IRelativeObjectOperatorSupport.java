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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.annotations.InjectObject;

import corner.orm.tapestry.page.relative.support.IRelativeObjectOperator;

/**
 * 提供了对关联对象操作.
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public interface IRelativeObjectOperatorSupport {
	/**
	 * 得到关联对象操作者.
	 * @return 关联对象操作者。
	 */
	@InjectObject("service:corner.orm.relativeOperator")
	public abstract IRelativeObjectOperator getRelativeObjectOperator();
}
