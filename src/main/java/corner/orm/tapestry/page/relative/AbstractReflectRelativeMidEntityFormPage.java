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

import org.apache.tapestry.IPage;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;

import corner.orm.hibernate.expression.NewExpressionExample;
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
			criteria.add(NewExpressionExample.create(getQueryEntity()).enableLike().excludeZeroes()
					.ignoreCase());
	}

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#createCriteria(Session)
	 */
	public Criteria createCriteria(Session session) {
		if(this.getThirdObject()==null){
			throw new IllegalStateException("many 端的实体对象为空,清定义 thirdObject！");
		}
		return session.createCriteria(this.getThirdObject().getClass());
	}
	/**
	 * 响应查询的操作.
	 * @return 当前页
	 * @since 2.0
	 */
	public IPage doQueryEntityAction(){
		this.setQueryEntity(this.getQueryEntity()); //纠正tapestry不能够记录实例化的属性。
		return this;
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
	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#appendOrder(org.hibernate.Criteria)
	 */
	public void appendOrder(Criteria criteria) {
		//do nothing just must implements
	}
}
