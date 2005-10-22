//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

public class PaginationBean {
	private int nFirst;
	private int nPageSize;
	private int rowCount;
	public int getNFirst() {
		return nFirst;
	}
	public void setNFirst(int first) {
		nFirst = first;
	}
	public int getNPageSize() {
		return nPageSize;
	}
	public void setNPageSize(int pageSize) {
		nPageSize = pageSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
