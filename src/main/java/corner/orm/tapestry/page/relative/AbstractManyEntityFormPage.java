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
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.orm.tapestry.table.PersistentBasicTableModel;

/**
 * 抽象的many的页面的form页。
 * <p>此form页面的T为当前操作的实体，E为关联实体。
 * @author jcai
 * @version $Revision$
 * @since 2.0.3
 */
public abstract class AbstractManyEntityFormPage<T,E> extends AbstractEntityFormPage<T> implements IPersistentQueriable {

	/**
	 * 得到关联对象。
	 * 此对象用来在对关联对象循环的时候使用的临时变量。
	 * @return 关联的对象
	 */
	public abstract E getRelativeObject();
	public abstract void setRelativeObject(E obj);
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @return table model
	 */
	public  IBasicTableModel getSource(){
		return new PersistentBasicTableModel(this.getEntityService(),this);
	}
	/**
	 * 得到供选择关联的列表，譬如：在Group端增加和User关联的关系，
	 * 此页面应该是一个供 User 选择的列表的选择页面。
	 * @return
	 */
	protected abstract AbstractRelativeSelectionListPage<T,E> getRelativeListPage();

	/**
	 * 新增加一个关联关系的操作。
	 * @param obj 供操作的对象。
	 * @return 操作后返回的页面。
	 */
	public IPage doNewRelativeAction(T obj){
		AbstractRelativeSelectionListPage<T,E> page=this.getRelativeListPage();
		page.setRootedObject(obj);
		return page;
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
}
