package corner.orm.tapestry.validator;

import java.math.BigDecimal;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 判断是否当前组建的值是否小于设定组建的之和
 * 如：a=b+c+...,a为当前组建，b、c等是设定的组建
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldPlusEq extends BaseValidator{

	//保存page页中定义的field
	private String [] otherField;
	
	private BigDecimal allFieldValue;
	
	public FieldPlusEq() {
		super();
		allFieldValue = new BigDecimal(0);
	}

	public FieldPlusEq(String initializer) {
		super(initializer);
	}

	//后台判断
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		Number value = (Number)object;
		BigDecimal [] btemp = new BigDecimal[this.otherField.length];
		
		//计算总值
		for(int i=0;i<this.otherField.length;i++){
			btemp[i] = new BigDecimal(field.getPage().getRequestCycle().getParameter(this.otherField[i]));
			allFieldValue = allFieldValue.add(btemp[i]);
		}
		
		if(value.doubleValue() != allFieldValue.doubleValue())
			throw new ValidatorException(buildMessage(messages,field));
	}
	
	/**
	 * 构建message
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("{0}输入有错误.",
				null, new Object[] { field
						.getDisplayName() });
	}
	
	
	/**
	 * @param fieldPlusEqStr 需要相加后与本field判断的组建
	 */
	public void setFieldPlusEq(String fieldPlusEqStr){
		//去掉大括号
		fieldPlusEqStr = fieldPlusEqStr.substring(1, fieldPlusEqStr.length()-1);
		//初始化
		initFieldPlusEq(fieldPlusEqStr);
	}

	private void initFieldPlusEq(String fieldPlusEqStr) {
		String [] temp = fieldPlusEqStr.split(":");
		setOtherField(temp);
	}

	public String[] getOtherField() {
		return otherField;
	}

	public void setOtherField(String[] otherField) {
		this.otherField = otherField;
	}
	

}
