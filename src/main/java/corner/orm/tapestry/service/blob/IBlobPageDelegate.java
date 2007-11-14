// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-09-22
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

import corner.model.IBlobModel;

/**
 * 提供了对bolb处理的委派类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public interface IBlobPageDelegate<T extends IBlobModel> {

	/**
	 * 保存blob对象.
	 * @param callback 回调函数.
	 * @see org.springframework.orm.hibernate3.support.BlobByteArrayType
	 * 
	 */
	public abstract void save(IBlobBeforSaveCallBack<T> callback);

	/**
	 * 保存blob.
	 * @see #save(IBlobBeforSaveCallBack)
	 */
	public abstract void save();

}