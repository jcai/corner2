package corner.orm.tapestry.validator;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.Min;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldMax extends Min{
	
	//保存page页中定义的field
	private String _fieldMax;
	
	public FieldMax() {
		super();
	}

	public FieldMax(String initializer) {
		super(initializer);
	}
	
	/**
	 * 后台判断
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		//获得指定field的值
		double otherValue = Double.parseDouble(field.getPage().getRequestCycle().getParameter(_fieldMax));
		this.setMin(otherValue);
		super.validate(field,messages,object);
	}
	
	/**
	 * @param compareMaxField 需要比对大小的field
	 */
	public void setFieldMax(String compareMaxField){
		this._fieldMax=compareMaxField;
	}
}
