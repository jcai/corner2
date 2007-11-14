// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-18
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

package corner.orm.tapestry.component.propertyselection;

import org.apache.tapestry.form.IPropertySelectionModel;

/**
 * 进行选择的model，只要传进来数组就可以了
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PropertySelectionModel<T> implements IPropertySelectionModel {

	private T[] list;

	public PropertySelectionModel(T[] list) {
		this.list = list;
	}

	public String getLabel(int index) {

		return list[index].toString();
	}

	public Object getOption(int index) {
		return list[index].toString();
	}

	public int getOptionCount() {
		return list.length;
	}

	public String getValue(int index) {
		return list[index].toString();
	}

	public Object translateValue(String value) {
		return value;
	}

	public boolean isDisabled(int arg0) {
		return false;
	}
}
