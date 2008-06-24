//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.selectBox;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.IPage;

import corner.demo.model.one.A;
import corner.orm.tapestry.page.PoFormPage;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class SelectBoxPage extends PoFormPage{
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityFormPage#getEntityListPage()
	 */
	protected IPage getEntityListPage(){
		return this;
	}
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
//		super.saveOrUpdateEntity();
	}

	/**
	 * @return
	 */
	public List<A> getFromList(){
		List<A> list = new ArrayList<A>();
		A a = new A();
		a.setName("a1");
		a.setId("111111");
		list.add(a);
		
		a = new A();
		a.setName("a2");
		a.setId("222222");
		list.add(a);
		
		a = new A();
		a.setName("a3");
		a.setId("333333");
		list.add(a);
		
		a = new A();
		a.setName("a4");
		a.setId("444444");
		list.add(a);
		
		return list;
	}
}
