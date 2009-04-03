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
package corner.model.tree;



/**
 * 树菜单
 * @author Corner Team
 * @version $Revision: 343 $
 * @since 0.0.1
 */
public interface ILeftMenu extends ITreeAdaptor{

	/**
	 * @return Returns the actionPage.
	 */
	public abstract String getActionPage();

	/**
	 * @param actionPage The actionPage to set.
	 */
	public abstract void setActionPage(String actionPage);

}