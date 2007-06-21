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

import java.util.HashSet;

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
	
	/**
	 * 当前也所有的set
	 */
	public abstract HashSet getEntitys();
	public abstract void setEntitys(HashSet sets);
}

