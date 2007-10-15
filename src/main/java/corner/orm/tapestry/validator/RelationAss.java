//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

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
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class RelationAss extends BaseValidator{

	private static final String ASSOCIATE_SUFFIX="_ASS";
	
	public RelationAss() {
		super();
	}

	public RelationAss(String initializer) {
		super(initializer);
	}

	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		IRequestCycle cycle = field.getPage().getRequestCycle();
		
		String Value = cycle.getParameter(field.getClientId() + ASSOCIATE_SUFFIX);
		
		if(Value.equals("X") || Value.length() == 0)
			throw new ValidatorException(buildMessage(messages, field));
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

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(context
				.getLocale());

		accumulateProperty(cons, field.getClientId(), new JSONLiteral(
				"[corner.validate.isRelationAss,{" + "\"fields\":["
						+ field.getClientId() + ASSOCIATE_SUFFIX + "]," + "decimal:"
						+ JSONObject.quote(symbols.getDecimalSeparator())
						+ "}]"));

		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context,field));
	}

	/**
	 * 构建message
	 * 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("没有获得关联对象。", null,
				new Object[] { field.getDisplayName() });
	}
}
