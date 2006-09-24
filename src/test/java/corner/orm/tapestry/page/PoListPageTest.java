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

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.BaseEngine;
import org.apache.tapestry.listener.ListenerMethodInvoker;
import org.apache.tapestry.listener.ListenerMethodInvokerImpl;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.page.relative.support.RelativeObjectOperator;

/**
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.2.1
 */
public class PoListPageTest extends CornerPageTestCase {
	private A testData;
	
	@BeforeMethod(dependsOnMethods={"buildFrameRegistry"})
	public void testPrepareData(){
		testData=new A();
		entityService.saveEntity(testData);
	}
	
	@Test
	public void testDeleteEntityAction(){
		IRequestCycle cycle = newCycle();
        
        EasyMock.expect(cycle.getListenerParameters()).andReturn(new Object[]{}).anyTimes();
        PoListPage page = newInstance(PoListPage.class,new Object[]{"pageName","AForm","entityService",entityService});
        cycle.activate(page);
        EasyMock.expect(cycle.isRewinding()).andReturn(false).times(1);
        replay();
        
        page.attach(new BaseEngine(), cycle);
        
        
        ListenerMethodInvoker invoker = 
            new ListenerMethodInvokerImpl("doDeleteEntitiesAction", page.getClass().getMethods());
        
        
        page.setSelectedEntities(new ArrayList<Object>());
        
        page.setEntity(testData);
        page.setCheckboxSelected(true);
        
        invoker.invokeListenerMethod(page, cycle);
        
        assertEquals(0,page.getSource().getRowCount());
        verify();
        
	}
	@Test
	public void testDoQueryEntityAction(){
		IRequestCycle cycle = newCycle();
        
        EasyMock.expect(cycle.getListenerParameters()).andReturn(new Object[]{}).anyTimes();
        PoListPage page = newInstance(PoListPage.class,new Object[]{"pageName","AForm","entityService",entityService});
        cycle.activate(page);
        EasyMock.expect(cycle.isRewinding()).andReturn(false).times(2);
        replay();
        
        page.attach(new BaseEngine(), cycle);
        
        
        ListenerMethodInvoker invoker = 
            new ListenerMethodInvokerImpl("doQueryEntityAction", page.getClass().getMethods());
        
        A a=new A();
        page.setEntity(a);
        page.setQueryEntity(new A());
        invoker.invokeListenerMethod(page, cycle);
        
        assertEquals(1,page.getSource().getRowCount());
        assertEquals(testData.getId(),((A) page.getSource().getCurrentPageRows(0, 10, null, false).next()).getId());
        
        verify();
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testDoNewOrEditRelativeEntityAction(){
		IRequestCycle cycle = newCycle();
        
        EasyMock.expect(cycle.getListenerParameters()).andReturn(new Object[]{testData,null,"RelativePage"});
        
        RelativeObjectOperator operator=new RelativeObjectOperator();
        operator.setRequestCycle(cycle);
        PoListPage page = newInstance(PoListPage.class,new Object[]{"pageName","AForm","entityService",entityService,"relativeObjectOperator",operator});
        IPageRooted<A,A> relativePage=newMock(IPageRooted.class);
        EasyMock.expect(cycle.getPage("RelativePage")).andReturn(relativePage);
        relativePage.setRootedObject(testData);
        cycle.activate(relativePage);
        replay();
        
        page.attach(new BaseEngine(), cycle);
        
        
        ListenerMethodInvoker invoker = 
            new ListenerMethodInvokerImpl("doNewOrEditRelativeEntityAction", page.getClass().getMethods());
        
        A a=new A();
        page.setEntity(a);
        page.setQueryEntity(new A());
        invoker.invokeListenerMethod(page, cycle);
        
        
        verify();
	}
}
