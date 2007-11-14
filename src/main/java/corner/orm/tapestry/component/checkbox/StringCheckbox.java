// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-08
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
 * 而如果用string类型，他会存true或者false
 * 
 * 而本Checkbox提供了一个选中的时候库中存 1,否则存 0
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
    	if(getValue()==null) return false;
    	return SELECTED_STR.equalsIgnoreCase(getValue().trim());
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
