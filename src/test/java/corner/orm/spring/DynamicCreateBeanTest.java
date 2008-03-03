package corner.orm.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.AbstractModel;
import corner.demo.model.many2many.A;

/**
 * 测试动态创建spring的bean
 * 初步想实现:
 * http://www.jroller.com/kenwdelong/entry/horizontal_database_partitioning_with_spring
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class DynamicCreateBeanTest {
	@Test
	public void test_data(){
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext(new String[]{"classpath:/corner/orm/spring/app-test.xml"},true);
		BeanFactoryPostProcessor beanFactoryPostProcessor=new BeanFactoryPostProcessor(){

			public void postProcessBeanFactory(
					ConfigurableListableBeanFactory beanFactory)
					throws BeansException {
				System.out.println("hello world");
				System.out.println(beanFactory.getClass().getName());
				DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
				try {
					MutablePropertyValues pvs=new MutablePropertyValues();
					pvs.addPropertyValue("name","acai");
					AbstractBeanDefinition beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(A.class.getName(),null,null,pvs,getClass().getClassLoader());
					((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("bean2", beanDefinition);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}};
		ctx.addBeanFactoryPostProcessor(beanFactoryPostProcessor);
		ctx.refresh();
		ctx.getBean("bean1");
		Object obj=ctx.getBean("bean2");
		Assert.assertEquals("acai",((AbstractModel) obj).getName());
		
	}
}
