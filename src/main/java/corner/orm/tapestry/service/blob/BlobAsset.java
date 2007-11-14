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

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AbstractAsset;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;

/**
 * 提供了blob的Asset用以显示blob的值.
 * <p>显示一个blob的图片可以使用
 * 需要提供了两个参数:
 * <li>tableType 表的类型,见{@link poison.pageservice.BlobService#LOB_PROVIDER_CLAZZS}
 * <li>tableKey 表的主键值,{@link BlobService#LOB_PROVIDER_CLAZZS};
 *
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2006-1-20
 * @see org.apache.tapestry.IAsset
 */
public class BlobAsset extends AbstractAsset {

	private IEngineService _blobService;

	private List<Object> parameters = new ArrayList<Object>();

	/**
	 * 构造一个Asset
	 * @param cycle 客户请求.
	 * @param tableType 表的类型
	 * @param key 表的主键
	 * @since 2.0.6 
	 * 
	 */
	public BlobAsset(IEngineService blobService,IRequestCycle cycle, String tableType, Serializable key) {
		super(null, null);

		
		_blobService = blobService;

		parameters.add(tableType);
		parameters.add(key);

	}
	public BlobAsset(IEngineService blobService, IRequestCycle requestCycle, Object obj) {
		super(null,null);
		_blobService = blobService;
		parameters.add(obj);
		
	}
	/**
	 * 构建URL.
	 * @see org.apache.tapestry.IAsset#buildURL()
	 */
	public String buildURL() {
		ILink l = _blobService.getLink(false, parameters.toArray());

		return l.getURL();
	}

	public InputStream getResourceAsStream() {
		return null;
	}

	public InputStream getResourceAsStream(IRequestCycle cycle, Locale locale) {
		return null;
	}
}
