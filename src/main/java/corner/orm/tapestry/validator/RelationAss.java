// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-15
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

import corner.orm.tapestry.component.prototype.autocompleter.AssociateAutocompleter;

/**
 * 针对自动完成的关联选择的校验.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class RelationAss extends BaseValidator{

	
	
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
		
		String Value = cycle.getParameter(field.getClientId() +AssociateAutocompleter.ASSOCIATE_SUFFIX);
		
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
		
		accumulateProperty(cons, field.getClientId(), new JSONLiteral(
				"[corner.validate.isRelationAss,{" + "\"ass_id\":'"+ field.getClientId() +AssociateAutocompleter.ASSOCIATE_SUFFIX +"'}]"));
		
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
		return messages.formatValidationMessage("没有关联的{0}。", null,
				new Object[] { field.getDisplayName() });
	}
}
