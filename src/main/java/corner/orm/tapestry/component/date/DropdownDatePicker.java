// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-01-19
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

package corner.orm.tapestry.component.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.dojo.form.AbstractFormWidget;
import org.apache.tapestry.form.TranslatedField;
import org.apache.tapestry.form.TranslatedFieldSupport;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 实现String类型的日期设定组件
 * 
 * @author Ghost
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class DropdownDatePicker extends AbstractFormWidget implements
		TranslatedField {
	/** parameter. */
	public abstract String getValue();

	/**
	 * 设置给定model中对应的属性
	 * 
	 * @param value
	 */
	public abstract void setValue(String value);

	/**
	 * 是否为不可用
	 * 
	 * @see org.apache.tapestry.form.IFormComponent#isDisabled()
	 */
	public abstract boolean isDisabled();

	/**
	 * 判断是否自动带有当前日期 true:自动带有当前日期;false:不带有当前日期
	 * 
	 * @return
	 */
	public abstract boolean isCurrentDate();

	/**
	 * Alt html text for the date icon, what is displayed when mouse hovers over
	 * icon.
	 */
	public abstract String getIconAlt();

	/**
	 * 设置一个日期类型的pattern
	 * 
	 * @param pattern
	 */
	public abstract void setPattern(String pattern);

	/**
	 * 得到一个日期类型的pattern
	 * 
	 * @return
	 */
	public abstract String getPattern();

	/**
	 * {@inheritDoc}
	 */
	protected void renderFormWidget(IMarkupWriter writer, IRequestCycle cycle) {

		renderDelegatePrefix(writer, cycle);

		// the html output doesn't matter very much as dojo
		// will create an inline input field for us anyways, but we do need
		// a node to reference
		writer.begin("div");
		renderIdAttribute(writer, cycle);

		renderDelegateAttributes(writer, cycle);

		getValidatableFieldSupport().renderContributions(this, writer, cycle);

		renderInformalParameters(writer, cycle);

		writer.end();
		renderDelegateSuffix(writer, cycle);

		// now create widget parms
		JSONObject json = new JSONObject();
		json.put("inputId", getClientId());
		json.put("inputName", getName());
		json.put("iconAlt", getIconAlt());
		json.put("displayFormat", getPattern());
		
		//需要copyClasses
		json.put("copyClasses", true);

		if (getValue() != null) {
			try {
				json.put("value", (new SimpleDateFormat(getPattern()))
						.parse(getValue()));
			} catch (ParseException e) {
				throw new ApplicationRuntimeException(
						"corner:DropdownDatePicker组件中使用给定的Pattern解析录入的时候字符串出错!");
			}
		} else {
			if (isCurrentDate()) {
				json.put("value", (new Date()).getTime());
			}
		}

		json.put("disabled", isDisabled());

		Map parms = new HashMap();
		parms.put("clientId", getClientId());
		parms.put("props", json.toString());

		getScript().execute(this, cycle,
				TapestryUtils.getPageRenderSupport(cycle, this), parms);
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void rewindFormWidget(IMarkupWriter writer, IRequestCycle cycle) {
		String value = cycle.getParameter(getName());

		try {
			String date = (String) getTranslatedFieldSupport().parse(this,
					value);
			// 对录入的date字符串进行验证，判断该字符串是否是时间(无默认的validator)
			getValidatableFieldSupport().validate(this, writer, cycle, date);
			setValue(date);
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#isRequired()
	 */
	public boolean isRequired() {
		return getValidatableFieldSupport().isRequired(this);
	}

	/** Injected. */
	public abstract IScript getScript();

	/** Injected. */
	public abstract TranslatedFieldSupport getTranslatedFieldSupport();

	/** Injected. */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();
}
