// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: OneSessionPerServletRequestFilter.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-05-26
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

package corner.orm.tapestry.format;

import java.text.Format;
import java.util.Hashtable;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Location;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IBinding;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.binding.AbstractBindingFactory;

import corner.orm.tapestry.translator.ExactDateTranslator;
import corner.orm.tapestry.translator.SimpleDateTranslator;
import corner.orm.tapestry.translator.SimpleTimeTranslator;
import corner.orm.tapestry.translator.TimeTranslator;

/**
 * 对date类型提供格式化输出
 * <p>
 * 与translator中的日期类型对应
 * 使用的时候<span jwcid="@Insert" value="ognl:xx" format="date:xx"/>
 * 
 * 提供的类型有:
 *  date
 *  sdate
 *  time
 *  stime
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class DateBindingFactory extends AbstractBindingFactory {
	
	/**
	 * 
	 * @see org.apache.tapestry.binding.BindingFactory#createBinding(org.apache.tapestry.IComponent, java.lang.String, java.lang.String, org.apache.hivemind.Location)
	 */
	public IBinding createBinding(IComponent root, String bindingDescription,
			String expression, Location location) {
		try {
			Format format = this.createFormat(expression);
			return new DateBinding(bindingDescription, getValueConverter(), location, format);
		} catch (Exception ex) {
			throw new ApplicationRuntimeException(ex.getMessage(), location, ex);
		}
	}

	

	/**
	 * 保存所有format的类.
	 */
	static Hashtable<String, Format> FORMAT_CLAZZ = new Hashtable<String, Format>();
	static {

		/** 
		 * 精确的日期 
		 * yyyy-MM-dd HH:mm:ss
		 */
		FORMAT_CLAZZ.put("date", ExactDateTranslator.DATE_FORMAT);
		/** 
		 * 普通的日期 
		 * yyyy-MM-dd
		 */
		FORMAT_CLAZZ.put("sdate", SimpleDateTranslator.DATE_FORMAT);
		/** 
		 * 简单的时间
		 * HH:mm
		 */
		FORMAT_CLAZZ.put("stime", SimpleTimeTranslator.DATE_FORMAT);
		/** 
		 * 比较精确的时间
		 * HH:mm:ss
		 */
		FORMAT_CLAZZ.put("time", TimeTranslator.DATE_FORMAT);
	}

	/**
	 * 创建一个format
	 * @param expression 给定的format的名称
	 * @return format
	 */
	private Format createFormat(String expression) {
		Defense.notNull(expression, "expression");
		Format pattern = FORMAT_CLAZZ.get(expression);
		Defense.notNull(expression, "未发现定义的format[" + expression + "]");
		return pattern;

	}
}
