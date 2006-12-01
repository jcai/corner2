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

package corner.orm.tapestry.component.textfield;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

/**
 * 复写Tapestry的TextField,提供指定TextField默认值的能力
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class TextField extends org.apache.tapestry.form.TextField {

	/**
	 * @see org.apache.tapestry.form.TextField#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if(this.getDefaultValue()==null || this.getDefaultValue().trim().length()<1){
			super.renderFormComponent(writer, cycle);
		}
		else{
			
			String value = getTranslatedFieldSupport().format(this, getValue());
	        if(value != null && value.trim().length()>0){
	        	super.renderFormComponent(writer, cycle);
	        }
	        else{//TODO  为什么不能直接使用 setValue(getDefaultValue()) 这样岂不更简单？

		        renderDelegatePrefix(writer, cycle);

		        writer.beginEmpty("input");

		        writer.attribute("type", isHidden() ? "password" : "text");

		        writer.attribute("name", getName());

		        if (isDisabled()) writer.attribute("disabled", "disabled");

		        writer.attribute("value", this.getDefaultValue());

		        renderIdAttribute(writer, cycle);

		        renderDelegateAttributes(writer, cycle);

		        getTranslatedFieldSupport().renderContributions(this, writer, cycle);
		        getValidatableFieldSupport().renderContributions(this, writer, cycle);

		        renderInformalParameters(writer, cycle);

		        writer.closeTag();

		        renderDelegateSuffix(writer, cycle);
	        }
		}
	}

	/**
	 * 取得默认值
	 * @return
	 */
	public abstract String getDefaultValue();
}
