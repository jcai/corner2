//==============================================================================
//file :        CodeFilter.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.page.generic;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import corner.orm.tapestry.component.select.DefaultSelectFilter;

/**
 * 对code的查询
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class CodeFilter extends DefaultSelectFilter {

	

	/**
	 * @see corner.orm.tapestry.component.select.AbstractSelectFilter#appendCriteria(org.hibernate.Criteria, java.lang.String)
	 */
	@Override
	protected void appendCriteria(Criteria criteria, String match) {
		if(match==null||match.trim().length()==0){
			return;
		}
		match=match.trim();
		criteria.add(Restrictions.or(Restrictions.like("name", match+"%"),Restrictions.like("pinyin", match+"%")));
		
	}

	/**
	 * @see corner.orm.tapestry.component.select.AbstractSelectFilter#getLabelField()
	 */
	@Override
	public String getLabelField() {
		return "name";
	}

}
