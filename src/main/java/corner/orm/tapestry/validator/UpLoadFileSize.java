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
 * @author <a href="mailto:wlh@bjmaxinfo.com">wlh</a>
 * @version $Revision$
 * @since 2.5.1
 */
public class UpLoadFileSize extends BaseValidator {

	/**
	 * 
	 */
	public UpLoadFileSize() {
		super();
	}

	/**
	 * @param initializer
	 */
	public UpLoadFileSize(String initializer) {
		super(initializer);
	}

	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent,
	 *      org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {

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

		DecimalFormatSymbols decimal = new DecimalFormatSymbols(context
				.getLocale());

		String literalStr = "[corner.validate.isUpLoadFileSize,{\"maxUpLoad\":"
				+ maxFileSize + ","
				+ "\"fieldId\":\""
				+ field.getClientId()
				+ "\","
				+ "decimal:"
				+ JSONObject.quote(decimal.getDecimalSeparator()) + "}]";
		System.err.println("literalStr:" + literalStr);
		JSONLiteral literal = new JSONLiteral(literalStr);

		this.accumulateProperty(cons, field.getClientId(), literal);
		this.accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, context
						.formatValidationMessage("{0}文件最大上传限制为{1}KB", null,
								new Object[] { field.getDisplayName(),
										maxFileSize }));
	}

	/**
	 * UpLoadFileSizeValidator
	 * 
	 */
	private String maxFileSize;

	/**
	 * @param maxFileSize
	 * @return
	 */
	public void setUpLoadFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
}
