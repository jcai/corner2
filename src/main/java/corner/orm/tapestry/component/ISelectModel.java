// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-09-06
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

import org.apache.tapestry.dojo.form.IAutocompleteModel;

/**
 * 提供具有中文名称检索的接口
 * <p>提供中文检索功能的CornerSelect和TextAraBox的model如果想支持中文检索，应该实现此接口</p>
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 * @deprecated
 */
public interface ISelectModel extends IAutocompleteModel {

	/**
	 * 取得中文名称
	 * <p>次方法用于返回中文名称</p>
	 * @param value
	 * @return
	 */
	String getCnLabelFor(Object value);
	
	public void setFilter(ISelectFilter filter);
}
