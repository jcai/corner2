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

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 给PropertySelection增加全选的功能
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class PropertySelection extends
		org.apache.tapestry.form.PropertySelection {

	/**
	 * 查询全部内容时的符号
	 */
	private static final String COMMON_SEARCH_SYMBOL = "%";

	/**
	 * 增加的全选那条option的标签名称。例如"全选" or "select all"
	 */
	public static final String USER_DEFINED_OPTION_NAME = "optionName";

	/**
	 * @see org.apache.tapestry.form.PropertySelection#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		cycle.setAttribute(USER_DEFINED_OPTION_NAME, this.getOptionName());
		super.renderFormComponent(writer, cycle);
		cycle.removeAttribute(USER_DEFINED_OPTION_NAME);
	}

	/**
	 * @see org.apache.tapestry.form.PropertySelection#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String value = cycle.getParameter(getName());
		if (COMMON_SEARCH_SYMBOL.equals(value)) {
			Object object = COMMON_SEARCH_SYMBOL;
			try {
				getValidatableFieldSupport().validate(this, writer, cycle,object);

				setValue(object);
			} catch (ValidatorException e) {
				getForm().getDelegate().record(e);
			}
		} else {
			super.rewindFormComponent(writer, cycle);
		}
	}

	/**
	 * 取得自定义的那个option的名称
	 */
	@InitialValue("literal:全选")
	public abstract String getOptionName();
}
