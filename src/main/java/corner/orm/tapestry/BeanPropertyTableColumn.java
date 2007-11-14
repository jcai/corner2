// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-11-04
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

import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.contrib.table.model.simple.ITableColumnEvaluator;
import org.apache.tapestry.contrib.table.model.simple.SimpleTableColumn;

import corner.util.BeanUtils;

/**
 * 
 * 基本的根据bean的属性来得到值.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 * @since 2005-11-4
 */
public class BeanPropertyTableColumn extends SimpleTableColumn {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6905390842282898107L;
	/**
	 * 有列的名称来构造.
	 * @param strColumnName 列的名称.
	 */
	public BeanPropertyTableColumn(String strColumnName) {
		super(strColumnName, strColumnName);
		this.setEvaluator(new ITableColumnEvaluator() {
			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = -6282345521481814769L;

			public Object getColumnValue(ITableColumn column, Object obj) {
				return BeanUtils.getProperty(obj, column.getColumnName());
			}
		});
	}

}
