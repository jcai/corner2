package corner.orm.tapestry.validator;

import java.util.Collection;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.multipart.UploadPart;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidationConstraint;
import org.apache.tapestry.valid.ValidationStrings;
import org.apache.tapestry.valid.ValidatorException;

public class PersonID extends BaseValidator{

	public PersonID() {
		super();
	}

	public PersonID(String initializer) {
		super(initializer);
	}
	
	public boolean getAcceptsNull() {
		return true;
	}

	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		if ((object == null)
				|| (String.class.isInstance(object) && (((String) object)
						.length() == 0))
				|| (Collection.class.isInstance(object) && ((Collection) object)
						.isEmpty())
				|| (UploadPart.class.isInstance(object) && ((UploadPart) object)
						.getSize() < 1)) {
			String message = buildMessage(messages, field);
			throw new ValidatorException(message, ValidationConstraint.REQUIRED);
		}
	}
	
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage(getMessage(),
				ValidationStrings.REQUIRED_FIELD, new Object[] { field
						.getDisplayName() });
	}

	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		
		/*
		context.registerForFocus(ValidationConstants.REQUIRED_FIELD);

		StringBuffer buffer = new StringBuffer(
				"function(event) { Tapestry.require_field(event, '");
		buffer.append(field.getClientId());
		buffer.append("', ");
		buffer.append(TapestryUtils.enquote(buildMessage(context, field)));
		buffer.append("); }");

		context.addSubmitHandler(buffer.toString());
		*/
		
		context
				.includeClasspathScript("/corner/orm/tapestry/validator/PersonIDValidator.js");
		
		StringBuffer buffer = new StringBuffer(
				"function(event) { Tapestry.personid_field(event,'");
		buffer.append(field.getClientId());
		buffer.append("'); }");

		context.addSubmitHandler(buffer.toString());
		
	}
	
	/**
	 * Returns true, that's what Required means!
	 */
	public boolean isPersonID() {
		return true;
	}
}
