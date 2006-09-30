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

import java.text.Format;
import java.util.Date;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.BaseEngine;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.page.relative.ReflectRelativeEntityListPage;

/**
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.2.1
 */
public class EntityPageTest extends BaseComponentTestCase {
	@Test
	public void testDateFormat(){
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class);
		Format format=page.getDateFormat();
		
		String result=format.format(new Date(0));
		assertEquals("1970-01-01",result);
		
		Format format1=page.getDateFormat();
		assertEquals(format1,format);
		
		Format customFormat=page.getDateFormat("yyyyMMdd");
		result=customFormat.format(new Date(0));
		assertEquals("19700101",result);
		
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGoEntityPage(){
		A a=new A();
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class);
		EntityPage<A> entityPage=(EntityPage<A>) newInstance(AbstractEntityPage.class);
		IRequestCycle cycle=newCycle();
		EasyMock.expect(cycle.getPage("AList")).andReturn(entityPage);
		
		replay();
		page.attach(new BaseEngine(), cycle);
		page.goEntityPage(a, "AList");
		assertEquals(a,entityPage.getEntity());
		verify();
		
		
		replay();
		page.goEntityPageByPage(a,entityPage);
		page.attach(new BaseEngine(), cycle);
		assertEquals(a,entityPage.getEntity());
		verify();
	}
	@SuppressWarnings("deprecation")
	@Test
	public void testGetCurrentPagePath(){
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,"pageName","test/test/AForm");
		assertEquals("test/test/",page.getCurrentPagePath());
	}
	@Test
	public void testDoViewRelativeEntityListAction(){
		A a=new A();
		IRequestCycle cycle=newCycle();
		IPageRooted relativeListPage=(IPageRooted) newInstance(ReflectRelativeEntityListPage.class);
		EasyMock.expect(cycle.getPage("BList")).andReturn(relativeListPage);
		replay();
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class);
		page.attach(new BaseEngine(), cycle);
		relativeListPage=(IPageRooted) page.doViewRelativeEntityListAction(a, "BList");
		assertEquals(a,relativeListPage.getRootedObject());
		verify();
		
	}
}
