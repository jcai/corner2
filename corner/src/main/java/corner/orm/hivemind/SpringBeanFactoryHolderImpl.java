package corner.orm.hivemind;

import org.apache.hivemind.events.RegistryShutdownListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import corner.orm.spring.SpringContainer;
/**
 * 
 * 把Spring包装后提供给Hivemind的服务。
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
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
