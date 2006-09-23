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

package corner.orm.tapestry.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IEngine;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.BaseEngine;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.PageService;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.listener.ListenerMethodInvoker;
import org.apache.tapestry.listener.ListenerMethodInvokerImpl;
import org.apache.tapestry.test.Creator;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.service.EntityService;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public class AbstractEntityFormPageTest extends BaseComponentTestCase {
	  private Map newMap(Object key, Object value)
	    {
	        Map result = new HashMap();

	        result.put(key, value);

	        return result;
	    }
	@SuppressWarnings("unchecked")
	@Test
	public void testEntityPage() throws Exception{
/*		final Registry reg = buildFrameworkRegistry(new String[]{});
        
        
        
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
        IRequestCycle request=(IRequestCycle) reg.getService("tapestry.globals.IRequestCycle", IRequestCycle.class);
       
        assertNotNull(request);
        IRequestCycle cycle = newCycle();
        
        
        AbstractEntityFormPage page=(AbstractEntityFormPage) newInstance(AbstractEntityFormPage.class,new Object[]{"entityService",entityService,"pageName","AForm"});
        page.finishLoad(cycle,null,null);
        
        
//        BeanUtils.setProperty(page, "_requestCycle",cycle);
        
        expect(page.getRequestCycle()).andReturn(cycle);
        replay();
        
        
        
        page.setEntity(new A());
        page.doSaveEntityAction();
        
        verify();*/
		final Registry reg = buildFrameworkRegistry(new String[]{});
        
        
        
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
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
        
	}
	private IPage createPage()
    {
        Creator creator = new Creator();

        return (IPage) creator.newInstance(BasePage.class);
    }
	

}
