/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: EnumSelectionModel.java 911 2008-06-14 06:45:46Z jcai $
 * created at:2008-06-14
 */

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