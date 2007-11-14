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
 * 
 * 对blob的提供者的配置说明.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class BlobProviderContribution extends BaseLocatable {
	private String name;
	private IBlobProvider provider;
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
	 * @return Returns the provider.
	 */
	public IBlobProvider getProvider() {
		return provider;
	}
	/**
	 * @param provider The provider to set.
	 */
	public void setProvider(IBlobProvider provider) {
		this.provider = provider;
	}
}
