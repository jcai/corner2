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

import java.util.Iterator;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author Ghost
 * @version $Revision$
 * @since 0.9.9.2
 */
public abstract class TextAreaBox extends Autocompleter {

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
