//==============================================================================
// file :       $Id: UniqueEntity.java 2106 2006-10-26 10:19:02Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-10-26 10:19:02Z $
//              by:         $Author: jcai $
//              revision:   $Revision: 2106 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.validator;

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

import corner.util.CardIDChecker;

/**
 * 验证公民身份证号码是否正确.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class PersonID extends BaseValidator{

	public PersonID() {
		super();
	}

	public PersonID(String initializer) {
		super(initializer);
	}

	/**
	 * 
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		if(object==null){
			return;
		}
		if(CardIDChecker.validate(object.toString())){
			return;
		}
		
		throw new ValidatorException(buildMessage(messages,field));
		
	}
	//构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("{0}不正确.格式为15／18位身份证号码.",
				null, new Object[] { field
						.getDisplayName() });
	}
	/**
	 * 插入js脚本
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		context.addInitializationScript(field, "dojo.require(\"corner.validate.web\");");
        
        JSONObject profile = context.getProfile();
        
        if (!profile.has(ValidationConstants.CONSTRAINTS)) {
            profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
        }
        JSONObject cons = profile.getJSONObject(ValidationConstants.CONSTRAINTS);
        
        accumulateProperty(cons, field.getClientId(),
                new JSONLiteral("[corner.validate.isPersonID,false,true]"));
        
        accumulateProfileProperty(field, profile, 
                ValidationConstants.CONSTRAINTS, buildMessage(context, field));
	}
	
	
}
