//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.data;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.services.DataSqueezer;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.service.EntityService;


public class HibernateDataSqueezerTest extends BaseComponentTestCase{
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	@Test
	public void testSqueeze() 
    throws Exception 
    {
        final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = ( DataSqueezer )reg.getService( DataSqueezer.class );
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
        PlatformTransactionManager manager=(PlatformTransactionManager) spring.getBeanFactory().getBean("transactionManager");
        TransactionStatus t=manager.getTransaction(transactionDefinition);
        
        Session session=((HibernateDaoSupport) entityService.getObjectRelativeUtils()).getHibernateTemplate().getSessionFactory().getCurrentSession();

        A entity=new A();
        session.save(entity);
        
        final String squeezedValue = squeezer.squeeze(entity);
        System.out.println(squeezedValue);
        assertTrue( squeezedValue.startsWith( "HB:"+A.class.getName() ) );
        
        manager.commit(t);
        reg.cleanupThread();
        reg.shutdown();
    }
	@Test
	public void testUnsqueeze() 
    throws Exception 
    {
        final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = ( DataSqueezer )reg.getService( DataSqueezer.class );
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
        PlatformTransactionManager manager=(PlatformTransactionManager) spring.getBeanFactory().getBean("transactionManager");
        TransactionStatus t=manager.getTransaction(transactionDefinition);
        
        Session session=((HibernateDaoSupport) entityService.getObjectRelativeUtils()).getHibernateTemplate().getSessionFactory().getCurrentSession();

        A entity=new A();
        session.save(entity);
        
        final String squeezedValue = squeezer.squeeze(entity);
        
        
        assertTrue( squeezedValue.startsWith( "HB:"+A.class.getName() ) );
        
        assertEquals(entity,squeezer.unsqueeze(squeezedValue));
        manager.commit(t);
        reg.shutdown();
    }
}
