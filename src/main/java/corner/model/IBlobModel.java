// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-26
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

package corner.model;

/**
 * blob模型的接口
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public interface IBlobModel {
	
	public static final String BLOB_DATA_PRO_NAME="blobData";
	public static final String BLOB_NAME_PRO_NAME="blobName";
	public static final String CONTENT_TYPE_PRO_NAME="contentType";

	public abstract byte[] getBlobData();

	public abstract void setBlobData(byte[] blobData);

	public abstract String getContentType();

	public abstract void setContentType(String contentType);
	
	/**
	 * 设置上传的文件名称(包括文件名称和扩展名),用于下载的时候让浏览器可以自动关联程序打开改文件
	 * @param fileName
	 */
	public abstract void setBlobName(String blobName);
	
	public abstract String getBlobName();

}