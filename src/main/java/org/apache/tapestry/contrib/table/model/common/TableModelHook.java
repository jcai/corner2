//==============================================================================
//file :        TablePages.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package org.apache.tapestry.contrib.table.model.common;

import org.apache.tapestry.contrib.table.model.ITableModel;




/**
 * 用来获取行数的钩子程序
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public final class TableModelHook{

	/**
	 * 大约的记录数
	 * @return 大约记录数
	 */
	public static int getAboutRowCount(ITableModel model){
		//TODO 对不能cast的model应该进行处理
		return ((AbstractTableModel) model).getRowCount();
	}

//	public static void storePage(ITableModelSource objSource) {
//		 objSource.storeTableAction(new TableActionPageChange(getPn()));
//		
//	}
}
