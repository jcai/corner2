// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-24
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

import org.apache.tapestry.annotations.Parameter;

/**
 * 一个自动完成的组件，自动赋值
 * http://wiki.script.aculo.us/scriptaculous/show/Ajax.Autocompleter
 * 
 * <p>
 *  本组件提供了不但填写本字段的值，同时还可以对其他字段进行更新。<br/>
 *  <ul>
 *  	<li> 更新字段可以采用<a href="http://www.json.org">JSON</a>方式定义。</li>
 *  	<li> 而label字段采用以逗号分隔的字符串定义.</li>
 *  </ul>
 *  
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AutoEvaluateModelAutocompleter extends BaseAutocompleter {

	/** 选择的过滤器 * */
	@Parameter(defaultValue = "ognl:new corner.orm.tapestry.component.prototype.autocompleter.AutoEvaluateSelectModel()")
	public abstract IAutoEvaluateSelectModel getSelectModel();

	@Parameter(defaultValue = "literal:{select:'selectme',afterUpdateElement:ac.evaluate}")
	public abstract String getOptions();

	/**
	 * 要显示的字段
	 */
	@Parameter(required = true)
	public abstract String getLabelFields();

	/**
	 * 查询的字段,如果未指定，则为labes字段的第一个
	 */
	@Parameter
	public abstract String getQueryFieldName();
	
	/**
	 * 用于获得配置信息 {MaterialTypeField:chnName,CharacterDescField:CharacterDesc}
	 */
	@Parameter
	public abstract String getUpdateFields();

	/**
	 * 获得样式模板
	 */
	@Parameter(defaultValue = "literal:<span class=\"selectme\">%1$s</span>")
	public abstract String getTemplate();

	
	// 对选择器进行赋值
	protected ISelectModel constructSelectModel() {
		IAutoEvaluateSelectModel model=this.getSelectModel();
		model.parseParameter(this.getQueryFieldName(),
				this.getLabelFields(), this.getUpdateFields(),this.getReturnTemplates());
		return model;
	}
	
	/**
	 * 返回模版 {'&lt;span class=&quot;selectme&quot;&gt;%1$s&lt;/span&gt;/%2$s':id|name}
	 */
	@Parameter
	public abstract String getReturnTemplates();
}
