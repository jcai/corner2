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
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class PropertySelection extends
		org.apache.tapestry.form.PropertySelection {


	/**
	 * @see org.apache.tapestry.form.PropertySelection#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if(this.getIsSelect()){
	        renderDelegatePrefix(writer, cycle);
	        
	        writer.begin("select");
	        writer.attribute("name", getName());
	        
	        if (isDisabled())
	            writer.attribute("disabled", "disabled");
	        
	        if (getSubmitOnChange())
	            writer.attribute("onchange", "javascript: this.form.events.submit();");
	        
	        renderIdAttribute(writer, cycle);
	        
	        renderDelegateAttributes(writer, cycle);
	        
	        getValidatableFieldSupport().renderContributions(this, writer, cycle);
	        
	        // Apply informal attributes.
	        renderInformalParameters(writer, cycle);
	        
	        writer.println();
	        
	        IPropertySelectionModel model = getModel();
	        
	        if (model == null)
	            throw Tapestry.createRequiredParameterException(this, "model");
	        
	        int count = model.getOptionCount();
	        boolean foundSelected = false;
	        Object value = getValue();

	        //在全部option之前插入一个全选的option
            writer.begin("option");
            writer.attribute("value", "%");

            if (!foundSelected && isEqual("%", value))
            {
                writer.attribute("selected", "selected");

                foundSelected = true;
            }        

            writer.print(this.getOptionName());

            writer.end();

            writer.println();

            
	        for (int i = 0; i < count; i++)
	        {
	            Object option = model.getOption(i);

	            writer.begin("option");
	            writer.attribute("value", model.getValue(i));

	            if (!foundSelected && isEqual(option, value))
	            {
	                writer.attribute("selected", "selected");

	                foundSelected = true;
	            }

	            writer.print(model.getLabel(i));

	            writer.end();

	            writer.println();
	        }

	        
	        writer.end(); // <select>

	        renderDelegateSuffix(writer, cycle);
		}
		else{
			super.renderFormComponent(writer, cycle);
		}
	}


    /**
	 * @see org.apache.tapestry.form.PropertySelection#rewindFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		String value = cycle.getParameter(getName());
		
		if(this.getIsSelect()){
			Object object = null;
			if(value.equals("%")){
				object = "%";
			}
			else{
				object = getModel().translateValue(value);
			}
	        
	        try
	        {
	            getValidatableFieldSupport().validate(this, writer, cycle, object);
	            
	            setValue(object);
	        }
	        catch (ValidatorException e)
	        {
	            getForm().getDelegate().record(e);
	        }			
		}
		else{
			super.rewindFormComponent(writer, cycle);
		}
	}


	private boolean isEqual(Object left, Object right)
    {
        // Both null, or same object, then are equal

        if (left == right)
            return true;
        
        // If one is null, the other isn't, then not equal.
        
        if (left == null || right == null)
            return false;
        
        // Both non-null; use standard comparison.
        
        return left.equals(right);
    }	
	
	/**
	 * 判断是否为显示全部查询
	 */
	public abstract boolean getIsSelect();
	
	/**
	 * 取得自定义的那个option的名称
	 */
	public abstract String getOptionName();
}
