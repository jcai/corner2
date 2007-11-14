// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-02
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
