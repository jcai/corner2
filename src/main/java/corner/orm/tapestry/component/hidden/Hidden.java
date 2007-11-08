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

package corner.orm.tapestry.component.hidden;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;

import corner.util.StringUtils;

/**
 * 加入defaultValue的hidden
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class Hidden extends org.apache.tapestry.form.Hidden{
	
	/**
	 * @see org.apache.tapestry.form.Hidden#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String value = (String) getValue();

		if (value != null && value.trim().length() > 0 && !isUseDefValue()) {
			super.renderFormComponent(writer, cycle);
		} else {
			this.setValue(this.getDefaultValue());
			super.renderFormComponent(writer, cycle);
		}
	}
	
	/**
	 * 如果refValue类型为Number(long,double等等),而defValue也是Number类型
	 * 那么:
	 * true:1.refValue=0 同时 defValue!=0 此时属于新增状态，使用defValue 
	 * false:2.refValue>0 此时属于编辑状态，此时使用refValue
	 * @return boolean值
	 */
	private boolean isUseDefValue(){
		Object defValue = getDefaultValue();//默认值
		Object refValue = getValue();//字段已经保存的值
		if(StringUtils.isNumber(defValue,refValue)){
			double defV = Double.valueOf(defValue.toString());
			double refV = Double.valueOf(refValue.toString());
			if(refV == 0 && defV!=0){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
		
	}
	
	/**
	 * 取得默认值
	 * 
	 * @return
	 */
	@Parameter
	public abstract Object getDefaultValue();
}
