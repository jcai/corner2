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

package corner.orm.tapestry.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;

import corner.service.EntityService;

/**
 * CornerSelect和TextAreaBox组件的实现类
 * @author Ghost
 * @version $Revision$
 * @since 2.1.1
 */
public abstract class AbstractCornerSelectWidget extends Autocompleter {
    // mode, can be remote or local (local being from html rendered option elements)
    private static final String MODE_REMOTE = "remote";	
    /**
     * render FormWidget
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	protected void renderFormWidget(IMarkupWriter writer, IRequestCycle cycle)
    {
        renderDelegatePrefix(writer, cycle);
        
        writer.begin("input");
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
        
        ILink link = getDirectService().getLink(true, new DirectServiceParameter(this));
        
        Map parms = new HashMap();
        parms.put("id", getClientId());
        
        JSONObject json = new JSONObject();
        json.put("dataUrl", link.getURL() + "&filter=%{searchString}");
        json.put("mode", MODE_REMOTE);
        json.put("widgetId", getName());
        json.put("name", getName());
        json.put("searchDelay", getSearchDelay());
        json.put("fadeTime", getFadeTime());
        
        ISelectModel model = getModel();
        if (model == null)
            throw Tapestry.createRequiredParameterException(this, "model");
        
        Object value = getValue();
        Object key = value != null ? model.getPrimaryKey(value) : null;
        
        //用于在json字符串中写如三个值，用于修改操作时候现实组件中选中的值
        if (value != null && key != null) {
            
            json.put("value", key);
            json.put("cnlabel", model.getCnLabelFor(value));
            json.put("label", model.getLabelFor(value));
        }
        
        parms.put("props", json.toString());
        parms.put("form", getForm().getName());
        
        PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
        getScript().execute(this, cycle, prs, parms);
    }	
	
	/**
	 * render Component
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderComponent(org.apache.tapestry.json.IJSONWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public void renderComponent(IJSONWriter writer, IRequestCycle cycle) {
        ISelectModel model = getModel();
        
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
	/**
	 * 设置从widget组件返回的value
	 * <p>把从widget返回的value直接作为结果返回
	 * @see org.apache.tapestry.dojo.form.Autocompleter#rewindFormWidget(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void rewindFormWidget(IMarkupWriter writer, IRequestCycle cycle) {
        String value = cycle.getParameter(getName());  
        Object object = value;
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
	
	/**
	 * 返回CornerSelectModel
	 * <p>返回自定义的CornerSelectModel</p>
	 * @see org.apache.tapestry.dojo.form.Autocompleter#getModel()
	 */	
	public ISelectModel getModel(){
		try {
			CornerSelectModel model=new CornerSelectModel();
			ISelectFilter selectFilter = this.getSelectFilter()==null?new DefaultSelectFilter():this.getSelectFilter();
			selectFilter.setEntityService(this.getEntityService());
			selectFilter.setQueryClass(Class.forName(this.getQueryClass()));
			selectFilter.setLabel(this.getLabel());
			selectFilter.setCnLabel(this.getCnlabel());
			model.setFilter(selectFilter);
			return model;
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得SelectFilter
	 * @return
	 */
	public abstract ISelectFilter getSelectFilter();
	
	/**
	 * 设置下拉层列表中要显示的实体
	 * @param queryClass
	 */
	 public abstract void setQueryClass(String queryClass);
	 /**
	  * 取得拉层列表中要显示的实体
	  * @return
	  */
	 public abstract String getQueryClass();
	 /**
	  * 提供对实体的增删改查操作的能力
	  * @return
	  */
	 @InjectObject("spring:entityService")
	 public abstract EntityService getEntityService();
	 /**
	  * 取得供拼音检索的字段名称
	  * @return
	  */
	 public abstract String getLabel();
	 /**
	  * 设置拼音检索的字段名称
	  * @param label
	  */
	 public abstract void  setLabel(String label);
	 
	 /**
	  * 设置中文检索的字段名称
	  * @param cnlabel
	  */
	 public abstract void setCnlabel(String cnlabel);
	 /**
	  * 取得中文检索的字段名称
	  * @return
	  */
	 public abstract String getCnlabel();
}
