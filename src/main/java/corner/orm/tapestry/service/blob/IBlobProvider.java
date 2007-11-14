// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-16
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

package corner.orm.tapestry.service.blob;

import corner.service.EntityService;

/**
 * 提供一个blob图像来源.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2006-1-19
 */
public interface IBlobProvider {
	/**
	 * 返回blob对象为字节数组.
	 * @return 字节数组.
	 */
	public byte[] getBlobAsBytes();
	/**
	 * blob的类型,通常用来在web上的显示.
	 * @return blob的类型.
	 */
	public String getContentType();
	/**
	 * blob的主键值.
	 * @param tableKey 主键值.
	 */
	public void setKeyValue(String tableKey);
	/**
	 * 设置实体服务.
	 * 
	 * @param entityService 实体服务.
	 */
	public void setEntityService(EntityService entityService);
}
