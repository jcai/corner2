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

package corner.orm.tapestry.translator;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hivemind.HiveMind;
import org.apache.hivemind.util.PropertyUtils;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.translator.AbstractTranslator;
import org.apache.tapestry.json.JSONLiteral;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.hibernate.v3.MatrixRow;
import corner.orm.hibernate.v3.VectorType;

/**
 * 实现对向量类型的translator。
 * <p>提供了把输入的字符串转换为向量，其中向量的分割符是{@link VectorType#SEGMENT}.
 * 校验部分代码来自于piano-core，Thanks Ghostbb.
 * 
 * @author jcai
 * @author ghostbb
 * @version $Revision$
 * @since 2.1
 */
public  class VectorTranslator extends AbstractTranslator {
	private final static String VALIDATE_VECTOR_STRING_PATTERN="^([^,]+,)*[^,]+$";
	private String _segment;

    public VectorTranslator()
    {
        _segment = defaultPattern();
    }

    

    public VectorTranslator(String initializer)
    {
        PropertyUtils.configureProperties(this, initializer);

        if (HiveMind.isBlank(_segment))
        {
            _segment = defaultPattern();
        }
    }
    private String defaultPattern() {
		
		return VectorType.SEGMENT;
	}



	/**
	 * @see org.apache.tapestry.form.translator.AbstractTranslator#formatObject(org.apache.tapestry.form.IFormComponent, java.util.Locale, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String formatObject(IFormComponent field, Locale locale, Object object) {
		MatrixRow<Object> v=(MatrixRow<Object>) object;
		if(v.size()==0){
			return null;
		}
		StringBuffer sb=new StringBuffer();
		
		for(Object s : v){
			sb.append(s.toString());
			sb.append(this._segment);
		}
		if(sb.length()>0)
			sb.setLength(sb.length()-this._segment.length());
		if(sb.toString().length()==0){
			return null;
		}
		return sb.toString();
	}



	boolean validateText(String text){
		Pattern pattern = Pattern.compile(VALIDATE_VECTOR_STRING_PATTERN);
		Matcher match = pattern.matcher(text);
		return match.find();
	}
	/**
	 * @see org.apache.tapestry.form.translator.AbstractTranslator#parseText(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.String)
	 */
	@Override
	protected Object parseText(IFormComponent field, ValidationMessages messages, String text) throws ValidatorException {
		if(text!=null){
			if(!validateText(text)){
				throw new ValidatorException(buildMessage(messages,field));
			}
			MatrixRow<String> v=new MatrixRow<String>();
			String [] array=text.split(this._segment);
			v.addAll(Arrays.asList(array));
			return v;
		}
		return null;
		
	}
//	 构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage(
				"{0}的格式不正确，正确格式是以逗号分割的字符串.", null,
				new Object[] { field.getDisplayName() });
	}


	/**
	 * @see org.apache.tapestry.form.translator.AbstractTranslator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	@Override
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		JSONObject profile = context.getProfile();
		
		if (!profile.has(ValidationConstants.CONSTRAINTS)) {
			profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
		}
		JSONObject cons = profile
				.getJSONObject(ValidationConstants.CONSTRAINTS);


		accumulateProperty(cons, field.getClientId(), new JSONLiteral(("[tapestry.form.validation.isValidPattern,\""
                + VALIDATE_VECTOR_STRING_PATTERN + "\"]")));

		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context, field));
	}
    
}
