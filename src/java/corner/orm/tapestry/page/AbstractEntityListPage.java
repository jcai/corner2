/**
 * 
 */
package corner.orm.tapestry.page;

import org.apache.tapestry.IPage;

/**
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

}
