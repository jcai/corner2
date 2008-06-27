/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: ILeftMenu.java 343 2008-05-20 04:28:13Z jcai $
 * created at:2008-05-13
 */

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