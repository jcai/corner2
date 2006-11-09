package corner.orm.tapestry.validator;

import java.math.BigDecimal;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;

public class FieldMax extends BaseValidator{
	
	private String _fieldMax;
	
	public FieldMax() {
		super();
	}

	public FieldMax(String initializer) {
		super(initializer);
	}

	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		Number value = (Number) object;
		
		BigDecimal otherValue = new BigDecimal(field.getPage().getRequestCycle().getParameter(this.get_fieldMax()));
		
		if(value.doubleValue() < otherValue.doubleValue())
			throw new ValidatorException(buildMessage(messages,field));
	}
	
	//	构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("必须小于{0}.",
				null, new Object[] { field
						.getDisplayName() });
	}
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
