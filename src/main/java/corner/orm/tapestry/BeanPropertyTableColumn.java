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

package corner.orm.tapestry;

import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.contrib.table.model.simple.ITableColumnEvaluator;
import org.apache.tapestry.contrib.table.model.simple.SimpleTableColumn;

import corner.util.BeanUtils;

/**
 * 
 * 基本的根据bean的属性来得到值.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-4
 */
public class BeanPropertyTableColumn extends SimpleTableColumn {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6905390842282898107L;
	/**
	 * 有列的名称来构造.
	 * @param strColumnName 列的名称.
	 */
	public BeanPropertyTableColumn(String strColumnName) {
		super(strColumnName, strColumnName);
		this.setEvaluator(new ITableColumnEvaluator() {
			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = -6282345521481814769L;

			public Object getColumnValue(ITableColumn column, Object obj) {
				return BeanUtils.getProperty(obj, column.getColumnName());
			}
		});
	}

}
