// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-28
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

package corner.orm.tapestry.page;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.request.IUploadFile;

/**
 * 抽象的针对blob的页面. 
 * <p>提供了上传文件的句柄,可编辑blob对象的判断,显示图像的Asset. 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-2-6
 */
public interface IBlobPage {
	
	/**
	 * 上传文件的句柄.
	 * @return 上传文件句柄.
	 */
	public abstract IUploadFile getUploadFile();
	/**
	 * 是否编辑blob.
	 * @return 是否编辑.
	 */
	public abstract boolean isEditBlob();
	/**
	 * 设定是否编辑blob对象.
	 * @param isEditBlob 是否编辑.
	 */
	public abstract void setEditBlob(boolean isEditBlob);
	/**
	 * 得到blob服务。
	 * @return blob服务
	 */
	@InjectObject("service:corner.blob.BlobService")
	public abstract IEngineService getBlobService();
	

}