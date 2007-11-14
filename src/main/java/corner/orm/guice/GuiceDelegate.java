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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;

import corner.util.BeanUtils;

/**
 * 对Guice的代理类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class GuiceDelegate implements Injector {
	private List<ModuleContribution> moduleContributions;
	private Injector injector;
	public void setModuleContributions(List<ModuleContribution> moduleContributions) {
		this.moduleContributions = moduleContributions;
	}
	
	@SuppressWarnings("unchecked")
	public void initializeService()
    {
        Iterator<ModuleContribution> i = moduleContributions.iterator();
        List<Module> list = new ArrayList<Module>();
        while(i.hasNext())
        {
        	list.add(BeanUtils.instantiateClass(i.next().getModuleClass()));
        }
        injector=Guice.createInjector(list);
    }

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#findBindingsByType(com.google.inject.TypeLiteral)
	 */
	public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> arg0) {
		return injector.findBindingsByType(arg0);
	}

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#getBinding(com.google.inject.Key)
	 */
	public <T> Binding<T> getBinding(Key<T> arg0) {
		return injector.getBinding(arg0);
	}

	/**
	 * @return
	 * @see com.google.inject.Injector#getBindings()
	 */
	public Map<Key<?>, Binding<?>> getBindings() {
		return injector.getBindings();
	}

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#getInstance(java.lang.Class)
	 */
	public <T> T getInstance(Class<T> arg0) {
		return injector.getInstance(arg0);
	}

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#getInstance(com.google.inject.Key)
	 */
	public <T> T getInstance(Key<T> arg0) {
		return injector.getInstance(arg0);
	}

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#getProvider(java.lang.Class)
	 */
	public <T> Provider<T> getProvider(Class<T> arg0) {
		return injector.getProvider(arg0);
	}

	/**
	 * @param <T>
	 * @param arg0
	 * @return
	 * @see com.google.inject.Injector#getProvider(com.google.inject.Key)
	 */
	public <T> Provider<T> getProvider(Key<T> arg0) {
		return injector.getProvider(arg0);
	}

	/**
	 * @param arg0
	 * @see com.google.inject.Injector#injectMembers(java.lang.Object)
	 */
	public void injectMembers(Object arg0) {
		injector.injectMembers(arg0);
	}
	
	
}
