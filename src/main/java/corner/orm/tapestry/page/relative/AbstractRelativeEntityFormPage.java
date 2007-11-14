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

import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.page.EntityPage;

/**
 * 增加关联关系对象时候用到的表单页。
 * 适用于one-to-many时候，通过one来增加many端对象的操作。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class AbstractRelativeEntityFormPage<T, E> extends AbstractEntityFormPage<E> implements IPageRooted<T,E>{

	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityFormPage#getEntityListPage()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IPage getEntityListPage() {
		EntityPage<T> page= (EntityPage<T>) getRootFormPage();
		if(page instanceof IPageRooted){
			((IPageRooted<T,E>) page).setRootedObject(this.getRootedObject());
		}else{
			page.setEntity(this.getRootedObject());
		}
		return page;
	}
	
	
}
