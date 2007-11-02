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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * 针对Xstream的代理类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public  class XStreamDelegate {

	/**
	 * 通过给定的模型来产生对应的JSON字符串.
	 * @param model 模型实体.
	 * @return 转换后的字符串.
	 */
	public static String toJSON(IVersionable model){
		XStream xstream = new XStream(new JettisonMappedXmlDriver()){
			//添加hibernate的映射类.
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new VersionModelMapper(next);
			}
		};
		//注册版本控制模型的转换类.
        xstream.registerConverter(new VersionModelConverter(xstream.getMapper(),new PureJavaReflectionProvider()),XStream.PRIORITY_VERY_HIGH);
       
        //生成JSON字符串.
        return xstream.toXML(model);
	}
}
