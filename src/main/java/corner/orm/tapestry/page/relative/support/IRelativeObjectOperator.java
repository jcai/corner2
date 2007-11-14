// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-08
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

package corner.orm.tapestry.page.relative.support;

import org.apache.tapestry.IPage;

/**
 * 对关联对象的操作。
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public interface IRelativeObjectOperator<T, E> {

	/**
	 * 新增加一个关联对象的操作。
	 * 
	 * @param obj 供操作的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	public abstract IPage doNewRelativeAction(T obj, String pageName);

	/**
	 * 编辑一个关联对象的操作。
	 * <p>适用于one-to-many的操作。
	 * @param obj 供操作的对象。
	 * @param e 关联的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	public abstract IPage doEditRelativeEntityAction(T obj, E e, String pageName);

}