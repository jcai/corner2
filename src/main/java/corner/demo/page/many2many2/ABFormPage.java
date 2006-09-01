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

package corner.demo.page.many2many2;

import org.apache.tapestry.dojo.form.IAutocompleteModel;

import corner.orm.tapestry.page.relative.ReflectRelativeMidEntityFormPage;
import corner.util.BeanUtils;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class ABFormPage extends ReflectRelativeMidEntityFormPage {
	/**
	 * 取得TextAreaBox的返回value
	 * @return
	 */
	public abstract Object getValue();	
	/**
	 * 给TextAreaBox提供DataSource
	 * <p>给TextAreaBox提供DataSource,该DataSource必须实现了IAutocompleteModel接口</p>
	 * @return
	 */
	public IAutocompleteModel getModel(){
		return new DoubleSearchAutocompleteModel(this.getEntityService(),this,"id","name","cnName");
	}
	/**
	 * @see corner.orm.tapestry.page.relative.AbstractReflectRelativeMidEntityFormPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		BeanUtils.setProperty(this.getEntity(),"message",this.getValue().toString());
		super.saveOrUpdateEntity();
	}
	
}
