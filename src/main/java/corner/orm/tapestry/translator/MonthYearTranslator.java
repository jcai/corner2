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

import java.text.SimpleDateFormat;

import org.apache.tapestry.form.translator.DateTranslator;

/**
 * 提供一个精确到月的Translator
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5.1
 */
public class MonthYearTranslator extends DateTranslator {
    /**
     * 中日期类型使用的pattern
     */
    private static final String OA_MONTH_YEAR_PATTERN = "yyyy-MM";
    
    /**
     * 用户回显时使用的Format
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(OA_MONTH_YEAR_PATTERN);

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#defaultPattern()
     */
    @Override
    protected String defaultPattern() {
        return OA_MONTH_YEAR_PATTERN;
    }
}
