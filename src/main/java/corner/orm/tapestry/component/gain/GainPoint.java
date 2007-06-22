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

package corner.orm.tapestry.component.gain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.IValidationDelegate;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class GainPoint extends BaseComponent implements IFormComponent {

	/**
	 * Invoked from {@link #renderComponent(IMarkupWriter, IRequestCycle)} to
	 * rewind the component. If the component is
	 * {@link IFormComponent#isDisabled() disabled} this will not be invoked.
	 * 
	 * @param writer
	 * @param cycle
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle){
		String sl[] = cycle.getParameters(getElementName());
		
		this.setElements(Arrays.asList(sl));
		
		this.setElementLength(sl.length);
		
//		for(String s : getElements()){
//			System.out.println(getElementName() + " " + s);
//		}
	}
	
	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
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
		if (form.isRewinding()) {
			if (!isDisabled()) {
				rewindFormComponent(writer, cycle);
			}

			// This is for the benefit of the couple of components (LinkSubmit)
			// that allow a body.
			// The body should render when the component rewinds.

			if (getRenderBodyOnRewind())
				renderBody(writer, cycle);
		} if (!cycle.isRewinding()){
			super.renderComponent(writer, cycle);
			
			/**
			 * 加入js
			 */
			PageRenderSupport pageRenderSupport = TapestryUtils
					.getPageRenderSupport(cycle, this);

			Map<String, Object> scriptParms = new HashMap<String, Object>();
			
			//
			String JSONElementValues = getJSONElementValues(this.getElements());
			
			scriptParms.put("tableId", this.getTableId());	//循环的表名，只使用一次
			
			scriptParms.put("elementSize", this.getElements().size());	//tr循环的次数，只使用一次
			
			scriptParms.put("gpid", this.getClientId());	//怕重复使用gpid
			
			scriptParms.put("elementName", this.getElementName());	//循环显示的名称,使用byNames
			
			scriptParms.put("elementValues", JSONElementValues);
			
			getScript().execute(this, cycle, pageRenderSupport, scriptParms);
        }
	}
	
	


	/**
	 * 获得json串
	 * @param elements
	 */
	private String getJSONElementValues(List<String> elements) {
		
		StringBuffer jsonElementValues = new StringBuffer("{\\\"elementValues\\\": [");
		
		for(String s : elements){
			jsonElementValues.append("\\\"").append(s).append("\\\",");
		}
		
		jsonElementValues = new StringBuffer(jsonElementValues.toString().substring(0, jsonElementValues.length() - 1));

		jsonElementValues.append("]}");
		
//		System.out.println(jsonElementValues.toString());
		
		return jsonElementValues.toString();
	}

	/**
	 * 元素长度
	 */
	public abstract int getElementLength();
	public abstract void setElementLength(int length);
	
	/**
	 * 输入的元素
	 */
	public abstract List<String> getElements();
	public abstract void setElements(List<String> l);
	
	/**
	 * 写入js
	 */
	@InjectScript("GainPoint.script")
	public abstract IScript getScript();

	/**
	 * 
	 */
	@Parameter(required = true)
	public abstract String getElementName();
	
	/**
	 * 相应的tableId,由gf赋值
	 */
	public abstract String getTableId();
	public abstract void setTableId(String s);
	
	/**
	 * 
	 */
	public abstract IForm getForm();

	public abstract void setForm(IForm form);

	protected boolean getRenderBodyOnRewind() {
		return false;
	}

	protected void setName(IForm form) {
		setName(form.getElementId(this));
	}
}
