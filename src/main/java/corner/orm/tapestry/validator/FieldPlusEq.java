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
	 * @param field	当前组建
	 */
	private void compute(IFormComponent field){
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
//	@Override
	public void renderContributio(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		
		context.addInitializationScript(field, "dojo.require(\"corner.validate.web\");");
		
        JSONObject profile = context.getProfile();
        
        if (!profile.has(ValidationConstants.CONSTRAINTS)) {
            profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
        }
        JSONObject cons = profile.getJSONObject(ValidationConstants.CONSTRAINTS);
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(context.getLocale());
        
        accumulateProperty(cons, field.getClientId(), 
                new JSONLiteral("[corner.validate.isfieldPlusEq,{"
                        + "minField:\"" + 00 + "\","
                        + "decimal:" + JSONObject.quote(symbols.getDecimalSeparator())
                        + "}]"));
        
//        new JSONLiteral("[corner.validate.isInRange,{ minField: \\ otherField[0] \\,decimal: JSONObject.quote(symbols.getDecimalSeparator())}]");
        
        
        accumulateProfileProperty(field, profile, 
                ValidationConstants.CONSTRAINTS, String.format("%s必须小于或等于%s","测试1",field.getDisplayName()));
	}

	/**
	 * 构建message
	 * 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("{0}输入有错误.", null,
				new Object[] { field.getDisplayName() });
	}

	/**
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

	private void initFieldPlusEq(String fieldPlusEqStr) {
		otherField = fieldPlusEqStr.split(":");
	}
}
