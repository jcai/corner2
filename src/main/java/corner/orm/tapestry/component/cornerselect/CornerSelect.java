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

package corner.orm.tapestry.component.cornerselect;

import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.dojo.form.IAutocompleteModel;

import corner.demo.page.many2many2.CornerSelectModel;
import corner.service.EntityService;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class CornerSelect extends Autocompleter {
	public IAutocompleteModel getModel(){
		try {
			return new CornerSelectModel(this.getEntityService(),Class.forName(this.getQueryClass()),this.getLabel(),this.getCnlabel());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 设置下拉层列表中要显示的实体
	 * @param queryClass
	 */
	 public abstract void setQueryClass(String queryClass);
	 /**
	  * 取得拉层列表中要显示的实体
	  * @return
	  */
	 public abstract String getQueryClass();
	 /**
	  * 提供对实体的增删改查操作的能力
	  * @return
	  */
	 public abstract EntityService getEntityService();
	 /**
	  * 取得供拼音检索的字段名称
	  * @return
	  */
	 public abstract String getLabel();
	 /**
	  * 设置拼音检索的字段名称
	  * @param label
	  */
	 public abstract void  setLabel(String label);
	 
	 /**
	  * 设置中文检索的字段名称
	  * @param cnlabel
	  */
	 public abstract void setCnlabel(String cnlabel);
	 /**
	  * 取得中文检索的字段名称
	  * @return
	  */
	 public abstract String getCnlabel();
}
