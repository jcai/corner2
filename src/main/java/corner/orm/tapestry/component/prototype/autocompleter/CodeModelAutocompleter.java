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
 * 一个自动完成的组件.
 * 使用到的js：
 *  http://wiki.script.aculo.us/scriptaculous/show/Ajax.Autocompleter
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class CodeModelAutocompleter extends BaseAutocompleter {
	 


    
	/** 选择的过滤器 **/
	@Parameter(defaultValue="ognl:new corner.orm.tapestry.component.prototype.autocompleter.CodeSelectModel()")
	public abstract ISelectModel getSelectModel();
	
	/**
	 * 获得样式模板
	 */
	@Parameter(defaultValue = "literal:<span class=\"selectme\">%1$s</span>/%3$s/%2$s")
	public abstract String getTemplate();
	
	@Parameter(defaultValue="literal:{select:'selectme'}")
	public abstract String getOptions();
	

	@Override
	protected ISelectModel constructSelectModel() {
		return this.getSelectModel();
	}

}
