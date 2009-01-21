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

/**
 * 移动JasperReport文本(StaticText,TextField)的X,Y轴坐标.
 * 以向右,向下方向为加.
 * @author lsq
 * @version $Revision$
 * @since 2.5.2
 */
public interface IJasperMoveXY {

	public int getX();
	public void setX(int x);
	
	public int getY();
	public void setY(int y);
}
