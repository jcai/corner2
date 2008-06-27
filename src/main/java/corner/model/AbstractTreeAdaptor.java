/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractTreeAdaptor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-20
 */

package corner.model;

import corner.service.tree.ITreeAdaptor;

/**
 * 抽象的树的模型.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class AbstractTreeAdaptor extends AbstractCodeModel implements
		ITreeAdaptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410796891166599761L;

	/**
	 * 用来对树型结构处理的属性.
	 */
	/**
	 * 保存树型结构的左边代码
	 * @hibernate.property
	 * 
	 * @hibernate.column name="TREE_LEFT_CODE" comment="左边的代码" sql-type="Numeric"
	 */
	private int left;

	/**
	 * 保存树型结构的左边代码
	 * 
	 * @hibernate.property
	 * @hibernate.column name="TREE_RIGHT_CODE" comment="右边的代码" sql-type="Numeric"
	 */
	private int right;

	/**
	 * 保存当前节点的深度
	 * 
	 * @hibernate.property
	 * @hibernate.column name="TREE_DEPTH" comment="树的深度" sql-type="Numeric"
	 */
	private int depth;

	/**
	 * @return Returns the left.
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * @param left
	 *            The left to set.
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return Returns the right.
	 */
	public int getRight() {
		return this.right;
	}

	/**
	 * @param right
	 *            The right to set.
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * @return Returns the depth.
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * @param depth
	 *            The depth to set.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * 
	 * @see com.bjmaxinfo.piano.model.ITreeAdaptor#getIndentString()
	 */
	public String getIndentString() {
		StringBuffer sb = new StringBuffer();
		int i = this.getDepth();
		while (i > 1) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			i--;
		}
		return sb.toString();
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ITreeAdaptor#getNodeName()
	 */
	public String getNodeName() {
		return getChnName();
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ITreeAdaptor#setNodeName(java.lang.String)
	 */
	public void setNodeName(String name) {
		setChnName(name);
	}
    /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */
}
