/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: AbstractSelectModel.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-27
 */
package corner.orm.tapestry.component.prototype;

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
	public void appendProjection(Criteria criteria){
		criteria.setProjection(Projections.projectionList().add(
				Projections.property(INDEX_CODE_FIELD)).add(
				Projections.property(ABC_FIELD)).add(
				Projections.property(CHN_FIELD)));
	}

	protected String escapeHtml(Object v) {
		return v == null ? "" : StringEscapeUtils.escapeHtml(v.toString());
	}
}
