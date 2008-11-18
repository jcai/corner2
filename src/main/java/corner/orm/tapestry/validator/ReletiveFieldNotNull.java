// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: FieldPlusEq.java 3678 2007-11-14 04:43:52Z jcai $
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
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.json.JSONLiteral;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 判断当前组件相关和当前组件相关的组件，是否都不为空
 * 
 * @author zsl
 * @version $Revision: 3678 $
 * @since 2.3
 */
public class ReletiveFieldNotNull extends BaseValidator {

	// 保存page页中定义的field
	private String [] otherField;
	
	private String fieldApInfo;

	public ReletiveFieldNotNull() {
		super();
	}

	public ReletiveFieldNotNull(String initializer) {
		super(initializer);
	}

	// 后台判断
	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {
		
	/*	String value = (String)object;
			
		if ("".equals(value)||value.length()<1){
			throw new ValidatorException(buildMessage(messages, field));
		}*/
		
		IRequestCycle cycle = field.getPage().getRequestCycle();
		for(int i = 0; i < this.otherField.length; i++){
			String str = cycle.getParameter(this.otherField[i]);
			
			if (null == str || "".equals(str) || str.length() < 1){
				throw new ValidatorException(buildMessage(messages, field));
			}
		}
	}

	
	/**
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle,
	 *      org.apache.tapestry.form.FormComponentContributorContext,
	 *      org.apache.tapestry.form.IFormComponent)
	 */
	@Override
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {

		context.addInitializationScript(field,
				"dojo.require(\"corner.validate.web\");");

		JSONObject profile = context.getProfile();

		if (!profile.has(ValidationConstants.CONSTRAINTS)) {
			profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
		}
		JSONObject cons = profile
				.getJSONObject(ValidationConstants.CONSTRAINTS);

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(context
				.getLocale());

		accumulateProperty(cons, field.getClientId(), new JSONLiteral(
				"[corner.validate.isReletiveFieldNotNull,{" + "\"fields\":["
						+ toFieldString(otherField) + "]," + "decimal:"
						+ JSONObject.quote(symbols.getDecimalSeparator())
						+ "}]"));     

		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context,field));
	}

	/**
	 * 构建message
	 * 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		//getDisplayNames(otherField,field)
		return messages.formatValidationMessage("和{0}关联的{1}都不能为空.", null,
				new Object[] { field.getDisplayName(),fieldApInfo});
	}

	/**
	 * 从page页面读入的配置信息
	 * @param fieldPlusEqStr
	 *            需要相加后与本field判断的组建
	 */
	public void setReletiveFieldNotNull(String fieldPlusEqStr) {
		// 去掉大括号
		fieldPlusEqStr = fieldPlusEqStr.substring(1,
				fieldPlusEqStr.length() - 1);
		// 初始化
		initReletiveFieldNotNull(fieldPlusEqStr);
	}
	

	/**
	 * 将传入的字符串转变为字符串数组
	 * @param fieldPlusEqStr 需要处理的字符串
	 */
	private void initReletiveFieldNotNull(String fieldPlusEqStr) {
		int i = fieldPlusEqStr.indexOf("@");
		String message = fieldPlusEqStr.substring(0, i);
		this.fieldApInfo = message;
		String fieldStr = fieldPlusEqStr.substring(i+1, fieldPlusEqStr.length());
		otherField = fieldStr.split(":");
	}

	/**
	 * 建立显示信息
	 * @param fields 需要处理的数组
	 * @param field 当前form组建
	 * @return 返回的字符串
	 */
	public String getDisplayNames(String[] fields,IFormComponent field){
		StringBuffer temp = new StringBuffer();
		for (String t : fields) {
			temp.append(((IFormComponent) field.getPage().getComponent(t)).getDisplayName()).append(",");
		}
		String str = temp.toString();
		return str.substring(0, str.length() - 1);
	}
	
	/**
	 * 返回用双引号扩起来，用逗号分割的字符串
	 * @param fields 需要处理的数组
	 * @return 返回字符串
	 */
	public String toFieldString(String[] fields) {
		StringBuffer temp = new StringBuffer();
		for (String t : fields) {
			temp.append("\"").append(t).append("\",");
		}
		String str = temp.toString();
		return str.substring(0, str.length() - 1);
	}
}
