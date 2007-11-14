// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-01-31
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

import java.text.DecimalFormatSymbols;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.json.JSONLiteral;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 对上传文件后缀进行过滤
 * @author xiafei
 * @version $Revision$
 * @since 2.3.7
 */
public class FileType extends BaseValidator{
	
	private String type;
	
	private String [] types;
	
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
		context.addInitializationScript(field,"dojo.require(\"corner.validate.web\");");

		JSONObject profile = context.getProfile();
		
		if (!profile.has(ValidationConstants.CONSTRAINTS)) {
			profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
		}
		JSONObject cons = profile.getJSONObject(ValidationConstants.CONSTRAINTS);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(context.getLocale());
		
		accumulateProperty(cons, field.getClientId(), new JSONLiteral(
				"[corner.validate.isFileType,{" + "\"fields\":["
						+ toFieldString(types) + "]," + "decimal:"
						+ JSONObject.quote(symbols.getDecimalSeparator())
						+ "}]"));
		
		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context,field));
	}
	
	/**
	 * 通过的后缀
	 * @param type
	 */
	public void setFileType(String type) {
		//去掉大括号
		type = type.substring(1,type.length() - 1);
		
		types = type.split(";");
		
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
	 * 返回用双引号扩起来，用逗号分割的字符串
	 * @param fields 需要处理的数组
	 * @return 返回字符串
	 */
	public String toFieldString(String[] fields) {
		StringBuffer temp = new StringBuffer();
		for (String t : fields) {
			temp.append("\"").append(t).append("\",");
		}
		String str = temp.toString();
		return str.substring(0, str.length() - 1);
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
