// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-26
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

package corner.orm.tapestry.service.blob;

import org.apache.hivemind.impl.BaseLocatable;

/**
 * 根据class的名称来提供blob服务.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class NameBlobProviderContribution extends BaseLocatable {
	private String name;
	private String providerClassName;
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the providerClassName.
	 */
	public String getProviderClassName() {
		return providerClassName;
	}
	/**
	 * @param providerClassName The providerClassName to set.
	 */
	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}
	
}
