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

package corner.orm.tapestry.page;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;

/**
 * 用于列表显示时候的操作
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public interface IListablePage<T> {
	/**
	 * 查询的实体.
	 *
	 * @return 查询实体.
	 */

	public abstract T getQueryEntity();
	public abstract void setQueryEntity(T obj);
	/**
	 * 得到列表的source
	 * @return table model
	 */
	public  IBasicTableModel getSource();
}
