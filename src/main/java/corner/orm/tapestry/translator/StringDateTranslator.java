// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: NumTranslator.java 4015 2008-04-22 07:42:14Z lsq $
// created at:2006-12-14
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
package corner.orm.tapestry.translator;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.translator.DateTranslator;
import org.apache.tapestry.valid.ValidatorException;


/**
 * 一个将日期格式化成yyyy-MM-dd字符串的translator
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class StringDateTranslator extends DateTranslator {
    /**
     * corner简单的中日期类型使用的pattern
     */
    private static final String CORNER_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#defaultPattern()
     */
    @Override
    protected String defaultPattern() {
        return CORNER_DATE_PATTERN;
    }

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#getDateFormat(java.util.Locale)
     */
    @Override
    public SimpleDateFormat getDateFormat(Locale locale) {
        SimpleDateFormat sdf = super.getDateFormat(locale);
        sdf.setLenient(false);

        return sdf;
    }

    /**
     * @see org.apache.tapestry.form.translator.FormatTranslator#formatObject(org.apache.tapestry.form.IFormComponent,
     *      java.util.Locale, java.lang.Object)
     */
    @Override
    protected String formatObject(IFormComponent field, Locale locale,
        Object object) {
        return object.toString();
    }

    /**
     * @see org.apache.tapestry.form.translator.FormatTranslator#parseText(org.apache.tapestry.form.IFormComponent,
     *      org.apache.tapestry.form.ValidationMessages, java.lang.String)
     */
    @Override
    protected Object parseText(IFormComponent field,
        ValidationMessages messages, String text) throws ValidatorException {
        Format format = getFormat(messages.getLocale());

        try {
            format.parseObject(text);
            return getFormatDate(text, null);
        } catch (ParseException ex) {
            throw new ValidatorException(buildMessage(messages, field,
                    getMessageKey()), getConstraint());
        }
    }

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#getMessageParameters(java.util.Locale, java.lang.String)
     */
    @Override
    protected Object[] getMessageParameters(Locale locale, String label) {
        String pattern = getDateFormat(locale).toLocalizedPattern()
                             .toUpperCase(locale);

        if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
            return new Object[] { label, CORNER_DATE_PATTERN.toUpperCase() };
        } else {
            return new Object[] { label, pattern };
        }
    }
    
    /**
	 * 格式化String日期格式
	 * @param d
	 * @param format
	 * @return
	 */
	public static String getFormatDate(String d, SimpleDateFormat format){
		if(d == null){
			return "";//返回空字符串
		}
		if(format == null){
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		try {
			d = format.format(format.parse(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return d;
	}
}
