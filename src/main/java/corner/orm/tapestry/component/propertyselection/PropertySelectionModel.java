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

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.tapestry.form.IPropertySelectionModel;

/**
 * 进行选择的model，只要传进来数组就可以了
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PropertySelectionModel<T> implements IPropertySelectionModel {
	/**
	 * 默认构造方法
	 * 
	 */
	public PropertySelectionModel() {

	}

	/**
	 * 直接传入List的构造方法
	 * 
	 * @param list
	 */
	public PropertySelectionModel(List<T> list) {

	}

	private T[] list;

	public PropertySelectionModel(T[] list) {
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
