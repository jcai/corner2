// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-09
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

package corner.orm.tapestry.validator;

import java.text.DecimalFormatSymbols;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.Min;
import org.apache.tapestry.json.JSONLiteral;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 判断是否当前组建的值是否小于设定组建的值
 * 
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldMin extends Min {

	// 保存page页中定义的field
	private String _minField;

	public FieldMin() {
		super();
	}

	public FieldMin(String initializer) {
		super(initializer);
	}

	/**
	 * 后台判断
	 * 
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent,
	 *      org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {
		// 获得指定field的值
		String otherFieldValue = field.getPage().getRequestCycle().getParameter(_minField);
		
		if(otherFieldValue == null || otherFieldValue.equals("")){
			otherFieldValue = "0";
		}
		
		double otherValue = Double.parseDouble(otherFieldValue);	//如果是null赋值为0
		this.setMin(otherValue);
		super.validate(field, messages, object);
	}
	
    
	/**
	 * @see org.apache.tapestry.form.validator.Min#renderContribution(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle,
	 *      org.apache.tapestry.form.FormComponentContributorContext,
	 *      org.apache.tapestry.form.IFormComponent)
	 */
	@Override
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		
		context.addInitializationScript(field, "dojo.require(\"corner.validate.web\");");
		
        JSONObject profile = context.getProfile();
        
        if (!profile.has(ValidationConstants.CONSTRAINTS)) {
            profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
        }
        JSONObject cons = profile.getJSONObject(ValidationConstants.CONSTRAINTS);
        
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(context.getLocale());
        
        accumulateProperty(cons, field.getClientId(), 
                new JSONLiteral("[corner.validate.isInRange,{"
                        + "minField:\"" +_minField + "\","
                        + "decimal:" + JSONObject.quote(symbols.getDecimalSeparator())
                        + "}]"));
        
        accumulateProfileProperty(field, profile, 
                ValidationConstants.CONSTRAINTS, String.format("%s必须小于或等于%s",((IFormComponent) field.getPage().getComponent(_minField)).getDisplayName(),field.getDisplayName()));
	}

	/**
	 * @param compareMaxField
	 *            需要比对大小的field
	 */
	public void setFieldMin(String compareMinField) {
		this._minField = compareMinField;
	}
}
