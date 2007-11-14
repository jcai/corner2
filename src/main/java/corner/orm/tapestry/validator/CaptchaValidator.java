// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-10
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

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.tapestry.service.captcha.CaptchaService;
import corner.orm.tapestry.service.captcha.RandomUtil;

/**
 * 对输入的验证码进行校验
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public class CaptchaValidator extends BaseValidator{

	public CaptchaValidator() {
		super();
		
	}

	public CaptchaValidator(String initializer) {
		super(initializer);
		
	}

	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		String sid=(String) field.getPage().getRequestCycle().getInfrastructure().getRequest().getSession(true).getAttribute(CaptchaService.SESSION_ATTRIBUTE_ID);
		if(sid==null){
			return;
		}
		String encodedStr=RandomUtil.encodeStr(sid);
		if(!encodedStr.equals(object)){
			throw new ValidatorException(buildMessage(messages,field));
		}
		
	}
//	构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("输入的验证码不正确.",
				null, new Object[] { field
						.getDisplayName() });
	}

	

}
