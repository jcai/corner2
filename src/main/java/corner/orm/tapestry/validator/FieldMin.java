package corner.orm.tapestry.validator;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.Min;
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
	private String _fieldMax;

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
		double otherValue = Double.parseDouble(field.getPage()
				.getRequestCycle().getParameter(_fieldMax));
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
		
	}

	/**
	 * @param compareMaxField
	 *            需要比对大小的field
	 */
	public void setFieldMin(String compareMinField) {
		this._fieldMax = compareMinField;
	}
}
