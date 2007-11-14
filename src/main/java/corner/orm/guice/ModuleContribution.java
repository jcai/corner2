// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-20
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

package corner.orm.guice;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.impl.BaseLocatable;

import com.google.inject.Module;

/**
 * 实现其他的module
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class ModuleContribution extends BaseLocatable{
	private Class<Module> moduleClass;

	/**
	 * @return Returns the moduleClass.
	 */
	public Class<Module> getModuleClass() {
		return moduleClass;
	}

	/**
	 * @param moduleClass The moduleClass to set.
	 */
	@SuppressWarnings("unchecked")
	public void setModuleClassName(String moduleClassName) {
		try {
			this.moduleClass = (Class<Module>) (Class.forName(moduleClassName));
		} catch (ClassNotFoundException e) {
			throw new ApplicationRuntimeException(e);
		}
	}
}
