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
import org.apache.tapestry.IRequestCycle;

import corner.orm.tapestry.page.relative.IPageRooted;

/**
 * 封装了一些对关联对象的操作代理，以便复用.
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public class RelativeObjectOperator implements IRelativeObjectOperator<Object, Object>  {
	private IRequestCycle cycle;
	
	/**
	 * @see corner.orm.tapestry.page.relative.support.IRelativeObjectOperator#doNewRelativeAction(Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IPage doNewRelativeAction(Object obj,String pageName){
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.cycle.getPage(pageName);
		page.setRootedObject(obj);
		return page;
	}
	/**
	 * @see corner.orm.tapestry.page.relative.support.IRelativeObjectOperator#doObjectditRelativeObjectntityAction(Object, Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IPage doEditRelativeEntityAction(Object obj,Object e,String pageName){
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.cycle.getPage(pageName);
		page.setRootedObject(obj);
		page.setEntity(e);
		
		return page;
	}
	public void setRequestCycle(IRequestCycle cycle){
		this.cycle=cycle;
	}
}
