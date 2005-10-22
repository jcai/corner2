package corner.orm.hivemind;

import org.apache.hivemind.events.RegistryShutdownListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import corner.orm.spring.SpringContainer;
/**
 * 
 * ��Spring��װ���ṩ��Hivemind�ķ���
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-9
 */
public class SpringBeanFactoryHolderImpl extends
		org.apache.hivemind.lib.impl.SpringBeanFactoryHolderImpl implements
		RegistryShutdownListener {
	/**
	 * �õ�Spring��BeanFactory
	 * @see org.apache.hivemind.lib.SpringBeanFactorySource#getBeanFactory()
	 */
	 public BeanFactory getBeanFactory() {
		    if (super.getBeanFactory() == null) {
                    super.setBeanFactory(SpringContainer.getInstance().getApplicationContext());
            }
            
            return super.getBeanFactory();
    }
	 /**
	  * ���ر�Hivemind����ʱ,�ر�Spring����.
	  * @see org.apache.hivemind.events.RegistryShutdownListener#registryDidShutdown()
	  */
    public void registryDidShutdown() {
           ((ConfigurableApplicationContext) super.getBeanFactory()).close();
    }

}