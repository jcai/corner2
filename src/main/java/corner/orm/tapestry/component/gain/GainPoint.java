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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import org.apache.tapestry.form.Submit;
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
		String sl[] = cycle.getParameters("inName");
		
		for(String s : sl){
			System.out.println(s);
		}
	}
	
	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {

		Set entries = cycle.getPage().getComponents().entrySet();

		Iterator iter = entries.iterator();

		StringBuffer reNameTemps = new StringBuffer("{\\\"reNames\\\": [");

		String reNames = null;

		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			// Object key = entry.getKey();

			if (!(entry.getValue() instanceof IFormComponent)) {
				continue;
			}
			
			if((entry.getValue() instanceof Submit) || (entry.getValue() instanceof GainPoint)){
				continue;
			}

			IFormComponent component = (IFormComponent) entry.getValue();

			// if(cc.getBinding("id").equals(this.getClientId()) ){
			reNameTemps.append("\\\"").append(component.getClientId()).append(
					"\\\",");
			// }
		}

		reNames = reNameTemps.toString().substring(0, reNameTemps.length() - 1);

		reNames += "]}";

		
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
        }

		/**
		 * 原来的处理name
		 */
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);

		Map<String, Object> scriptParms = new HashMap<String, Object>();

		scriptParms.put("gpid", this.getClientId());
		scriptParms.put("elementName", this.getElementName());
		scriptParms.put("reNames", reNames);

		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
	}

	/**
	 * @return
	 */
	@InjectScript("GainPoint.script")
	public abstract IScript getScript();

	/**
	 * 
	 */
	@Parameter(defaultValue = "literal:aaa")
	public abstract String getElementName();
	
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
