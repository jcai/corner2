// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-07
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

package corner.orm.tapestry.component.tinymce;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;

/**
 * 提供一个编辑框。
 *  js采用了TinyMCE,官方网站为： http://tinymce.moxiecode.com/ 
 *  关于editor的设置，可以参见：
 * http://tinymce.moxiecode.com/tinymce/docs/reference_configuration.html
 * 
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author agilewang
 * @version $Revision$
 * @since 2.3
 */
public abstract class TinyMceEditor extends AbstractComponent {

	/**
	 * 采用的模式，一共有： 
	 * extareas 
	 * Converts all textarea elements to editors when the
	 * page loads.
	 * 
	 * specific_textareas 
	 * Converts all textarea elements with the a
	 * textarea_trigger attribute set to "true".
	 * 
	 * exact 
	 * exact - Converts only explicit elements, these are listed in the
	 * elements option. These elements can be any kind for example textareas or
	 * divs.
	 * 
	 * 详细参见：http://tinymce.moxiecode.com/tinymce/docs/option_mode.html
	 * 
	 * @return
	 */
	@Parameter(defaultValue = "literal:exact")
	public abstract String getMode();
	/**
	 * This option should contain a comma separated list of element id's to convert into editor instances. This option is only used if mode is set to "exact".
	 * 详见：
	 * http://tinymce.moxiecode.com/tinymce/docs/option_elements.html
	 * @return
	 */
	@Parameter
	public abstract String getElements();

	@Parameter(defaultValue = "literal:zh_cn_utf8")
	public abstract String getLanguage();

	@Parameter(defaultValue = "literal:advanced")
	public abstract String getTheme();

	@Parameter(defaultValue = "literal:500")
	public abstract String getWidth();

	@Parameter(defaultValue = "literal:400")
	public abstract String getHeight();

	@Parameter(defaultValue = "literal:table,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,zoom,media,searchreplace,print,contextmenu,paste,directionality,fullscreen")
	public abstract String getPlugins();

	@Parameter
	public abstract String getContentCss();

	@Parameter(defaultValue = "ognl:false")
	public abstract boolean isDebug();

	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		if(cycle.isRewinding()) return;
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", getMode());
		map.put("elements", getElements());
		map.put("language", getLanguage());
		map.put("theme", getTheme());
		map.put("width", getWidth());
		map.put("height", getHeight());
		map.put("plugins", getPlugins());
		map.put("contentCss", getContentCss());
		map.put("debug", String.valueOf(isDebug()));

		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);
		getScript().execute(this, cycle, pageRenderSupport, map);
	}

	@InjectScript("TinyMceEditor.script")
	public abstract IScript getScript();

}
