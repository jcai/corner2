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

import org.apache.tapestry.link.ILinkRenderer;

import corner.demo.model.one2many.A;
import corner.orm.tapestry.RawURLLinkRenderer;
import corner.orm.tapestry.component.gain.GainPoint;
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
		GainPoint gp  = (GainPoint) this.getPage().getComponent("GainPointField");
		
		for(Object e : gp.getSaveOrUpdateEntitys()){
			this.setEntity(e);
			super.saveOrUpdateEntity();
		}
		
		for(Object e : gp.getDeleteEntitys()){
			this.getEntityService().deleteEntities(e);
		}
	}
	
	/**
	 * @return
	 */
	public ILinkRenderer getRenderer(){
		return new RawURLLinkRenderer();
	}
	
//	public IPage doWinPopQueryPage(){
//		return this.getRequestCycle().getPage("widget/WinSelectionListPage");
//	}
	
	public List getSource(){
		return  ((A)this.getRootedObject()).getBs();
	}
}

