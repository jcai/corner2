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

import corner.util.BeanUtils;

/**
 * 
 * 根据bean的属性来对值进行获取.
 * <P>采用的是BeanUtils.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-11-4
 * @see BeanUtils
 */
public final class BeanPropertyTableColumnEvaluator implements ITableColumnEvaluator{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2746967267411020412L;

	public Object getColumnValue(ITableColumn column, Object obj) {
		return BeanUtils.getProperty(obj,column.getColumnName());
	}
}
