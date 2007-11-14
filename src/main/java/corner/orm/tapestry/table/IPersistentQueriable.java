// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-24
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

package corner.orm.tapestry.table;
import org.hibernate.Criteria;
import org.hibernate.Session;


/**
 * 针对hibernate查询提供的接口服务。
 * @author Jun Tsai
 * @version $Revision:3677 $
 * @since 2006-5-24
 */
public interface IPersistentQueriable {
	/**
	 * 创建一个offline的Cirteria
	 * @return criteria detached
	 */
	public Criteria createCriteria(Session session);
	/**
	 * 对查询进行一个性化的设置。
	 * @param criteria
	 */
	public void appendCriteria(Criteria criteria);
	/**
	 * 对查询进行排序处理.
	 * @param criteria 查询对象.
	 * @since 2.2.1
	 */
	public void appendOrder(Criteria criteria);
}
