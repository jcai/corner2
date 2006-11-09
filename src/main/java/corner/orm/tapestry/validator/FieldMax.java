package corner.orm.tapestry.validator;

import java.math.BigDecimal;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldMax extends BaseValidator{
	
	//保存page页中定义的field
	private String _fieldMax;
	
	public FieldMax() {
		super();
	}

	public FieldMax(String initializer) {
		super(initializer);
	}
	
	//后台判断
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		Number value = (Number) object;
		
		//获得指定field的值
		BigDecimal otherValue = new BigDecimal(field.getPage().getRequestCycle().getParameter(this.get_fieldMax()));
		
		if(value.doubleValue() < otherValue.doubleValue())
			throw new ValidatorException(buildMessage(messages,field));
	}
	
	//	构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("必须小于或等于{0}.",
				null, new Object[] { field
						.getDisplayName() });
	}
	
	/**
	 * @param compareMaxField 需要比对大小的field
	 */
	public void setFieldMax(String compareMaxField){
		set_fieldMax(compareMaxField);
	}

	public String get_fieldMax() {
		return _fieldMax;
	}

	public void set_fieldMax(String max) {
		_fieldMax = max;
	}

	
}
