// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-10
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

package corner.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.Assert;

/**
 * 提供常用的日期类型的处理工具
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class DateUtils {

	private static final String SIMPLE_DATE_PATTERN_STR = "yyyy-MM-dd";
	
	private static final String NULL_DATE_PATTERN_EXCEPTION_STR = "日期格式不能为空";
	
	private static final String NULL_DATE_EXCEPTION_STR = "被格式化的日期不能为空";
	
	/**
	 * 返回一个"yyyy-MM-dd"格式的formatter
	 * @return {@link SimpleDateFormat}
	 */
	public static final SimpleDateFormat getSimpleDateFormat(){
		return getSimpleDateFormat(SIMPLE_DATE_PATTERN_STR);
	}
	
	/**
	 * 根据给定的pattern返回一个formatter
	 * @param pattern
	 * @return {@link SimpleDateFormat}
	 */
	public static final SimpleDateFormat getSimpleDateFormat(String pattern){
		Assert.notNull(NULL_DATE_PATTERN_EXCEPTION_STR);
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 把传入的日期类型的date，用"yyyy-MM-dd"格式化成字符串
	 * @param date
	 * @return {@link String}
	 */
	public static final String getSimpleStringDate(Date date){
		Assert.notNull(NULL_DATE_EXCEPTION_STR);
		return getSimpleDateFormat().format(date);
	}
}
