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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.components.IPrimaryKeyConverter;

import corner.util.BeanUtils;

/**
 * 根据主键值来进行对Object进行转换.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-11-4
 */
public class ReflectPrimaryKeyConverter<T> implements IPrimaryKeyConverter {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ReflectPrimaryKeyConverter.class);
	
	private String idStr;
	private Class<T> c;

	public ReflectPrimaryKeyConverter(Class<T> c,String idStr){
		this.idStr=idStr;
		this.c=c;
	}
	/**
	 * 根据object来得到对应的主建值.
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug("getPrimaryKey(Object) -  : Object obj=" + obj); //$NON-NLS-1$
		}
		return BeanUtils.getProperty(obj,idStr);
	}
	/**
	 * 根据主键值来得到对应的Object.
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object id) {
		try {
			Object obj=c.newInstance();
			BeanUtils.setProperty(obj,idStr,id);

			if (logger.isDebugEnabled()) {
				logger.debug("getValue(Object) id["+id+"]"); //$NON-NLS-1$
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
