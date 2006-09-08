/**
 *
 */
package corner.orm.tapestry.page;

import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.components.IPrimaryKeyConverter;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;

import corner.orm.hibernate.expression.NewExpressionExample;
import corner.orm.tapestry.HibernateConverter;
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.orm.tapestry.table.PersistentBasicTableModel;

/**
 * 抽象的基础页页面。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-24
 * @param <T> 当前操作的实体对象.
 */
public abstract class AbstractEntityListPage<T> extends AbstractEntityPage<T> implements IListablePage<T>,IPersistentQueriable {
	@SuppressWarnings("unchecked")
	public EntityPage<T> getEntityFormPage() {
		return (EntityPage<T>) this.getRequestCycle().getPage(getEntityFormPageStr());
	}
	/**
	 * 得到实体表单页面的名称.
	 * @return 实体表单页面的名称。
	 */
	protected String getEntityFormPageStr(){
		return 
		this.getPageName().substring(0,
				this.getPageName().lastIndexOf("List"))
				+ "Form";
	}
	//------ 查询部分
	

	/**
	 * 响应查询的操作.
	 * @return 当前页
	 * @since 2.0
	 */
	public IPage doQueryEntityAction(){
		this.setQueryEntity(this.getQueryEntity()); //纠正tapestry不能够记录实例化的属性。
		return this;
	}

	//------ 处理含有checkbox的列表。
	/** 记载选中的list* */
	@InitialValue("new java.util.ArrayList()")
	public abstract List<T> getSelectedEntities();
	public abstract void setSelectedEntities(List<T> list);


	public boolean isCheckboxSelected() {
		return this.getSelectedEntities().contains(getEntity());
	}

	public void setCheckboxSelected(boolean bSelected) {
		if (bSelected) {
			this.getSelectedEntities().add(getEntity());
		}
	}
	/**
	 * 提供一组的checkbox供选择.
	 * 批量删除实体.
	 *
	 * @return 当前页.
	 * @since 2.0
	 */
	public IPage doDeleteEntitiesAction(){
		this.getEntityService().deleteEntities(this.getSelectedEntities().toArray());
		return this;
	}

//	 -------------------since 2.0
	/**
	 * 删除一个实体。
	 *
	 * @param entity
	 *            实体对象。
	 * @return 返回页面.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public IPage doDeleteEntityAction(T entity) { // 删除操作
		this.getEntityService().deleteEntities(entity);
		return this;
	}

	/**
	 * 编辑实体操作.
	 *
	 * @param entity
	 *            实体.
	 * @return 返回编辑页面.
	 * @since 2.0
	 */

	public IPage doEditEntityAction(T entity) { // 编辑操作
		EntityPage<T> page = this.getEntityFormPage();
		page.setEntity(entity);
		return page;
	}

	/**
	 * 新增实体操作.
	 *
	 * @return 新增实体操作的页面.
	 * @since 2.0
	 */
	public IPage doNewEntityAction() { // 新增加操作.
		return this.getEntityFormPage();
	}
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

		return session.createCriteria(this.getEntity().getClass());
	}
	/**
	 * 得到列表的source
	 * @return table model
	 */
	public  IBasicTableModel getSource(){
		return new PersistentBasicTableModel(this.getEntityService(),this,this.getRequestCycle().isRewinding());
	}
	/** 得到对主键的Converter* */
	public IPrimaryKeyConverter getConverter() {
		return new HibernateConverter(this.getDataSqueezer());
	}
	//=====  加入直接操作实体的DirectLink连接
	/**
	 * 编辑实体的连接
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doEditEntityAction","parameters=entity"})
	public abstract IComponent getEditEntityLink();
	/**
	 * 增加实体的连接
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doNewEntityAction"})
	public abstract IComponent getNewEntityLink();
	/**
	 * 删除实体的连接
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doDeleteEntityAction","parameters=entity"})
	public abstract IComponent getDeleteEntityLink();

}
