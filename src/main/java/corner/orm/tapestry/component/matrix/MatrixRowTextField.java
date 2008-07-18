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

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.form.TranslatedField;
import org.apache.tapestry.form.translator.Translator;

import corner.orm.tapestry.component.textfield.TextField;
import corner.orm.tapestry.translator.NumTranslator;
import corner.util.StringUtils;
import corner.util.VectorUtils;

/**
 * MatrixRowField使用的TextField组件，用于控制TextField的defaultValue
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class MatrixRowTextField extends TextField {
	
	/**
	 * 判断数字类型时是否使用defaultValue
	 * @return boolean值
	 * 1. 如果refValue类型为Number(long,double等等),而defValue也是Number类型
	 *     true:1.refValue=0 同时 defValue!=0 此时属于新增状态，使用defValue
	 *     false:2.refValue>0 此时属于编辑状态，此时使用refValue
	 * 2. 如果refValue或者defValue其中任意一个不是Number类型，返回false
	 * 
	 * 注意：
	 * translator是NumTranslator,同时refValue==0,同时MatrixRowField中的Value总和也是0,此时不使用defaultValue
	 */
	protected boolean checkNumberUseDefValue(TranslatedField field){
		Object defValue = getDefaultValue();//默认值
		double refV = Double.valueOf(getValue().toString());//字段已经保存的值,已经确定是Number类型
		if(StringUtils.isNumber(defValue)){
			double defV = Double.valueOf(defValue.toString());
			
			Translator translator = field.getTranslator();
			MatrixRowField  mrf = (MatrixRowField)this.getContainer();
			if(translator instanceof NumTranslator){//当前translator是NumTranslator,同时refValue==0,此时不使用defaultValue
				NumTranslator t = (NumTranslator)translator;
				double totalCount = VectorUtils.sum(mrf.getValue());
				if(totalCount>0){//数字类型MatrixRow的行汇总值大于0,不使用defaultValue
					return false;
				} else {
					return true;
				}
			} else{//Translator不是数字类型的Translator
				if(getValue() != null && StringUtils.blank(getValue().toString())){//输入是否为null或者空字符串"",如果是空，那么使用默认值
					return true;
				} else {
					return false;
				}
			}
		} else{
			return false;
		}
		
	}
}
