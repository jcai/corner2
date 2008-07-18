/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2008-2-20
 */

package corner.orm.tapestry.validator;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

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

import corner.orm.tapestry.component.gain.GainPoint;
import corner.util.StringUtils;

/**
 * 判断gp至少有一项数据
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class GainPointRequired extends BaseValidator {

	//保存page页面定义的组件id
	private List<String> paras;

	public GainPointRequired() {
		super();
	}

	public GainPointRequired(String initializer) {
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
		
		this.initParams(field);
		
		IRequestCycle cycle = field.getPage().getRequestCycle();
		
		if (isShowException(cycle)) {
			throw new ValidatorException(buildMessage(messages, field));
		}
	}

	/**
	 * 是否弹出错误信息
	 * @param cycle
	 * @return
	 */
	private boolean isShowException(IRequestCycle cycle) {
		String [] values = null;
		for(String prop : paras) {
			values = cycle.getParameters(prop);
			if(isValuesNotNull(values)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 如果值不是空
	 * @param values
	 * @return
	 */
	private boolean isValuesNotNull(String[] values) {
		for(String v :values){
			if(StringUtils.notBlank(v)){
				return true;
			}
		}
		return false;
	}

	 /**
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		
		this.initParams(field);

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
				"[corner.validate.isGainPointRequired,{" + "\"paras\":["
						+ toFieldString() + "]," + "decimal:"
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
		return messages.formatValidationMessage("{0}必须有一行数据.", null,
				new Object[] { field.getDisplayName() });
	}
	
	/**
	 * 返回用双引号扩起来，用逗号分割的字符串
	 * @return 返回字符串
	 */
	public String toFieldString() {
		StringBuffer temp = new StringBuffer();
		for (String t : paras) {
			temp.append("\"").append(t).append("\",");
		}
		String str = temp.toString();
		return str.substring(0, str.length() - 1);
	}
	
	/**
	 * 将传入的字符串转换为字符串数组
	 * @param field 需要处理的字符串
	 */
	public void initParams(IFormComponent field){
		GainPoint gp = (GainPoint) field;
		String [] paras = gp.getShowPropertys().split(",");
		List<String> list = new ArrayList<String>();
		for(String prop : paras){
			if(!prop.equals(gp.getPagePersistentId())){
				list.add(prop);
			}
		}
		this.paras = list;
	}
}
