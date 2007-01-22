//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.date;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.form.AbstractFormComponent;
import org.apache.tapestry.form.TranslatedField;
import org.apache.tapestry.form.TranslatedFieldSupport;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.valid.ValidatorException;

/**
 * 实现String类型的日期设定组件
 * 
 * @author Ghost
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class DatePicker extends AbstractFormComponent implements
		TranslatedField {
	private static final String SYM_NAME = "name";

	private static final String SYM_FORMNAME = "formName";

	private static final String SYM_MONTHNAMES = "monthNames";

	private static final String SYM_SHORT_MONTHNAMES = "shortMonthNames";

	private static final String SYM_WEEKDAYNAMES = "weekDayNames";

	private static final String SYM_SHORT_WEEKDAYNAMES = "shortWeekDayNames";

	private static final String SYM_FIRSTDAYINWEEK = "firstDayInWeek";

	private static final String SYM_MINDAYSINFIRSTWEEK = "minimalDaysInFirstWeek";

	private static final String SYM_FORMAT = "format";

	private static final String SYM_INCL_WEEK = "includeWeek";

	private static final String SYM_CLEAR_BUTTON_LABEL = "clearButtonLabel";

	private static final String SYM_VALUE = "value";

	private static final String SYM_BUTTONONCLICKHANDLER = "buttonOnclickHandler";

	public abstract String getValue();

	public abstract void setValue(String value);

	public abstract boolean isDisabled();

	public abstract boolean getIncludeWeek();

	public abstract IAsset getIcon();

	public abstract String getImageClass();

	/**
	 * 判断是否自动带有当前日期 true:自动带有当前日期;false:不带有当前日期
	 * 
	 * @return
	 */
	public abstract boolean isCurrentDate();

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
	 * @since 4.1.1
	 */
	public abstract String getTitle();

	/**
	 * Injected.
	 * 
	 * @since 4.0
	 */
	public abstract IScript getScript();

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);

		boolean disabled = isDisabled();
		Locale locale = getPage().getLocale();
		 SimpleDateFormat format =	new SimpleDateFormat(getPattern());

		DateFormatSymbols dfs = format.getDateFormatSymbols();
		Calendar cal = Calendar.getInstance(locale);

		String name = getName();

		String title = getTitle();
		if (title == null)
			title = format.toLocalizedPattern();

		String value = getTranslatedFieldSupport().format(this, getValue());

		Map symbols = new HashMap();

		symbols.put(SYM_NAME, name);
		symbols.put(SYM_FORMAT, format.toPattern());
		symbols.put(SYM_INCL_WEEK, getIncludeWeek() ? Boolean.TRUE
				: Boolean.FALSE);

		symbols.put(SYM_MONTHNAMES, makeStringList(dfs.getMonths(), 0, 12));
		symbols.put(SYM_SHORT_MONTHNAMES, makeStringList(dfs.getShortMonths(),
				0, 12));
		symbols.put(SYM_WEEKDAYNAMES, makeStringList(dfs.getWeekdays(), 1, 8));
		symbols.put(SYM_SHORT_WEEKDAYNAMES, makeStringList(dfs
				.getShortWeekdays(), 1, 8));
		symbols.put(SYM_FIRSTDAYINWEEK,
				new Integer(cal.getFirstDayOfWeek() - 1));
		symbols.put(SYM_MINDAYSINFIRSTWEEK, new Integer(cal
				.getMinimalDaysInFirstWeek()));
		symbols.put(SYM_CLEAR_BUTTON_LABEL, getMessages().getMessage("clear"));
		symbols.put(SYM_FORMNAME, getForm().getName());
		symbols.put(SYM_VALUE, getValue());

		getScript().execute(this, cycle, pageRenderSupport, symbols);

		renderDelegatePrefix(writer, cycle);

		writer.beginEmpty("input");
		writer.attribute("type", "text");
		writer.attribute("name", name);
		if(isCurrentDate() && (value == null || value.length()<1)){
			value = new SimpleDateFormat(getPattern()).format(new Date());
		}
		writer.attribute("value", value);
		writer.attribute("title", title);

		if (disabled)
			writer.attribute("disabled", "disabled");

		renderIdAttribute(writer, cycle);

		renderDelegateAttributes(writer, cycle);

		getTranslatedFieldSupport().renderContributions(this, writer, cycle);
		getValidatableFieldSupport().renderContributions(this, writer, cycle);

		renderInformalParameters(writer, cycle);

		writer.printRaw("&nbsp;");

		if (!disabled) {
			writer.begin("a");
			writer.attribute("href", (String) symbols
					.get(SYM_BUTTONONCLICKHANDLER));
		}

		IAsset icon = getIcon();

		writer.beginEmpty("img");
		writer.attribute("src", icon.buildURL());
		writer.attribute("alt", getMessages().getMessage("alt"));
		writer.attribute("border", 0);
		writer.attribute("class", getImageClass());
		//让图片与input对其的style
		writer.attribute("align", "absbottom");

		if (!disabled)
			writer.end();

		renderDelegateSuffix(writer, cycle);
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#rewindFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String value = cycle.getParameter(getName());

		try {
			String date = (String) getTranslatedFieldSupport().parse(this, value);

			getValidatableFieldSupport().validate(this, writer, cycle, date);

			setValue(date);
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}

	/**
	 * Create a list of quoted strings. The list is suitable for initializing a
	 * JavaScript array.
	 */
	private String makeStringList(String[] a, int offset, int length) {
		StringBuffer b = new StringBuffer();
		for (int i = offset; i < length; i++) {
			// JavaScript is sensitive to some UNICODE characters. So for
			// the sake of simplicity, we just escape everything
			b.append('"');
			char[] ch = a[i].toCharArray();
			for (int j = 0; j < ch.length; j++) {
				if (ch[j] < 128) {
					b.append(ch[j]);
				} else {
					b.append(escape(ch[j]));
				}
			}

			b.append('"');
			if (i < length - 1) {
				b.append(", ");
			}
		}
		return b.toString();

	}

	/**
	 * Create an escaped Unicode character.
	 * 
	 * @param c
	 * @return The unicode character in escaped string form
	 */
	private static String escape(char c) {
		char unescapedChar = c;
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			b.append(Integer.toHexString(unescapedChar & 0x000F).toUpperCase());
			unescapedChar >>>= 4;
		}
		b.append("u\\");
		return b.reverse().toString();
	}

	/**
	 * Injected.
	 */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

	/**
	 * Injected.
	 */
	public abstract TranslatedFieldSupport getTranslatedFieldSupport();

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#isRequired()
	 */
	public boolean isRequired() {
		return getValidatableFieldSupport().isRequired(this);
	}
}
