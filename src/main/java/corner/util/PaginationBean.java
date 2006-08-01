//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

/**
 * 用于分页的bean.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-10-25
 */
public class PaginationBean {
	/***默认的每页记录数*/
	public static final int DEFAULT_PAGE_SIZE=10;
	/** 开始的记录数,从0开始 **/
	private int first;
	/** 每页显示记录数,通常默认为{@link #DEFAULT_PAGE_SIZE}**/
	private int pageSize=10;
	/**得到总的记录数**/
	private int rowCount=-1;
	/**
	 * @return Returns the first.
	 */
	public int getFirst() {
		return first;
	}
	/**
	 * @param first The first to set.
	 */
	public void setFirst(int first) {
		this.first = first;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the rowCount.
	 */
	public int getRowCount() {
		return rowCount;
	}
	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	
}
