// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-09
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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Persist;

import corner.orm.tapestry.page.EntityPage;

/**
 * 有关联关系的页面，通常为了记录根对象。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 * @param T 基础的实体对象。
 * @param E 基础对象所关联的对象。
 */
public interface IPageRooted<T,E> extends IPage{
	/**
	 * 得到基础的实体对象。
	 * @return 基础的实体对象。
	 */
	@Persist("entity")
	public abstract T getRootedObject();
	/**
	 * 设定基础的对象。
	 * @param obj 关联的根对象。
	 */
	public abstract void setRootedObject(T obj);
	
	/**
	 * 得到返回的根对象表单的页面。
	 * @return 根对象的表单页面。
	 * 
	 */
	public abstract EntityPage<T> getRootFormPage();
	
	/**
	 * 设定关联对象.
	 * @param e 关联对象。
	 */
	public abstract void setEntity(E e);
	
	/**
	 * 取得关联的对象
	 * @return E 关联对象
	 * @since 2.3.7
	 */
	public abstract E getEntity();
	
}
