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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;

import corner.orm.hibernate.expression.ExpressionExample;
import corner.orm.tapestry.page.IListablePage;
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.orm.tapestry.table.PersistentBasicTableModel;
import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * 用于处理中间表含有数据时的情况.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 * @param T 根实体
 * @param E 要操作的中间表格的实体.
 * @param F many另一端的实体.
 */
public abstract class AbstractReflectRelativeMidEntityFormPage<T,E,F> extends AbstractRelativeEntityFormPage<T,E>
		implements IPageRooted<T,E>, IListablePage<F>,IPersistentQueriable {
	

	public abstract F getThirdObject();
	public abstract void setThirdObject(F thirdObject);
	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#appendCriteria(Criteria)
	 */
	public void appendCriteria(Criteria criteria) {
		if (this.getQueryEntity() != null)
			criteria.add(ExpressionExample.create(getQueryEntity()).enableLike().excludeZeroes()
					.ignoreCase());
	}

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#createCriteria(Session)
	 */
	public Criteria createCriteria(Session session) {

		return session.createCriteria(this.getThirdObject().getClass());
	}
	/**
	 * 得到列表的source
	 * @return table model
	 */
	public  IBasicTableModel getSource(){
		return new PersistentBasicTableModel(this.getEntityService(),this,this.getRequestCycle().isRewinding());
	}
	
	/**
	 * 得到关联的对象的属性名称。
	 * @return
	 */
	protected  String getRelativePropertyName(){
		return EntityConverter.getClassNameAsPropertyName(this.getRootedObject());
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		BeanUtils.setProperty(getEntity(),getRelativePropertyName(),this.getRootedObject());
		super.saveOrUpdateEntity();
		this.flushHibernate();
	}
}
