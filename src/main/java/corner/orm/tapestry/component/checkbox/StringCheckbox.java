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

package corner.orm.tapestry.component.checkbox;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.AbstractFormComponent;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 提供对String类型的checkbox的支持.
 * <p>tapestry内置的checkbox只能保存boolean类型，而boolean类型在查询的时候不好控制.
 * 当选中的时候，数据库中保存 1,否则保存 0
 * 
 * 所以采用了String类型来处理.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class StringCheckbox extends AbstractFormComponent implements ValidatableField{
	public static final String SELECTED_STR="1";
	public static final String UNSELECTED_STR = "0";
	
	/**
     * @see org.apache.tapestry.form.validator.AbstractRequirableField#renderRequirableFormComponent(org.apache.tapestry.IMarkupWriter,
     *      org.apache.tapestry.IRequestCycle)
     */
    protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        renderDelegatePrefix(writer, cycle);
        
        writer.beginEmpty("input");
        writer.attribute("type", "checkbox");

        writer.attribute("name", getName());

        if (isDisabled())
            writer.attribute("disabled", "disabled");

        if (isSelected())
            writer.attribute("checked", "checked");

        renderIdAttribute(writer, cycle);

        getValidatableFieldSupport().renderContributions(this, writer, cycle);
        
        renderInformalParameters(writer, cycle);

        writer.closeTag();
        
        renderDelegateSuffix(writer, cycle);
    }

    /**
     * In traditional HTML, many checkboxes would have the same name but different values. Under
     * Tapestry, it makes more sense to have different names and a fixed value. For a checkbox, we
     * only care about whether the name appears as a request parameter.
     */
    protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        String value = cycle.getParameter(getName());
        
        try
        {
            // This is atypical validation - since this component does not explicitly bind to an object
            getValidatableFieldSupport().validate(this, writer, cycle, value);

            if(value!=null){
            	this.setValue(SELECTED_STR);
            }else{
            	this.setValue(UNSELECTED_STR);
            }
        }
        catch (ValidatorException e)
        {
            getForm().getDelegate().record(e);
        }
    }

    public abstract String getValue();

    public abstract void setValue(String selected);

    /**
     * 判断是否选中
     * @return
     */
    private boolean isSelected(){
    	return SELECTED_STR.equalsIgnoreCase(getValue());
    }
    /**
     * Injected.
     */
    public abstract ValidatableFieldSupport getValidatableFieldSupport();

    /**
     * @see org.apache.tapestry.form.AbstractFormComponent#isRequired()
     */
    public boolean isRequired()
    {
        return getValidatableFieldSupport().isRequired(this);
    }
}
