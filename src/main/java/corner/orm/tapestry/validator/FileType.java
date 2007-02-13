package corner.orm.tapestry.validator;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 对上传文件后缀进行过滤
 * @author xiafei
 * @version $Revision$
 * @since 2.3.7
 */
public class FileType extends BaseValidator{
	
	private String type;
	
	public FileType() {
		super();
	}

	public FileType(String initializer) {
		super(initializer);
	}

	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		
		String postfix = ((IUploadFile)object).getFileName();
		
		postfix = postfix.substring(postfix.lastIndexOf('.'),postfix.length());
		
		if (type.indexOf(postfix) == -1)
			throw new ValidatorException(buildMessage(messages, field));
	}
	
	/**
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	@Override
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle, FormComponentContributorContext context, IFormComponent field) {
		
	}
	
	/**
	 * 通过的后缀
	 * @param type
	 */
	public void setFileType(String type) {
		//去掉大括号
		type = type.substring(1,type.length() - 1);
		
		String [] types = type.split(";");
		
		int counter = 0;
		for(String s : types){
			if(s.charAt(0) == '.'){
				counter ++;
			}
		}
		
		if(counter == types.length){
			this.type = type;
		}else{
			exceptionAdvertiser(type);
		}
		
	}
	
	/**
	 * 构建message
	 * 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("只允上传{0}类型的文件.", null,
				new Object[] { type });
	}
	
	/**
	 * 根据给定的FileType抛出一个ApplicationRuntimeException
	 * @param FileType
	 */
	protected void exceptionAdvertiser(String postfix){
		throw new ApplicationRuntimeException("FileType validator的参数'"
				+ postfix + "'不对，应该为'{.postfix;.postfix;}'");
	}
}
