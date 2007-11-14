// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-10-16
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

package corner.orm.tapestry.component.select;

import java.util.List;

/**
 * 对字符串进行查询的过滤器.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author ghostbb
 * @version $Revision:2023 $
 * @since 2.2.1
 */
public interface ISelectFilter {

	/**
	 * 对给定的字符串进行搜索。
	 * <p>
	 * 返回的map，key为选择器的下拉列表的显示的值，而value为待处理的数据.
	 * @param match 待搜索的字符串
	 * @param model 选择器的模型.
	 * @return 一个供选择的map。
	 */
	List query(String match,IPoSelectorModel model);
	

	/**
	 * 返回label字段名称.
	 * @return label字段名称.
	 */
	public String getLabelField();
	/**
	 * 得到返回值的字段
	 * @return 返回值的返回字段.
	 */
	public String[] getReturnValueFields();

}
