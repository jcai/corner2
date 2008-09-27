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

package corner.orm.tapestry.component.calendar.coolest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.web.WebRequest;

import corner.orm.tapestry.component.textfield.TextField;

/**
 * Coolest时间控件
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public abstract class CoolestCalendar extends TextField{
	@InjectScript("CoolestCalendar.script")
	public abstract IScript getScript();
	
	@InjectObject("infrastructure:request")
	public abstract WebRequest getWebRequest();
	
	
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderFormComponent(writer, cycle);
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);

		Map<String, Object> scriptParms = new HashMap<String, Object>();

		Locale locale = this.getPage().getLocale();
		
		StringBuffer lang = new StringBuffer(locale.getLanguage());
		
		if(locale.getCountry() != null && locale.getCountry().length() != 0){
			lang.append("-").append(locale.getCountry().toLowerCase());
		}
		
		String jsFileName = "";
		
		if(lang.toString().equals("zh-tw")){
			jsFileName = "calendar-big5-utf8.js";
		}else if(lang.toString().equals("zh-cn")){
			jsFileName = "calendar-zh.js";
		}else{
			jsFileName = "calendar-" + lang + ".js";
		}
		
		scriptParms.put("baseUrl",		  getWebRequest().getContextPath());
		
		//增加js语言文件名
		scriptParms.put("jsFileName",     jsFileName);

		// 增加环境条件
		scriptParms.put("inputField",     getInputField());
		scriptParms.put("displayArea",    getDisplayArea());
		scriptParms.put("button",         getButton());
		scriptParms.put("eventName",      getEventName());
		scriptParms.put("ifFormat",       getIfFormat());
		scriptParms.put("daFormat",       getDaFormat());
		scriptParms.put("singleClick",    getSingleClick());
		scriptParms.put("disableFunc",    getDisableFunc());
		scriptParms.put("dateText",       getDateText());
		scriptParms.put("firstDay",       getFirstDay());
		scriptParms.put("align",          getAlign());
		scriptParms.put("range",          getRange());	//最大最小日期
		scriptParms.put("weekNumbers",    getWeekNumbers());
		scriptParms.put("flatCallback",   getFlatCallback());
		scriptParms.put("onSelect",       getOnSelect());
		scriptParms.put("onClose",        getOnClose());
		scriptParms.put("onUpdate",       getOnUpdate());
		scriptParms.put("date",           getDate());
		scriptParms.put("showsTime",      getShowsTime());
		scriptParms.put("timeFormat",     getTimeFormat());
		scriptParms.put("electric",       getElectric());
		scriptParms.put("step",           getStep());
		scriptParms.put("position",       getPosition());
		scriptParms.put("cache",          getCache());
		scriptParms.put("showOthers",     getShowOthers());
		scriptParms.put("multiple",       getMultiple());

		
//		scriptParms.put("dateStatusFunc", params["disableFunc"]);	// takes precedence if both are defined
//		scriptParms.put("flat",           null);

		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
	}
	/**
	 * 加入自动完成关闭 标签 autocomplete="off"
	 * @see org.apache.tapestry.AbstractComponent#renderInformalParameters(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	protected void renderInformalParameters(IMarkupWriter writer, IRequestCycle cycle)
    {
		writer.attribute("autocomplete", "off");
        super.renderInformalParameters(writer, cycle);
    }
	
	/**
	 * 显示
	 */
	@Parameter(defaultValue = "ognl: this.getClientId()")
	public abstract String getInputField();
	/**
	 * 显示
	 */
	@Parameter
	public abstract String getDisplayArea();
	
	/**
	 * 按钮
	 */
	@Parameter
	public abstract String getButton();
	
	/**
	 * 
	 */
	@Parameter(defaultValue = "literal:click")
	public abstract String getEventName();
	
	/**
	 * 返回时间格式
	 */
	@Parameter(defaultValue = "literal:%Y-%m-%d")
	public abstract String getIfFormat();
	
	/**
	 * 返回到DIV上的时间格式
	 */
	@Parameter(defaultValue = "literal:%Y-%m-%d")
	public abstract String getDaFormat();
	
	/**
	 * 是否单击
	 */
	@Parameter(defaultValue = "ognl:true")
	public abstract boolean getSingleClick();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "literal:function isDisabled(date){}")
	public abstract String getDisableFunc();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getDateText();
	
	/**
	 * 开始时间(星期几)
	 */
	@Parameter(defaultValue = "ognl:0")
	public abstract int getFirstDay();
	
	/**
	 * 显示Div之前插入的html元素
	 */
	@Parameter(defaultValue = "literal:Br")
	public abstract String getAlign();
	
	/**
	 * 时间范围
	 */
	@Parameter(defaultValue = "literal:[1900, 2099]")
	public abstract String getRange();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "ognl:true")
	public abstract boolean getWeekNumbers();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getFlatCallback();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getOnSelect();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getOnClose();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "literal:function catcalc(cal) {}")
	public abstract String getOnUpdate();
	
	/**
	 * 预定义时间
	 */
	@Parameter
	public abstract String getDate();
	
	/**
	 * 是否显示时间
	 */
	@Parameter(defaultValue = "ognl:false")
	public abstract String getShowsTime();
	
	/**
	 * 12/24小时制
	 */
	@Parameter(defaultValue = "literal:24")
	public abstract String getTimeFormat();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "ognl:true")
	public abstract boolean getElectric();
	
	/**
	 * 年移动步长
	 */
	@Parameter(defaultValue = "ognl:2")
	public abstract int getStep();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getPosition();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "ognl:false")
	public abstract boolean getCache(); 
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "ognl:false")
	public abstract boolean getShowOthers();
	
	/**
	 * @return
	 */
	@Parameter
	public abstract String getMultiple();
}

