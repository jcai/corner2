//==============================================================================
//file :        AbstractManyEntityFormPage.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.table.RelativePersistentBasicTableModel;

/**
 * 抽象的many的页面的form页。
 * <p>此form页面的T为当前操作的实体，E为关联实体。
 * @author jcai
 * @version $Revision$
 * @param <T> 当前操作的实体对象。
 * @param <E> 关联的对象。
 * @since 2.0.3
 */
public abstract class AbstractManyEntityFormPage<T, E> extends AbstractEntityFormPage<T>  implements IRelativeObjectOperatorSupport {

	
	/**
	 * 得到关联对象。
	 * 此对象用来在对关联对象循环的时候使用的临时变量。
	 * @return 关联的对象
	 */
	public abstract E getRelativeObject();
	public abstract void setRelativeObject(E obj);
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @param relativePropertyName 关联的属性名字，通常为复数，譬如：groups,users等。
	 * @return table model
	 */
	public  IBasicTableModel getSource(String relativePropertyName){
		return new RelativePersistentBasicTableModel<T>(this.getEntityService(),this.getEntity(),relativePropertyName);
	}
	/**
	 * 得到供选择关联的列表，譬如：在Group端增加和User关联的关系，
	 * 此页面应该是一个供 User 选择的列表的选择页面。
	 * <p>适用于many-to-many的操作，仅仅适用于增加关联的关系。
	 * @return
	 * @deprecated 将在2.0.8中删除.
	 */
	protected abstract AbstractRelativeSelectionListPage<T,E> getRelativeListPage();

	/**
	 * 新增加一个关联关系的操作。
	 * <p>适用于many-to-many的操作，仅仅是增加关系。
	 * @param obj 供操作的对象。
	 * @return 操作后返回的页面。
	 * @deprecated 将在2.0.8中删除,请使用#{@link #doNewRelativeAction(T, String)}。
	 */
	public IPage doNewRelativeAction(T obj){
		AbstractRelativeSelectionListPage<T,E> page=this.getRelativeListPage();
		page.setRootedObject(obj);
		return page;
	}
	/**
	 * 新增加一个关联对象的操作。
	 * 
	 * @param obj 供操作的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	@SuppressWarnings("unchecked")
	public IPage doNewRelativeAction(T obj,String pageName){
		return this.getRelativeObjectOperator().doNewRelativeAction(obj, pageName);
	}
	/**
	 * 编辑一个关联对象的操作。
	 * <p>适用于one-to-many的操作。
	 * @param obj 供操作的对象。
	 * @param e 关联的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	@SuppressWarnings("unchecked")
	public IPage doEditRelativeEntityAction(T obj,E e,String pageName){
		return this.getRelativeObjectOperator().doEditRelativeEntityAction(obj, e, pageName);
	}
	
	/**
	 * 删除对象之间的关联关系。
	 * @param t 当前的实体对象。
	 * @param e 关联的关系实体对象。
	 */
	protected abstract void deleteRelationship(T t,E e);
	/**
	 * 响应删除关联关系的操作。
	 * @param t 当前的实体对象。
	 * @param e 关联关系实体对象。
	 * @return 删除关联关系后的返回页面。
	 */
	public IPage doDeleteRelativeAction(T t,E e){
		this.deleteRelationship(t,e);
		this.setEntity(t);
		this.flushHibernate();
		return this;
	}
	/**
	 * 删除的关联对象。
	 * <p>通常用于在one-to-many的时候删除many端的对象。
	 * @param e 关联的对象。
	 * @return 当前页面。
	 */
	@SuppressWarnings("unchecked")
	public IPage doDeleteRelativeEntityAction(E e){
		this.getEntityService().deleteEntities(e);
		return this;
	}
	/**
	 * 返回到关联对象的列表页。
	 * @param t 实体对象。
	 * @param listPath 列表页面。
	 * @return 列表页面。
	 */
	@SuppressWarnings("unchecked")
	public IPage doViewRelativeEntityListAction(T t,String listPageName){
		IPageRooted<T,E> page= (IPageRooted<T,E>) this.getRequestCycle().getPage(listPageName);
		page.setRootedObject(t);
		return page;
	}
}
