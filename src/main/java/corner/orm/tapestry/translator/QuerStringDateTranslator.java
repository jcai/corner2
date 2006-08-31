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

import java.util.Locale;

import org.apache.hivemind.util.PropertyUtils;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.translator.AbstractTranslator;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class QuerStringDateTranslator extends AbstractTranslator{
	public QuerStringDateTranslator()
    {
       
    }

    

    public QuerStringDateTranslator(String initializer)
    {
        PropertyUtils.configureProperties(this, initializer);
    }
    
	@Override
	protected String formatObject(IFormComponent field, Locale locale, Object object) {
		return object.toString().replaceAll(" ", "");
	}

	@Override
	protected Object parseText(IFormComponent field, ValidationMessages messages, String text) throws ValidatorException {
		return text.replaceAll("\\~", " ~ ");
		
	}

}
