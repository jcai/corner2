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

package corner.orm.tapestry.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 * @deprecated
 */
public class ObjectSelectFilter extends DefaultSelectFilter {

	/**
	 * @see corner.orm.tapestry.component.DefaultSelectFilter#filterValues(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List filterValues(String match) {
		List ret = new ArrayList();
        
        if (match == null)
            return ret;
        
        StringBuffer buffer = new StringBuffer("");
        buffer.append(match.trim());
        buffer.append("%");
        String filter = buffer.toString();

        List _values = this.listAllMatchedValue(filter);
        for(Object obj:_values){

        	//需要保存关联的时候使用
        	Object label = obj;
//        	String cnlabel = this.getCnLabelFor(obj);
        	ret.add(label);
//        	ret.put(label, cnlabel);
        }
        return ret;
	}

}
