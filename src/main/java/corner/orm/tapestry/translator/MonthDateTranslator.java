/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: DateTranslator.java 1412 2008-07-20 01:47:09Z ghostbb $
 * created at:2008-05-14
 */

package corner.orm.tapestry.translator;

import java.text.SimpleDateFormat;

import org.apache.tapestry.form.translator.DateTranslator;

/**
 * 提供一个精确到月的Translator
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5.1
 */
public class MonthDateTranslator extends DateTranslator {
    /**
     * 中日期类型使用的pattern
     */
    private static final String OA_MONTH_DATE_PATTERN = "yyyy-MM";
    
    /**
     * 用户回显时使用的Format
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(OA_MONTH_DATE_PATTERN);

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#defaultPattern()
     */
    @Override
    protected String defaultPattern() {
        return OA_MONTH_DATE_PATTERN;
    }
}
