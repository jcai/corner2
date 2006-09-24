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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableModel;
import org.apache.tapestry.engine.BaseEngine;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;
import corner.demo.model.one2many.B;
import corner.orm.tapestry.page.CornerPageTestCase;

/**
 * @author jcai
 * @version $Revision$
 * @since 2.2.1
 */
public class RelativeEntityListPage extends CornerPageTestCase {
	@Test
	public void testRelativeEntityList(){
		A a=new A();
		entityService.saveEntity(a);
		
		IRequestCycle cycle=newCycle();
		AbstractRelativeEntityListPage<A,B> entityListPage=(AbstractRelativeEntityListPage<A, B>) newInstance(AbstractRelativeEntityListPage.class,"entityService",entityService);
		entityListPage.setRootedObject(a);
		EasyMock.expect(cycle.isRewinding()).andReturn(false);
		
		replay();
		entityListPage.attach(new BaseEngine(),cycle);
		IBasicTableModel source=entityListPage.getSource("bs");
		
		assertEquals(0,source.getRowCount());
		verify();
	}
}
