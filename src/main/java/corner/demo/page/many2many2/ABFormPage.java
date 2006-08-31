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

package corner.demo.page.many2many2;

import java.util.List;

import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import corner.orm.hibernate.expression.ExpressionExample;
import corner.orm.tapestry.page.relative.ReflectRelativeMidEntityFormPage;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public abstract class ABFormPage extends ReflectRelativeMidEntityFormPage {
	/**
	 * 取得TextAreaBox的返回value
	 * @return
	 */
	public abstract Object getValue();	
	/**
	 * 给TextAreaBox提供DataSource
	 * <p>给TextAreaBox提供DataSource,该DataSource必须实现了IAutocompleteModel接口</p>
	 * @return
	 */
	public IAutocompleteModel getModel(){
		Users users = new Users();
		List<User> userList = users.getUsers();
		return new DoubleSearchAutocompleteModel(userList,"id","userName","cnName");
	}
	/**
	 * @see corner.orm.tapestry.page.relative.AbstractReflectRelativeMidEntityFormPage#appendCriteria(org.hibernate.Criteria)
	 */
	@Override
	public void appendCriteria(Criteria criteria) {
		if (this.getQueryEntity() != null)
			criteria.add(ExpressionExample.create(getQueryEntity()).enableLike().excludeZeroes()
					.ignoreCase()).add(Restrictions.eq("",""));
	}
	/**
	 * @see corner.orm.tapestry.page.relative.AbstractReflectRelativeMidEntityFormPage#createCriteria(org.hibernate.Session)
	 */
	@Override
	public Criteria createCriteria(Session session) {
		// TODO Auto-generated method stub
		return super.createCriteria(session);
	}
	
}
