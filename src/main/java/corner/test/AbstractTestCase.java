package corner.test;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 基于实际应用的基础测试类.
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 */
public class AbstractTestCase extends Assert {
	protected SpringContainer container = SpringContainer.getInstance();
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	HibernateTransactionManager manager;
	TransactionStatus t;
	
	
	protected void startTransaction(){
		manager=(HibernateTransactionManager) container.getApplicationContext().getBean("transactionManager");
        t=manager.getTransaction(transactionDefinition);
        
	}
	protected void commitTransaction(){
		manager.commit(t);
	}
	
	
	protected EntityService getEntityService() {
		return (EntityService) container.getApplicationContext().getBean(
				"entityService");
	}
	
	protected Session getCurrentSession(){
		
		return manager.getSessionFactory().getCurrentSession();
	}
}
