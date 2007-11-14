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

import java.util.List;

/**
 * 提供版本服务的类.
 * <p>
 * 提供和subversion版本控制服务器进行交互的类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun tsai</a>
 * @version $Revision$
 * @since 2.5
 * 
 */
public interface IVersionService {

	/**
	 * 对一个需要进行版本控制的对象进行版本化.
	 * 
	 * @param versionableObject 待版本化的对象.
	 * @return 版本号
	 */
	public long checkin(IVersionable versionableObject);

	
	
	/**
	 * 从资源库中抓取对应的版本的记录信息.
	 * 
	 * @param versionableObject 待版本化的对象.
	 * @return 记录列表.
	 * @see VersionResult
	 */
	public  List<VersionResult> fetchVersionInfo(IVersionable versionableObject);
	/**
	 * 抓取某一条记录作为json传出.
	 * 
	 * @param versionableObject 待版本化的对象.
	 * @param revision 需要抓出来的版本号.version为 -1  的时候，为最新版本.
	 * @return json字符串.
	 */
	public  String fetchObjectAsJson(IVersionable versionableObject,long revision);
	/**
	 * 删除一个版本
	 * 
	 * @param versionableObject 待版本化的对象.
	 */
	public void delete(IVersionable versionableObject);
}
