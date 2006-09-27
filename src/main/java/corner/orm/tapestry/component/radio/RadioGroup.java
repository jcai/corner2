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

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

/**
 * 扩展了Tapestry中的RadioGroup,实现了指定默认选中某个Radio的功能
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class RadioGroup extends org.apache.tapestry.form.RadioGroup {
	
    

    private int _optionId;
	private Object _selection;
	/**
	 * @see org.apache.tapestry.form.RadioGroup#getNextOptionId()
	 */
	@Override
	public int getNextOptionId() {
		this._optionId++;
		return super.getNextOptionId();
	}
	/**
	 * @see org.apache.tapestry.form.RadioGroup#isSelection(java.lang.Object)
	 */
	@Override
	public boolean isSelection(Object value) {
		if(_selection==null){
			return this._optionId==this.getDefaultIndex();
		}else{
			return super.isSelection(value);
		}
	}
	
	/**
	 * @see org.apache.tapestry.form.RadioGroup#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		_selection = getBinding("selected").getObject();
		super.renderFormComponent(writer, cycle);
	}
	
	/**
	 * @see org.apache.tapestry.form.RadioGroup#cleanupAfterRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void cleanupAfterRender(IRequestCycle cycle) {
		super.cleanupAfterRender(cycle);
		this._selection=null;
	}

	/**
     * 得到需要被默认选中的索引，当索引为0时为不选中任何一个值
     * @return 索引有效值,从 1开始.
     */
    public abstract int getDefaultIndex();
}
