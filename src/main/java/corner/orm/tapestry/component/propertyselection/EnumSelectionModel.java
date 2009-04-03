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

package corner.orm.tapestry.component.propertyselection;

import org.apache.tapestry.form.IPropertySelectionModel;

/**
 * 一个对枚举类型进行选择的model
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5.1
 */
public class EnumSelectionModel<T extends Enum> implements
		IPropertySelectionModel {

	private T[] list;

	public EnumSelectionModel(T[] list) {
		this.list = list;
	}

	public String getLabel(int index) {

		return list[index].toString();
	}

	public Object getOption(int index) {
		return list[index].toString();
	}

	public int getOptionCount() {
		return list.length;
	}

	public String getValue(int index) {
		return list[index].toString();
	}

	public Object translateValue(String value) {
		return value;
	}

	public boolean isDisabled(int arg0) {
		return false;
	}
}