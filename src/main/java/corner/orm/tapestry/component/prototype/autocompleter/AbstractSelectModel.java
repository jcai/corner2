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

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import corner.util.StringUtils;

/**
 * 抽象的选择model，提供一些公用的方法
 * <p>
 * 包含根据用户输入的字符类型选择字段进行查询的intelligenceAppendCriteria方法
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractSelectModel implements ISelectModel {
	
	/**
	 * 智能查询方法-根据用户的输入字符的Unicode来判断字符类型，从而选择不同的字段进行查询
	 * 
	 * @param criteria
	 * @param match
	 */
	public static void intelligenceAppendCriteria(Criteria criteria,
			String match) {
		if (match == null || match.trim().length() == 0) {
			return;
		} else {
			String matchField = null;
			if (StringUtils.comparedCharacter(match, ABC_STR)) {// 如果是拼音检索，需要去掉'#'
				matchField = ABC_FIELD;
			} else if (StringUtils.comparedCharacter(match, CHN_STR)) {
				matchField = CHN_FIELD;
			} else if (StringUtils.comparedCharacter(match, INDEX_CODE_STR)) {
				matchField = INDEX_CODE_FIELD;

			} else if (StringUtils.comparedCharacter(match, NUM_STR)) {
				matchField = NUM_FIELD;
			}
			if (matchField != null)
				criteria.add(Restrictions.like(matchField, match.trim() + "%"));
		}

	}
	
	/**
	 * 定义需要检索的字段
	 * @param criteria
	 */
	protected void appendProjection(Criteria criteria){
		criteria.setProjection(Projections.projectionList().add(
				Projections.property(INDEX_CODE_FIELD)).add(
				Projections.property(ABC_FIELD)).add(
				Projections.property(CHN_FIELD)));
	}

	protected String escapeHtml(Object v) {
		return v == null ? "" : StringEscapeUtils.escapeHtml(v.toString());
	}
}
