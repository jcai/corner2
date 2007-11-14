// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-06-13
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

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Component;

/**
 * 抽象的entity表单页。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.2
 * @param <T> 当前操作的实体对象.
 */
public abstract class AbstractEntityFormPage<T> extends AbstractEntityPage<T>{
	/**
	 * 得到回显的列表页面。
	 * @return 列表页。
	 * @since 2.0.1
	 */
	protected IPage getEntityListPage(){
		return this.getRequestCycle().getPage(
				this.getPageName().substring(0,
						this.getPageName().lastIndexOf("Form"))
						+ "List");
	}
	/**
	 * 保存实体操作.
	 *
	 *
	 * @return 保存后的返回页面.
	 * @since 2.0
	 */
	public IPage doSaveEntityAction() { // 保存操作。
		saveOrUpdateEntity();
		return getEntityListPage();
	}
	/**
	 * 取消对一个实体的编辑或者新增。
	 *
	 * @return 取消后返回的页面。
	 * @since 2.0
	 */
	public IPage doCancelEntityAction(){
		return this.getEntityListPage();
	}
	
	/**
	 * 使用注释，简化取消对一个实体的编辑或者新增
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doCancelEntityAction"})
	public abstract IComponent getCancelEntityLink();
}
