/**
 * 
 */
package corner.orm.tapestry.page;

import java.util.List;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InitialValue;

/**
 * 抽象的基础页页面。
 * @author Jun Tsai
 * @version $Revison$
 * @since 2006-5-24
 */
public abstract class AbstractEntityListPage<T> extends AbstractEntityPage<T> {
	/**
	 * 查询的实体.
	 * 
	 * @return 查询实体.
	 */
	public abstract T getQueryEntity();

	public abstract void setQueryEntity(T obj);
	@SuppressWarnings("unchecked")
	public EntityPage<T> getEntityFormPage() {
		return (EntityPage<T>) this.getRequestCycle().getPage(
				this.getPageName().substring(0,
						this.getPageName().lastIndexOf("List"))
						+ "Form");
	}
	/** 记载选中的list* */
	@InitialValue("new java.util.ArrayList()")
	public abstract List<T> getSelectedEntities();
	public abstract void setSelectedEntities(List<T> list);
	
	
	public boolean getCheckboxSelected() {
		return this.getSelectedEntities().contains(getEntity());
	}

	public void setCheckboxSelected(boolean bSelected) {
		if (bSelected) {
			this.getSelectedEntities().add(getEntity());
		}
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
	 * 新增尸体操作.
	 * 
	 * @return 新增实体操作的页面.
	 * @since 2.0
	 */
	public IPage doNewEntityAction() { // 新增加操作.
		return this.getEntityFormPage();
	}
	/**
	 * 提供一组的checkbox供选择.
	 * 批量删除实体.
	 * 
	 * @return 当前页.
	 */
	public IPage doDeleteEntitiesAction(){
		this.getEntityService().deleteEntities(this.getSelectedEntities());
		return this;
	}
	/**
	 * 响应查询的操作.
	 * @return 当前页
	 */
	public IPage doQueryEntityAction(){
		return this;
	}

}
