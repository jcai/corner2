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
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.json.JSONObject;

import corner.demo.model.one.A;
import corner.orm.tapestry.component.response.IAjaxResponsePage;
import corner.orm.tapestry.page.PoFormPage;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class SelectBoxPage extends PoFormPage implements IAjaxResponsePage{
	
	/**
	 * @see corner.orm.tapestry.component.response.IAjaxResponsePage#getJsonData(org.apache.tapestry.IRequestCycle)
	 */
	public String getJsonData(IRequestCycle cycle) {
		String value = cycle.getParameter("field");
		if(value == null) return null;
		
		JSONObject json = new JSONObject();
		json.put("000000", value);
		json.put("111111", "a1");
		json.put("555555", "a5");
		json.put("666666", "a6");
		json.put("777777", "a7");
		return json.toString();
	}

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
		this.getToSource();
	}
	
	@InitialValue("new java.util.ArrayList()")
	public abstract List<A> getToSource();
	
	/**
	 * @return
	 */
	public List<A> getFromSource(){
		return this.getEntityService().findAll(A.class);
	}
	
	/**
	 * @return
	 */
	public List<A> getToList(){
		List<A> list = new ArrayList<A>();
		A a = new A();
		a.setName("a1");
		a.setId("111111");
		list.add(a);
		
		a = new A();
		a.setName("a2");
		a.setId("222222");
		list.add(a);
		
		return list;
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
