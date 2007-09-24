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

package corner.orm.tapestry.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * 传递参数使用的
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IJasperParameter {
	/**
	 * 获得集合
	 * @throws JRException 
	 */
	public Map getJasperParameters() throws JRException;
}