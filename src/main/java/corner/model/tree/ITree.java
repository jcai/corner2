/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: ITree.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-12-25
 */

package corner.model.tree;

/**
 * 树最基本的特性
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ITree {
	public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
	
	/**
	 * @return Returns the left.
	 */
	public int getLeft();

	/**
	 * @param left The left to set.
	 */
	public void setLeft(int left);

	/**
	 * @return Returns the right.
	 */
	public int getRight();

	/**
	 * @param right The right to set.
	 */
	public void setRight(int right);

	/**
	 * @return Returns the depth.
	 */
	public int getDepth();

	/**
	 * @param depth The depth to set.
	 */
	public void setDepth(int depth);
}
