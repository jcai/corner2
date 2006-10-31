package corner.orm.tapestry.validator;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
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
		// TODO Auto-generated method stub
		
	}

	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		context
				.includeClasspathScript("/corner/orm/tapestry/validator/PersonIDValidator.js");

		StringBuffer buffer = new StringBuffer(
				"function(event) { Tapestry.require_field(event, '");
		buffer.append(field.getClientId());
		buffer.append("'); }");
		
		/*
		String message = TapestryUtils.enquote(buildMessage(context, field));

		StringBuffer buffer = new StringBuffer(
				"function(event) { Tapestry.require_field(event, '");
		buffer.append(field.getClientId());
		buffer.append("', ");
		buffer.append(message);
		buffer.append("); }");
		*/

		context.addSubmitHandler(buffer.toString());
	}
	
	/**
	 * Returns true, that's what Required means!
	 */
	public boolean isPersonID() {
		return true;
	}
}
