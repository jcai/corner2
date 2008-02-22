/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2008-2-20
 */

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
import org.springframework.util.Assert;

/**
 * 判断一组checkbox至少选中一项
 * @author <a href="mailto:qsl@bjmaxinfo.com">Renais</a>
 * @version $Revision$
 * @since 0.9.0
 */
public class CheckboxGroupRequired extends BaseValidator {

	//保存错误提示信息
	private String message;

	//保存page页面定义的组件id
	private String[] otherFields;

	public CheckboxGroupRequired() {
		super();
	}

	public CheckboxGroupRequired(String initializer) {
		super(initializer);
	}
	
	/**
	 * @see org.apache.tapestry.form.validator.BaseValidator#getAcceptsNull()
	 */
	public boolean getAcceptsNull() {
        return true;
    }
	
	/**
     * Returns true, that's what Required means!
     */
    public boolean isRequired(){
        return true;
    }

	/**
	 * 后台验证
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {
		
		boolean flag = false;
		
		IRequestCycle cycle = field.getPage().getRequestCycle();
		
		for(int i = 0; i < otherFields.length; i++) {
			if(cycle.getParameter(otherFields[i]) != null) {
				flag = true;
			}
		}
		
		if (flag == false) {
			Assert.notNull(message);
			throw new ValidatorException(buildMessage(messages, field));
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
				"[corner.validate.isCheckboxGroupRequired,{" + "\"ids\":["
						+ toFieldString(otherFields) + "]," + "decimal:"
						+ JSONObject.quote(symbols.getDecimalSeparator())
						+ "}]"));

		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context, field));
	}
	
	/**
	 * 构建message 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("{0}项目必须选中一项.", null,
				new Object[] { message });
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
	
	/**
	 * 从page页面读取的配置信息
	 * @param parameters
	 */
	public void setCheckboxGroupRequired(String parameters) {
		parameters = parameters.substring(1, parameters.length() - 1);
		initParams(parameters);
	}

	/**
	 * 将传入的字符串转换为字符串数组
	 * @param parameters 需要处理的字符串
	 */
	public void initParams(String parameters){
		message = parameters.substring(parameters.indexOf(":") + 1, parameters.indexOf(";"));
		String str = parameters.substring(parameters.lastIndexOf(":") + 1);
		otherFields = str.split("-");
	}
}
