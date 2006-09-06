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

package corner.orm.tapestry.component.cornerselect;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.json.JSONObject;

import corner.orm.tapestry.component.CornerSelectModel;
import corner.orm.tapestry.component.ISelectModel;
import corner.service.EntityService;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class CornerSelect extends Autocompleter {
	
    // mode, can be remote or local (local being from html rendered option elements)
    private static final String MODE_REMOTE = "remote";	
    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	protected void renderFormWidget(IMarkupWriter writer, IRequestCycle cycle)
    {
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
        
        if (value != null && key != null) {
            
            json.put("value", getDataSqueezer().squeeze(key));
            json.put("cnlabel", model.getCnLabelFor(value));
        }
        
        parms.put("props", json.toString());
        parms.put("form", getForm().getName());
        
        PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
        getScript().execute(this, cycle, prs, parms);
    }	
	
	/**
	 * 返回CornerSelectModel
	 * <p>返回自定义的CornerSelectModel</p>
	 * @see org.apache.tapestry.dojo.form.Autocompleter#getModel()
	 */
	public ISelectModel getModel(){
		try {
			return new CornerSelectModel(this.getEntityService(),Class.forName(this.getQueryClass()),this.getLabel(),this.getCnlabel());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
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
