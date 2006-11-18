//==============================================================================
// file :       $Id:Selector.java 2023 2006-10-17 05:18:45Z jcai $
// project:     corner
//
// last change: date:       $Date:2006-10-17 05:18:45Z $
//              by:         $Author:jcai $
//              revision:   $Revision:2023 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.select;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.services.DataSqueezer;

import corner.service.EntityService;

/**
 * 一个选择器。
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author ghostbb
 * @version $Revision:2023 $
 * @since 2.2.1
 * 
 */
public abstract class Selector extends Autocompleter {
	@Parameter(defaultValue = "new corner.orm.tapestry.component.select.DefaultSelectFilter()")
	public abstract ISelectFilter getSelectFilter();

	@Parameter(required = true)
	public abstract String getQueryClassName();
	@Parameter(defaultValue="false")
	public abstract boolean getForceValidOption();

	// @Parameter(required=true,defaultValue="new
	// corner.orm.tapestry.component.select.SelectorModel()")
	@InitialValue("new corner.orm.tapestry.component.select.SelectorModel()")
	public abstract IAutocompleteModel getModel();

	public abstract void setModel(IAutocompleteModel model);

	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();

	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	@Parameter
	public abstract String getLabelField();

	/**
	 * 用于更新的字段的id（注意不是name）,用","分隔. 譬如：this,f1,f2,f3 ,而this为本字段.
	 * 
	 * @return 更新的字段列表.
	 */
	@Parameter
	public abstract String getUpdateFields();

	@Parameter
	public abstract String getReturnValueFields();

	// mode, can be remote or local (local being from html rendered option
	// elements)
	private static final String MODE_REMOTE = "remote";
	private static final String SEP_RETURN_VALUE=",";
	private static final String SEP_UPDATE_FIELDS=",";
	
	

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderComponent(org.apache.tapestry.json.IJSONWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public void renderComponent(IJSONWriter writer, IRequestCycle cycle) {
		initSelectorModel();
		//============= 以下从tapestry中的Autocompleter拷贝过来，只是修改了对key的序列华.
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

			json.put(key.toString(), filteredValues.get(key)); // json.put(getDataSqueezer().squeeze(key), filteredValues.get(key));
		}

	}

	 void initSelectorModel() {
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
			model.setReturnValueFields(this.getReturnValueFields().split(SEP_RETURN_VALUE));
		}
		model.setSelectFilter(this.getSelectFilter());
		if(this.getUpdateFields()!=null){
			String []  strs=this.getUpdateFields().split(SEP_UPDATE_FIELDS);
			model.setUpdateFields(strs);
		}
		model.setComponent(this);

	}

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderFormWidget(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderFormWidget(IMarkupWriter writer, IRequestCycle cycle) {
		initSelectorModel();
		//以下从tapestry的Autocompleter组件中copy过来，仅仅增加了updateFields属性.
		renderDelegatePrefix(writer, cycle);

		writer.begin("select");
		writer.attribute("name", getName());

		if (isDisabled())
			writer.attribute("disabled", "disabled");

		renderIdAttribute(writer, cycle);

		renderDelegateAttributes(writer, cycle);

		getValidatableFieldSupport().renderContributions(this, writer, cycle);

		// Apply informal attributes.
		renderInformalParameters(writer, cycle);

		writer.end();
		renderDelegateSuffix(writer, cycle);

		ILink link = getDirectService().getLink(true,
				new DirectServiceParameter(this));

		
		IPoSelectorModel model = (IPoSelectorModel) getModel();
		
		Map parms = new HashMap();
		parms.put("id", getClientId());

		JSONObject json = new JSONObject();
		json.put("dataUrl", link.getURL() + "&filter=%{searchString}");
		json.put("mode", MODE_REMOTE);
		json.put("widgetId", getName());
		json.put("name", getName());
		json.put("searchDelay", getSearchDelay());
		json.put("fadeTime", getFadeTime());
		

		//仅仅增加一下部分的代码
		//========= star================
		if(this.getUpdateFields()!=null){
			json.put("updateFields",this.getUpdateFields());
		}
		json.put("forceValidOption", this.getForceValidOption());
		//========= end ===============

		
		if (model == null)
			throw Tapestry.createRequiredParameterException(this, "model");

		Object value = getValue();
		Object key = value != null ? model.getPrimaryKey(value) : null;

		if (value != null && key != null) {

			json.put("value", getDataSqueezer().squeeze(key));
			json.put("label", model.getLabelFor(value));
		}

		parms.put("props", json.toString());
		parms.put("form", getForm().getName());

		PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
		getScript().execute(this, cycle, prs, parms);
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
