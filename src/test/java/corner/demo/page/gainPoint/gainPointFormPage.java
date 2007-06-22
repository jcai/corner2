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

import java.util.List;

import corner.demo.model.one2many.A;
import corner.orm.tapestry.component.gain.GainFence;
import corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class gainPointFormPage extends ReflectRelativeEntityFormPage {

	/**
	 * @see corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		GainFence gf  = (GainFence) this.getPage().getComponent("GainFenceField");
		
		for(Object e : gf.getEntitys()){
			this.setEntity(e);
			super.saveOrUpdateEntity();
		}
	}
	
	public List getSource(){
//		((A)this.getRootedObject()).getBs();
//		
//		HashSet set = new HashSet();
		
		return  ((A)this.getRootedObject()).getBs();
	}
}

