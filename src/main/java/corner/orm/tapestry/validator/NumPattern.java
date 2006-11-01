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
		return pattern.replaceAll("^\\{(\\d+):(\\d+)\\}$","错误的数字格式，正确的为：小数点前面$1位，后面$2位.");
	}
	

}