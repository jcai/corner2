//==============================================================================
// file :       $Id: AbstractEntityFormPageTest.java 1872 2006-09-23 15:28:59Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-09-23 15:28:59Z $
//              by:         $Author: jcai $
//              revision:   $Revision: 1872 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.BaseEngine;
import org.apache.tapestry.listener.ListenerMethodInvoker;
import org.apache.tapestry.listener.ListenerMethodInvokerImpl;
import org.easymock.EasyMock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.service.EntityService;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 1872 $
 * @since 2.2.1
 */
public class PoFormPageTest extends BaseComponentTestCase {
	Registry reg;
	EntityService entityService;
	@BeforeMethod
	public void buildFrameRegistry() throws Exception{
		reg = buildFrameworkRegistry(new String[]{});
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
    	
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testEntityPage() throws Exception{
	    IPage listPage=newMock(IPage.class);
        IRequestCycle cycle = newCycle();
        
        EasyMock.expect(cycle.getListenerParameters()).andReturn(new Object[]{}).anyTimes();
        EasyMock.expect(cycle.getPage("AList")).andReturn(listPage).anyTimes();
        cycle.activate(listPage);
		
        
        replay();
        
        AbstractEntityFormPage page = newInstance(AbstractEntityFormPage.class,new Object[]{"pageName","AForm","entityService",entityService});
        page.attach(new BaseEngine(), cycle);
        
        
        ListenerMethodInvoker invoker = 
            new ListenerMethodInvokerImpl("doSaveEntityAction", page.getClass().getMethods());
        
        
        
        A a=new A();
        page.setEntity(a);
        
         invoker.invokeListenerMethod(page, cycle);
        
        verify();
        assertTrue(entityService.findAll(A.class).size()==1);
        entityService.deleteEntities(a);
	}
	@AfterMethod
	public void closeRegistry(){
		reg.shutdown();
	}
	

}