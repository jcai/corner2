/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
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

	public void setNumPattern(String pattern){
		super.setPattern(pattern.replaceAll("^\\{(\\d+):(\\d+)\\}$","^\\\\d{0,$1}(\\\\.\\\\d{0,$2})?\\$"));
	}

}