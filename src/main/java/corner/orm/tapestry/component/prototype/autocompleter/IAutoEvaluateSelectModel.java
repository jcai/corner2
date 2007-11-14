// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-06
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

package corner.orm.tapestry.component.prototype.autocompleter;


/**
 * 提供自动赋值时候的一个选择model.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IAutoEvaluateSelectModel extends ISelectModel{

	
	/**
	 * 解析给定的参数,提供了查询字段，以及label字段，更新字段。
	 * @param queryFieldName 查询字段的名称。
	 * @param labelFields 显示字段的定义，该字符串是以逗号分隔的字符串。
	 * @param updateFields 更新字符串，该串是一个json的字符串。
	 * @param returnTemplates 更新时使用的模板和字段的名值对组，后将用json整理
	 * 
	 */
	public void parseParameter(String queryFieldName, String labelFields, String updateFields, String returnTemplates);
	
}
