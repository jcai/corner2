/*		
 * Copyright 2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: TreeSelectModel.java 10312 2008-06-27 04:11:21Z xf $
 * created at:2008-06-04
 */

package corner.orm.tapestry.component.tree;

import java.util.List;

import org.apache.tapestry.IPage;
import org.apache.tapestry.json.JSONObject;

import corner.service.tree.ITreeAdaptor;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision: 10312 $
 * @since 1.0.0
 */
public abstract class AbstractTreeSelectModel implements ITreeSelectModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6410796891166599761L;

	/**
	 * 用来对树型结构处理的属性.
	 */
	/**
	 * 保存树型结构的左边代码
	 */
	private int left;

	/**
	 * 保存树型结构的左边代码
	 * 
	 */
	private int right;

	/**
	 * 保存当前节点的深度
	 * 
	 */
	private int depth;
	
	/**
	 * 查询类名
	 */
	private String queryClassName;
	
	/**
	 * 依赖字段
	 */
	private String [] depends;
	
	/**
	 * 所掉页面
	 */
	private IPage parentPage;
	
	/**
	 * 返回字段对照
	 */
	private JSONObject returnValues;
	
	/**
	 * 查询出来的结果集
	 */
	private List<? extends ITreeAdaptor> treeList;
	
	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getLeft()
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setLeft(int)
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getRight()
	 */
	public int getRight() {
		return this.right;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setRight(int)
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getDepth()
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getNextDepth()
	 */
	public int getNextDepth(){
		return this.depth+1;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setDepth(int)
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getQueryClassName()
	 */
	public String getQueryClassName() {
		return queryClassName;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setQueryClassName(java.lang.String)
	 */
	public void setQueryClassName(String queryClassName) {
		this.queryClassName = queryClassName;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getDepends()
	 */
	public String[] getDepends() {
		return depends;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setDepends(java.lang.String[])
	 */
	public void setDepends(String[] depends) {
		this.depends = depends;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getParentPage()
	 */
	public IPage getParentPage() {
		return parentPage;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setParentPage(org.apache.tapestry.IPage)
	 */
	public void setParentPage(IPage parentPage) {
		this.parentPage = parentPage;
	}
	

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getReturnValues()
	 */
	public JSONObject getReturnValues() {
		return returnValues;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setReturnValues(org.apache.tapestry.json.JSONObject)
	 */
	public void setReturnValues(JSONObject returnValues) {
		this.returnValues = returnValues;
	}
	
	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#getTreeList()
	 */
	public List<? extends ITreeAdaptor> getTreeList() {
		return treeList;
	}

	/**
	 * @see corner.orm.tapestry.component.tree.ITreeSelectModel#setTreeList(java.util.List)
	 */
	public void setTreeList(List<? extends ITreeAdaptor> treeList) {
		this.treeList = treeList;
	}
}
