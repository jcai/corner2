//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.selectBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.IValidationDelegate;

/**
 * 两个select互选选择
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class SelectBox extends BaseComponent implements IFormComponent{
	
	/**
	 * Invoked from {@link #renderComponent(IMarkupWriter, IRequestCycle)} to
	 * rewind the component. If the component is
	 * {@link IFormComponent#isDisabled() disabled} this will not be invoked.
	 * 
	 * @param writer
	 * @param cycle
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
	}
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		/**
		 * 处理form
		 */
		IForm form = TapestryUtils.getForm(cycle, this);

		setForm(form);

		if (form.wasPrerendered(writer, this))
			return;

		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);

		setName(form);
		
		/**
		 * 当提交时和为显示时
		 */
		if (cycle.isRewinding()) {
			if (!isDisabled()) {
				rewindFormComponent(writer, cycle);
			}

			// This is for the benefit of the couple of components (LinkSubmit)
			// that allow a body.
			// The body should render when the component rewinds.

			if (getRenderBodyOnRewind())
				renderBody(writer, cycle);
		}
		
		if (!cycle.isRewinding()) {
	        Map<String,Object> parms = new HashMap<String,Object>();
	        parms.put("fromId", getFromId());
	        parms.put("fromField", getFromField());
	        parms.put("foField", getToField());
	        
	        getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
	    }
	}
	
	/**
	 * 
	 */
	public abstract IForm getForm();

	/**
	 * @param form
	 */
	public abstract void setForm(IForm form);

	protected boolean getRenderBodyOnRewind() {
		return false;
	}

	/**
	 * @param form
	 */
	protected void setName(IForm form) {
		setName(form.getElementId(this));
	}

	/**
	 * FormId
	 */
	@Parameter(required = true)
	public abstract String getFromId();
	
	/**
	 * 
	 */
	@Parameter(required = true)
	public abstract String getFromField();
	
	/**
	 * 数据源，用于回显或者显示
	 */
	@Parameter(required = true)
	public abstract List<ISelectBox> getFromList();
	
	/**
	 * FormId
	 */
	@Parameter(required = true)
	public abstract String getToField();
	
	/**
	 * 已经选中的
	 */
	@Parameter
	public abstract List<ISelectBox> getToList();
	
	/**
	 * 写入js
	 */
	@InjectScript("SelectBox.script")
	public abstract IScript getScript();
}