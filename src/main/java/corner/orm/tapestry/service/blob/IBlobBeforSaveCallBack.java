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

import corner.model.IBlobModel;

/**
 * 针对blob对象在保存前的操作回调接口.
 * <p>提供在保存blob对象之前的调用.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2006-2-6
 */
public interface IBlobBeforSaveCallBack<T  extends IBlobModel> {
	/**
	 * 在blob保存之前的操作.
	 * @param blob blob对象.
	 */
	public void doBeforeSaveBlob(T blob);

}
