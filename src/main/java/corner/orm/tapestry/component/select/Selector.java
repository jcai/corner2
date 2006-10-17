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

package corner.orm.tapestry.component.select;

import java.util.Iterator;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.services.DataSqueezer;

import corner.service.EntityService;

/**
 * 一个选择器。
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author ghostbb
 * @version $Revision$
 * @since 2.2.1
 * 
 */
public abstract class Selector extends Autocompleter {
	@Parameter(defaultValue = "new corner.orm.tapestry.component.select.DefaultSelectFilter()")
	public abstract ISelectFilter getSelectFilter();

	@Parameter(required = true)
	public abstract String getQueryClassName();

	// @Parameter(required=true,defaultValue="new
	// corner.orm.tapestry.component.select.SelectorModel()")
	@InitialValue("new corner.orm.tapestry.component.select.SelectorModel()")
	public abstract IAutocompleteModel getModel();

	public abstract void setModel(IAutocompleteModel model);

	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();

	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	@Parameter(required = true)
	public abstract String getLabelField();

	
	@Parameter
	public abstract String getReturnValueFields();

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderComponent(org.apache.tapestry.json.IJSONWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public void renderComponent(IJSONWriter writer, IRequestCycle cycle) {
		initSelectorModel();
		IAutocompleteModel model = getModel();

		if (model == null)
			throw Tapestry.createRequiredParameterException(this, "model");

		Map filteredValues = model.filterValues(getFilter());

		if (filteredValues == null)
			return;

		Iterator it = filteredValues.keySet().iterator();
		Object key = null;

		JSONObject json = writer.object();

		while (it.hasNext()) {

			key = it.next();

			json.put(key.toString(), filteredValues.get(key));
		}
		
	}

	public void initSelectorModel() {
		IPoSelectorModel model = (IPoSelectorModel) this.getModel();
		model.setDataSqueezer(this.getDataSqueezer());
		try {
			model.setPoClass(Class.forName(this.getQueryClassName()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		model.setEntityService(this.getEntityService());
		model.setLabelField(this.getLabelField());
		if (this.getReturnValueFields() != null) {
			model.setReturnValueFields(this.getReturnValueFields().split(","));
		}

	}

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderFormWidget(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormWidget(IMarkupWriter arg0, IRequestCycle arg1) {
		initSelectorModel();
		super.renderFormWidget(arg0, arg1);
	}

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#rewindFormWidget(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void rewindFormWidget(IMarkupWriter arg0, IRequestCycle arg1) {
		initSelectorModel();
		super.rewindFormWidget(arg0, arg1);
	}

}
