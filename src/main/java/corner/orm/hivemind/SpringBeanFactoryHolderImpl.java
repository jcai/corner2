// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
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

package corner.orm.hivemind;

import org.apache.hivemind.events.RegistryShutdownListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import corner.orm.spring.SpringContainer;
/**
 * 
 * 把Spring包装后提供给Hivemind的服务。
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-9-9
 */
public class SpringBeanFactoryHolderImpl extends
		org.apache.hivemind.lib.impl.SpringBeanFactoryHolderImpl implements
		RegistryShutdownListener {
	/**
	 * 得到Spring的BeanFactory
	 * @see org.apache.hivemind.lib.SpringBeanFactorySource#getBeanFactory()
	 */
	 public BeanFactory getBeanFactory() {
		    if (super.getBeanFactory() == null) {
                    super.setBeanFactory(SpringContainer.getInstance().getApplicationContext());
            }
            
            return super.getBeanFactory();
    }
	 /**
	  * 当关闭Hivemind容器时,关闭Spring容器.
	  * @see org.apache.hivemind.events.RegistryShutdownListener#registryDidShutdown()
	  */
    public void registryDidShutdown() {
           ((ConfigurableApplicationContext) super.getBeanFactory()).close();
    }

}
