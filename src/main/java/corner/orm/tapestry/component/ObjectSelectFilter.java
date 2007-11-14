// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-09-30
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
