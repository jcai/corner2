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

import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.services.DataSqueezer;

import corner.orm.tapestry.state.IContext;
import corner.service.EntityService;

/**
 * 一个选择的过滤器
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface ISelectModel {
	/**
	 * 用于Autocompleter的拼音检索的正则表达式
	 */
	public static final String ABC_STR = "[a-z]";
	/**
	 * 默认的拼音字段名称
	 */
	public static final String ABC_FIELD = "abcCode";
	/**
	 * 用于Autocompleter的中文检索的正则表达式
	 */
	public static final String CHN_STR = "[\u4e00-\u9fa5]";
	/**
	 * 默认的中文字段名称
	 */
	public static final String CHN_FIELD = "chnName";
	/**
	 * 字典表中,各种字典实体的简写码
	 */
	public static final String INDEX_CODE_STR = "[A-Z]";
	public static final String INDEX_CODE_FIELD = "indexCode";
	/**
	 * 用于Autocompleter的数字检索的正则表达式
	 */
	public static final String NUM_STR = "[0-9]";
	/**
	 * 默认的数字字段名称
	 */
	public static final String NUM_FIELD = "numCode";
	/**
	 * 搜索
	 * @param service corner基础服务类
	 * @param queryClassName 查询的类名.
	 * @param searchString 搜索的字符串
	 * @param company 当前公司
	 * @return 结果
	 */
	public List search(EntityService service,String queryClassName,String searchString,IContext context,DataSqueezer squeezer,String [] dependFieldsValue);
	
	/**
	 * 对结果的某一行进行渲染
	 * @param writer 写入浏览器
	 * @param entity 返回结果集中的某一个元素
	 * @param labelFields 回显的结果
	 */
	public void renderResultRow(IMarkupWriter writer,Object entity,String template,DataSqueezer squeezer);
	
	/**
	 * 保持当前组建
	 */
	public void setComponent(IComponent component);
	public IComponent getComponent();
}
