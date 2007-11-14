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

import java.math.BigDecimal;
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
 * 判断是否当前组建的值是否小于设定组建的之和 如：a=b+c+...,a为当前组建，b、c等是设定的组建
 * 
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldPlusEq extends BaseValidator {

	// 保存page页中定义的field
	private String[] otherField;

	private BigDecimal allFieldValue = null;

	public FieldPlusEq() {
		super();
	}

	public FieldPlusEq(String initializer) {
		super(initializer);
	}

	// 后台判断
	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {
		Number value = (Number) object;

		compute(field);

		if (value.doubleValue() != allFieldValue.doubleValue())
			throw new ValidatorException(buildMessage(messages, field));
	}

	/**
	 * 计算功能的方法
	 * 
	 * @param field
	 *            当前组建
	 */
	private void compute(IFormComponent field) {
		BigDecimal[] btemp = new BigDecimal[this.otherField.length];
		allFieldValue = new BigDecimal(0);
		// 计算总值
		IRequestCycle cycle = field.getPage().getRequestCycle();
		for (int i = 0; i < this.otherField.length; i++) {
			String Value = cycle.getParameter(this.otherField[i]);
			btemp[i] = new BigDecimal(Value);

			allFieldValue = allFieldValue.add(btemp[i]);
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
				"[corner.validate.isFieldPlusEq,{" + "\"fields\":["
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
		return messages.formatValidationMessage("{0}之和必须等于{1}.", null,
				new Object[] { getDisplayNames(otherField,field),field.getDisplayName() });
	}

	/**
	 * 从page页面读入的配置信息
	 * @param fieldPlusEqStr
	 *            需要相加后与本field判断的组建
	 */
	public void setFieldPlusEq(String fieldPlusEqStr) {
		// 去掉大括号
		fieldPlusEqStr = fieldPlusEqStr.substring(1,
				fieldPlusEqStr.length() - 1);
		// 初始化
		initFieldPlusEq(fieldPlusEqStr);
	}

	/**
	 * 将传入的字符串转变为字符串数组
	 * @param fieldPlusEqStr 需要处理的字符串
	 */
	private void initFieldPlusEq(String fieldPlusEqStr) {
		otherField = fieldPlusEqStr.split(":");
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
