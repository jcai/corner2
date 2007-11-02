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

package corner.service.svn;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * 针对进行版本控制的对象进行的映射.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionModelMapper extends MapperWrapper  {

	public static final String ENTITY_JSON_KEY="entity"; 
	public VersionModelMapper(Mapper wrapped) {
		super(wrapped);
	}
	/**
	 * @see com.thoughtworks.xstream.mapper.MapperWrapper#serializedClass(java.lang.Class)
	 */
	@Override
	public String serializedClass(Class type) {
		//注释部分为动态改变转换后的cglib的类名.
//		if (type.getName().contains("CGLIB")) {//是否为hibernate代理的类.
//			return type.getSuperclass().getName();
//		}
//		return type.getName();
		return ENTITY_JSON_KEY;
	}
}
