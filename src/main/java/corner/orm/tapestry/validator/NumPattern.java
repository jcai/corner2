// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-31
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
import org.apache.tapestry.valid.ValidatorException;

/**
 * 针对数字模式的判断。
 * @author jcai
 * @version $Revision$
 * @since 2.0.6
 */
public class NumPattern extends org.apache.tapestry.form.validator.Pattern {
	private String pattern;

	public NumPattern() {
		super();
	}

	public NumPattern(String arg0) {
		super(arg0);
	}

	public void validate(IFormComponent field, ValidationMessages messages,
			Object object) throws ValidatorException {
		String input = (String) object.toString();
		super.validate(field, messages, input);
	}
	
	/**
	 * 设定num的样式，需要注意的是，样式的格式一般是：{xx:xx}
	 * 分别定义了小数点前，和小数点后的出现的数字的个数。
	 * @param pattern 验证的pattern.
	 */
	public void setNumPattern(String pattern){
		this.pattern=pattern;
		super.setPattern(pattern.replaceAll("^\\{(\\d+):(\\d+)\\}$","^\\\\d{0,$1}(\\\\.\\\\d{0,$2})?\\$"));
	}

	/**
	 * @see org.apache.tapestry.form.validator.BaseValidator#getMessage()
	 */
	@Override
	public String getMessage() {
		return pattern.replaceAll("^\\{(\\d+):(\\d+)\\}$","错误的数字格式，正确的为：小数点前面至多$1位，后面至多$2位.");
	}
	

}