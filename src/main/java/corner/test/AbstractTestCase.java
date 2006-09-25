package corner.test;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.Assert;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 基于实际应用的基础测试类.
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 */
public class AbstractTestCase extends Assert {
	//spring container
	protected SpringContainer container = SpringContainer.getInstance();
	//默认的事物定义
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	//事物管理的manager
	HibernateTransactionManager manager;
	TransactionStatus t;
	
	/**
	 * 启动一个事物
	 *
	 */
	protected void startTransaction(){
		manager=(HibernateTransactionManager) container.getApplicationContext().getBean("transactionManager");
        t=manager.getTransaction(transactionDefinition);
        
	}
	/**
	 * 提交事物
	 *
	 */
	protected void commitTransaction(){
		manager.commit(t);
	}
	
	/**
	 * 得到基础的EntityService.
	 * @return 实体service
	 */
	protected EntityService getEntityService() {
		return (EntityService) container.getApplicationContext().getBean(
				"entityService");
	}
	/**
	 * 得到当前事物邦定的Session.
	 * @return 当前的Session.
	 */
	protected Session getCurrentSession(){
		return manager.getSessionFactory().getCurrentSession();
	}
}
