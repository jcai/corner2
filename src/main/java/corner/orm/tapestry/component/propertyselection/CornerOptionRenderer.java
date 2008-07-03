// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-06-29
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.component.propertyselection;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IOptionRenderer;
import org.apache.tapestry.form.IPropertySelectionModel;

/**
 * 实现了{@link IOptionRenderer}接口，并增加了全选的选项
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CornerOptionRenderer implements IOptionRenderer{
	
    /**
     * Default instance used by {@link PropertySelection} if no custom renderer is 
     * specified.
     */
    
    public static final IOptionRenderer DEFAULT_OPTION_RENDERER = new corner.orm.tapestry.component.propertyselection.CornerOptionRenderer();

	/**
	 * @see org.apache.tapestry.form.IOptionRenderer#renderOptions(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IPropertySelectionModel, java.lang.Object)
	 */
	public void renderOptions(IMarkupWriter writer, IRequestCycle cycle, IPropertySelectionModel model, Object selected) {
        int count = model.getOptionCount();
        boolean foundSelected = false;
        
        /**
         * 从cycle中取得是否显示自定义的名称:全选/selectAll.
         */
        Boolean isShowAll = (Boolean) cycle.getAttribute(PropertySelection.IS_SHOW_SELECTALL_OPTION_NAME);
        
        //要显示:全部/SelectAll.
        if(isShowAll.booleanValue()){
        	 /**
             * 从cycle中取得自定义的名称
             */
            String optionName = cycle.getAttribute(PropertySelection.USER_DEFINED_OPTION_NAME).toString();
            
            if(optionName == null || optionName.trim().length() == 0){
            	optionName = "select all";
            }
            //在全部option之前插入一个全选的option
            writer.begin("option");
            writer.attribute("value", "%");

            if (!foundSelected && isEqual("%", selected))
            {
                writer.attribute("selected", "selected");

                foundSelected = true;
            }        

            writer.print(optionName);

            writer.end();

            writer.println();
        }
       
        
        for (int i = 0; i < count; i++)
        {
            Object option = model.getOption(i);
            
            //disabled时,使用optgroup.这样兼容IE,FF.注意:label需是"&nbsp;".
            if (model.isDisabled(i)){
            	
            	writer.begin("optgroup");
            	writer.attribute("value",model.getValue(i));
                writer.attributeRaw("label", model.getLabel(i));
            	writer.end();
            	writer.println();
            	
            }else{
            	writer.begin("option");
                writer.attribute("value", model.getValue(i));

                if (!foundSelected && isEqual(option, selected))
                {
                    writer.attribute("selected", "selected");
                    
                    foundSelected = true;
                }
                
                writer.print(model.getLabel(i));

                writer.end();

                writer.println();
            }
            
        }
	}
	
    protected boolean isEqual(Object left, Object right)
    {
        // Both null, or same object, then are equal

        if (left == right)
            return true;
        
        // If one is null, the other isn't, then not equal.
        
        if (left == null || right == null)
            return false;
        
        // Both non-null; use standard comparison.
        
        return left.equals(right);
    }

}
