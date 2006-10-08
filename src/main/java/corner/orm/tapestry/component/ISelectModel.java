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

package corner.orm.tapestry.component;

import org.apache.tapestry.dojo.form.IAutocompleteModel;

/**
 * 提供具有中文名称检索的接口
 * <p>提供中文检索功能的CornerSelect和TextAraBox的model如果想支持中文检索，应该实现此接口</p>
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public interface ISelectModel extends IAutocompleteModel {

	/**
	 * 取得中文名称
	 * <p>次方法用于返回中文名称</p>
	 * @param value
	 * @return
	 */
	String getCnLabelFor(Object value);
	
	public void setFilter(ISelectFilter filter);
}
