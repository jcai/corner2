// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-01
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

import corner.orm.hibernate.IPersistModel;

/**
 * 可进行版本控制的model接口.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IVersionable extends IPersistModel{
	/**
	 * 得到需要进行版本控制的属性数组列表.
	 * @return 需要版本控制的属性数组列表.
	 */
	public String[]  getNeedVersionableProperties();
	/**
	 * 得到提交时候的注释.
	 * @return 注释信息
	 */
	public String getSvnLog();
	/**
	 * 得到提交时候的作者信息.
	 * @return 作者信息
	 */
	public String getSvnAuthor();
	/**
	 * 是否提交
	 */
	public boolean isSvnCommit();
	
	//-------------------  以下属性需要保存到数据库中
	/**
	 * 版本号属性，此属性需要保存到数据库中
	 */
	public String getRevision();
	/**
	 * 设定版本号.
	 * @param revison
	 */
	public void setRevision(String revison);
	
	/**
	 * 获得对象上一级
	 * @return
	 */
	public Object getParentObject();
}
