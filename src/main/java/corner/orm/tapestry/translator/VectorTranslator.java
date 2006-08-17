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
import java.util.Vector;

import org.apache.hivemind.HiveMind;
import org.apache.hivemind.util.PropertyUtils;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.translator.AbstractTranslator;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.hibernate.v3.VectorType;

/**
 * 实现对向量类型的translator。
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public  class VectorTranslator extends AbstractTranslator {
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
		Vector<String> v=(Vector<String>) object;
		StringBuffer sb=new StringBuffer();
		
		for(String s : v){
			sb.append(s);
			sb.append(this._segment);
		}
		if(sb.length()>0)
			sb.setLength(sb.length()-this._segment.length());
		return sb.toString();
	}



	/**
	 * @see org.apache.tapestry.form.translator.AbstractTranslator#parseText(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.String)
	 */
	@Override
	protected Object parseText(IFormComponent field, ValidationMessages messages, String text) throws ValidatorException {
		Vector<String> v=new Vector<String>();
		String [] array=text.split(this._segment);
		v.addAll(Arrays.asList(array));
		return v;
	}
    
}
