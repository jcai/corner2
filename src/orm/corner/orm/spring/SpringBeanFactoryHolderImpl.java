package corner.orm.spring;

import org.apache.hivemind.events.RegistryShutdownListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
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

	 public BeanFactory getBeanFactory() {
		    if (super.getBeanFactory() == null) {
                    super.setBeanFactory(SpringContainer.getInstance().getApplicationContext());
            }
            
            return super.getBeanFactory();
    }

    public void registryDidShutdown() {
           ((ConfigurableApplicationContext) super.getBeanFactory()).close();
    }

}
