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

package corner.orm.tapestry;

import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.contrib.table.model.simple.ITableColumnEvaluator;
import org.apache.tapestry.contrib.table.model.simple.SimpleTableColumn;

import corner.util.BeanUtils;

/**
 * 
 * �����ĸ���bean���������õ�ֵ.
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
	 * ���е�����������.
	 * @param strColumnName �е�����.
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