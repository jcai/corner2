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

package corner.orm.tapestry.component.form;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;

/**
 * 提供一个可是使用图片的Submit组件
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class Submit extends org.apache.tapestry.form.Submit {

	/**
	 * 判断该submit是否为 image submit
	 * true:是 false:否
	 * @return boolean
	 */
	@Parameter(defaultValue="true")
	public abstract boolean isImaged();

	/**
	 * @see org.apache.tapestry.form.Submit#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if(isImaged()){
	        writer.beginEmpty("input");
	        writer.attribute("type", "image");//修改为type="image"
	        writer.attribute("name", getName());

	        if (isDisabled())
	            writer.attribute("disabled", "disabled");

	        String label = getLabel();

	        if (label != null)
	            writer.attribute("alt", label);//设置alt属性

	        renderIdAttribute(writer, cycle);

	        renderInformalParameters(writer, cycle);
	        
	        renderSubmitBindings(writer, cycle);
	        
	        writer.closeTag();
		} else{
			super.renderFormComponent(writer, cycle);
		}
	}
}
