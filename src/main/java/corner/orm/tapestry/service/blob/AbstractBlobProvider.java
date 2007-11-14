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

import java.io.Serializable;

import corner.model.IBlobModel;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 抽象的blob提供对象.
 * <p>提供了blob的读取操作.{@link #getBlobAsBytes() 获取blob字节}{@link #getContentType() 字节流类型}
 * 用以装载blob数据.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2006-1-20
 */
public abstract class AbstractBlobProvider<T extends IBlobModel> implements IBlobProvider {
	
	/**blob数据模型对象**/
	private T blobData;
	/**实体服务**/
	private EntityService service;
	/**实体对象的主键值**/
	private Serializable blobId;
	/**
	 * 得到blob对象的类.
	 * @return blob模型对象的类.
	 */
	protected abstract Class<T> getBlobDataClass();
	
	/**
	 * 设定blob模型的主键值.
	 * @see corner.orm.tapestry.service.blob.IBlobProvider#setKeyValue(java.lang.String)
	 */
	public void setKeyValue(String tableKey) {
		this.blobId=tableKey;

	}
	/**
	 * 设定单态实体Service.
	 * @see corner.orm.tapestry.service.blob.IBlobProvider#setEntityService(corner.service.EntityService)
	 */
	public void setEntityService(
			EntityService entityService) {
		this.service=entityService;
	}
	/**
	 * 得到blob模型对象.
	 * @return blob模型对象.
	 */
	private T getBlobObject(){
		loadData();
		return blobData;
	}
	/**
	 * 把blob数据作为字节数组读出.
	 * @see corner.orm.tapestry.service.blob.IBlobProvider#getBlobAsBytes()
	 */
	public byte[] getBlobAsBytes() {
		return this.getBlobObject().getBlobData();
		
	}
	/**
	 * 得到blob数据的类型,用以显示blob数据.
	 * @see corner.orm.tapestry.service.blob.IBlobProvider#getContentType()
	 */
	public String getContentType() {
		return this.getBlobObject().getContentType();
	}
	/**
	 * 装载blob对象的数据.
	 *
	 */
	private void loadData() {
		if (blobData == null&&blobId!=null) {
			blobData=this.service.getEntity(getBlobDataClass(),blobId);
			
		}
		if(blobData==null){
			blobData=BeanUtils.instantiateClass(getBlobDataClass());
		}

	}

}
