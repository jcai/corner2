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

package corner.demo.page.gainPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.form.IFormComponent;

import corner.demo.model.one2many.B;
import corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class gainPointFormPage extends ReflectRelativeEntityFormPage {

	/**
	 * 装配页面中的entity到entitys中
	 */
	private void assembleEntitys() {
		
		if(this.getEntitys() == null){
			this.setEntitys(new HashSet());
		}
		
		Set co = this.getComponents().entrySet();
		Iterator list = co.iterator();
		IComponent c = null;
		String entityComponentId = null;
		
		List entityPropertys = new ArrayList();
		entityPropertys.add("name");
		
		
		
		String entityProperty = (String) entityPropertys.iterator().next();
		entityComponentId = entityProperty + "Field";
		
		Class clazz = this.getEntity().getClass();
		
		
		
		while(list.hasNext()){
			Entry entity  = (Entry) list.next();
			c = (IComponent) entity.getValue();
			
			
			if(c instanceof IFormComponent && c.getId().indexOf(entityComponentId) != -1){
				
				
				org.apache.tapestry.form.TextField fc = (org.apache.tapestry.form.TextField)c;
				
				B b = null;
				
				try {
					 b = (B) clazz.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				b.setName((String) fc.getValue());
				
				this.getEntitys().add(b);
			}
		}
	}
	

	/**
	 * @see corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		assembleEntitys();
		Iterator list = this.getEntitys().iterator();
		
		while(list.hasNext()){
			this.setEntity(list.next());
			super.saveOrUpdateEntity();
		}
	}
	
	/**
	 * 当前也所有的set
	 */
	public abstract HashSet getEntitys();
	public abstract void setEntitys(HashSet sets);
}

