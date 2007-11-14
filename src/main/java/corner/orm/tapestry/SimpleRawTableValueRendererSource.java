// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-21
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

package corner.orm.tapestry;

import org.apache.tapestry.IRender;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.contrib.table.model.ITableModelSource;
import org.apache.tapestry.contrib.table.model.ITableRendererSource;
import org.apache.tapestry.contrib.table.model.simple.SimpleTableColumn;
import org.apache.tapestry.valid.RenderString;
/**
 * 可以显示raw书局的TableValueRenderer.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-10-21
 */
public class SimpleRawTableValueRendererSource implements ITableRendererSource {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5955327358717411812L;
	private static final String EMPTY_REPRESENTATION = "&nbsp;";

	/**
	 * 
	 * @see org.apache.tapestry.contrib.table.model.ITableRendererSource#getRenderer(org.apache.tapestry.IRequestCycle, org.apache.tapestry.contrib.table.model.ITableModelSource, org.apache.tapestry.contrib.table.model.ITableColumn, java.lang.Object)
	 */
	public IRender getRenderer(IRequestCycle objCycle, ITableModelSource objSource,
			ITableColumn objColumn, Object objRow) {
		SimpleTableColumn objSimpleColumn=(SimpleTableColumn)objColumn;
		
		Object objValue = objSimpleColumn.getColumnValue(objRow);
		if(objValue==null)
			return new RenderString(EMPTY_REPRESENTATION,true);
		return new RenderString(objValue.toString(),true);
	}

}
