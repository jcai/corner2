// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-16
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

package corner.orm.tapestry.page.generic;

import org.apache.tapestry.pageload.ComponentClassProvider;
import org.apache.tapestry.pageload.ComponentClassProviderContext;


/**
 * 定义对Form和List页面自定义查找对应的页面类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class GenericPageClassProvider implements ComponentClassProvider{

	//是否需要enable泛型页面类.
	private boolean enableGenericPage;
	/**
	 * @return Returns the enableGenericPage.
	 */
	public boolean isEnableGenericPage() {
		return enableGenericPage;
	}
	/**
	 * @param enableGenericPage The enableGenericPage to set.
	 */
	public void setEnableGenericPage(boolean enableGenericPage) {
		this.enableGenericPage = enableGenericPage;
	}
	/**
	 * 
	 * @see org.apache.tapestry.pageload.ComponentClassProvider#provideComponentClassName(org.apache.tapestry.pageload.ComponentClassProviderContext)
	 */
	public String provideComponentClassName(ComponentClassProviderContext context) {
		if(enableGenericPage){
			String name=context.getName();
			if(name.endsWith("Form")){
				return GenericFormPage.class.getName();
			}else if(name.endsWith("List")){
				return GenericListPage.class.getName();
			}
		}
		return null;
	}

	
}
