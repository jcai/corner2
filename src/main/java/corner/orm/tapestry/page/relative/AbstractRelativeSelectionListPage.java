// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-06-21
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

import corner.orm.tapestry.page.AbstractEntityListPage;
import corner.orm.tapestry.page.EntityPage;

/**
 *
 * 抽象的关联关系列表选择页面。
 * <p>通常是提供了选择关联对象的列表页面。
 * @author jcai
 * @version $Revision$
 * @since 2.0.3
 * @param <T> 当前操作的实体。
 * @param <E> 关联的实体。
 */
public abstract class AbstractRelativeSelectionListPage<T,E> extends AbstractEntityListPage<E> implements IPageRooted<T,E>{

	/**
	 * 新增加关系的操作。
	 * @return 增加关系后的页面。
	 */
	public IPage doNewRelativeAction(){
		this.flushHibernate();

		return this.goReturnPage();
	}
	/**
	 * 返回到rootformPage {@link #getRootFormPage()}
	 * 
	 * @return
	 */
	public IPage goReturnPage(){
		EntityPage<T> page=this.getRootFormPage();
		if(page == null){
			throw new IllegalStateException("RootFormPage 为空！");
		}
		if(page instanceof IPageRooted){
			((IPageRooted<T,E>) page).setRootedObject(this.getRootedObject());
		}else{
			page.setEntity(this.getRootedObject());
		}
		return page;
	}
}
