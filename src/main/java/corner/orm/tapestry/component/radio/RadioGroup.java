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

package corner.orm.tapestry.component.radio;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.form.Radio;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 扩展了Tapestry中的RadioGroup,实现了指定默认选中某个Radio的功能
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class RadioGroup extends org.apache.tapestry.form.RadioGroup {
	
    /**
     * A <code>RadioGroup</code> places itself into the {@link IRequestCycle}as an attribute, so
     * that its wrapped {@link Radio}components can identify thier state,and this RadioGroup has 
     * a defaultValue to point at which Radio can be select as default.
     */
    
    // Cached copy of the value from the selectedBinding
    private Object _selection;

    // The value from the HTTP request indicating which
    // Radio was selected by the user.
    private int _selectedOption;

    private boolean _rewinding;

    private boolean _rendering;

    private int _nextOptionId;


    public int getNextOptionId()
    {
        if (!_rendering)
            throw Tapestry.createRenderOnlyPropertyException(this, "nextOptionId");

        return _nextOptionId++;
    }

    public boolean isRewinding()
    {
        if (!_rendering)
            throw Tapestry.createRenderOnlyPropertyException(this, "rewinding");

        return _rewinding;
    }

    
    /**
     * 判断该radio是否被选中
     * 
     * Returns true if the value is equal to the current selection for the group. This is invoked by
     * a {@link Radio}during rendering to determine if it should be marked 'checked'.
     */

    public boolean isSelection(Object value) 
    {
        if (!_rendering)
            throw Tapestry.createRenderOnlyPropertyException(this, "selection");

        if (_selection == value)
            return true;

        //没有提供defaultValue,并且是新增加一个页面，此时将不会有默认选中的radio
        if (_selection == null && value == null)
            return false;
        //提供了defaultValue,并且是新增加一个页面，此时将会默认选中指定value的那个radio
        if(_selection == null && value != null){
        	this._selection = this.getDefaultValue();
        }

        //转化成String之后进行比较，此时不是比较内存地址，而是比较内容是否相同
        return _selection.toString().equals(value.toString());
    }
    /**
     * Invoked by the {@link Radio}which is selected to update the property bound to the selected
     * parameter.
     */

    public void updateSelection(Object value)
    {
    	super.updateSelection(value);
        _selection = value;
    }

    /**
     * Used by {@link Radio}components when rewinding to see if their value was submitted.
     */

    public boolean isSelected(int option)
    {
        return _selectedOption == option;
    }

    /**
     * @see org.apache.tapestry.AbstractComponent#prepareForRender(org.apache.tapestry.IRequestCycle)
     */
    protected void prepareForRender(IRequestCycle cycle)
    {
    	super.prepareForRender(cycle);

        _rendering = true;
        _nextOptionId = 0;
    }

    /**
     * @see org.apache.tapestry.AbstractComponent#cleanupAfterRender(org.apache.tapestry.IRequestCycle)
     */
    protected void cleanupAfterRender(IRequestCycle cycle)
    {
        _rendering = false;
        _selection = null;

        super.cleanupAfterRender(cycle);
    }

    /**
     * @see org.apache.tapestry.form.AbstractRequirableField#renderFormComponent(org.apache.tapestry.IMarkupWriter,
     *      org.apache.tapestry.IRequestCycle)
     */
    protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        _rewinding = false;

        // For rendering, the Radio components need to know what the current
        // selection is, so that the correct one can mark itself 'checked'.
        _selection = getBinding("selected").getObject();

        super.renderFormComponent(writer, cycle);
    }

    /**
     * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
     *      org.apache.tapestry.IRequestCycle)
     */
    protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        String value = cycle.getParameter(getName());

        if (value == null)
            _selectedOption = -1;
        else
            _selectedOption = Integer.parseInt(value);

        _rewinding = true;

        super.rewindFormComponent(writer, cycle);
    }

    /**
     * 取得默认选中的那个Radio的value
     * @return
     */
    public abstract Object getDefaultValue();
}
