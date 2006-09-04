//==============================================================================
//file :        TextAreaBox.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.component.textareabox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author Ghost
 * @version $Revision$
 * @since 0.9.9.2
 */
public abstract class TextAreaBox extends Autocompleter {

    private static final String MODE_REMOTE = "remote";
	
	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderFormWidget(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	protected void renderFormWidget(IMarkupWriter writer, IRequestCycle cycle) {
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
        
        IAutocompleteModel model = getModel();
        if (model == null)
            throw Tapestry.createRequiredParameterException(this, "model");
        
        Object value = getValue();
        
        if (value != null) {
            
            json.put("value", value.toString());
//            json.put("label", BeanUtils.getProperty(model,"cnlabel"));
        }
        
        parms.put("props", json.toString());
        parms.put("form", getForm().getName());
        
        PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
        getScript().execute(this, cycle, prs, parms);
	}

	/**
	 * @see org.apache.tapestry.dojo.form.Autocompleter#renderComponent(org.apache.tapestry.json.IJSONWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public void renderComponent(IJSONWriter writer, IRequestCycle cycle) {
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

	/**
	 * 设置从TextAreaBox组件返回的value
	 * <p>把从TextAreaBox返回的value直接作为结果返回
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
	
	

}
