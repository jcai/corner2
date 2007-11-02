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

import org.hibernate.proxy.HibernateProxy;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import corner.util.BeanUtils;

/**
 * 所有需要进行版本控制的实体需要实现的接口.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionModelConverter extends ReflectionConverter{

	public VersionModelConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
		super(mapper, reflectionProvider);
	}

	
	/**
	 * 所有实现了 {@link IVersionable}接口的类才被转换.
	 * @see com.thoughtworks.xstream.converters.reflection.ReflectionConverter#canConvert(java.lang.Class)
	 */
	@Override
	public boolean canConvert(Class type) {
		return IVersionable.class.isAssignableFrom(type);
	}


	/**
	 * @see com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter#marshal(java.lang.Object, com.thoughtworks.xstream.io.HierarchicalStreamWriter, com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	@Override
	public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context) {
		if(isHibernateProxiedObject(original)){//是hibernate的代理类.
			original=((HibernateProxy)original).getHibernateLazyInitializer().getImplementation();
		}
		
		IVersionable versionableObject=(IVersionable) original;
		for(String pro:versionableObject.getNeedVersionableProperties()){
			Object value = BeanUtils.getProperty(original, pro);
			if(value==null){
				continue;
			}
			writer.startNode(pro);
			writer.setValue(value.toString());
			writer.endNode();
		}
	}
	private boolean isHibernateProxiedObject(Object original){
		return HibernateProxy.class.isAssignableFrom(original.getClass());
	}
	
}
