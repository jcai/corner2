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

package corner.orm.tapestry.component.textArea;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.TranslatedField;

import corner.orm.tapestry.translator.NumTranslator;
import corner.util.StringUtils;

/**
 * 带默认值的TextArea
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class TextArea extends org.apache.tapestry.form.TextArea {
	/**
	 * 取得默认值
	 */
	@Parameter
	public abstract Object getDefaultValue();

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		if (getDefaultValue() == null || StringUtils.blank(getDefaultValue().toString())) {
			super.renderFormComponent(writer, cycle);
		} else {
			String value = getTranslatedFieldSupport().format(this, getValue());
			if(StringUtils.isNumber(getValue()) || (this.getTranslator() instanceof NumTranslator)){//如果是Number类型
				if(checkNumberUseDefValue(this)){
					this.setValue(this.getDefaultValue());
					super.renderFormComponent(writer, cycle);
				} else {
					super.renderFormComponent(writer, cycle);
				}
			} else{//如果不是
				if(value != null && StringUtils.notBlank(value)){//空字符串的时候也会使用defaultValue
					super.renderFormComponent(writer, cycle);
				} else {
					this.setValue(this.getDefaultValue());
					super.renderFormComponent(writer, cycle);
				}
			}
		}
	}
	
	/**
	 * 判断数字类型时是否使用defaultValue
	 * @return boolean值
	 * 1. 如果refValue类型为Number(long,double等等),而defValue也是Number类型
	 *     true:1.refValue=0 同时 defValue!=0 此时属于新增状态，使用defValue
	 *     false:2.refValue>0 此时属于编辑状态，此时使用refValue
	 * 2. 如果refValue或者defValue其中任意一个不是Number类型，返回false
	 */
	protected boolean checkNumberUseDefValue(TranslatedField field){
		Object defValue = getDefaultValue();//默认值
		Object refValue = getValue();
		if(defValue != null){//判断录入是否为空
			if(StringUtils.isNumber(defValue)){
				double defV = Double.valueOf(defValue.toString());
				double refV = refValue!=null?Double.valueOf(refValue.toString()):0;//字段已经保存的值,已经确定是Number类型
				if(refV == 0 && defV!=0){
					return true;
				} else{
					return false;
				}
			} else{
				return false;
			}
		} else {
			return false;
		}
	}
}