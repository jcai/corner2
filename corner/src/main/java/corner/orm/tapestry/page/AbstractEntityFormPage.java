package corner.orm.tapestry.page;

import org.apache.tapestry.IPage;

/**
 * 抽象的entity表单页。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.2
 * @param <T> 当前操作的实体对象.
 */
public abstract class AbstractEntityFormPage<T> extends AbstractEntityPage<T>{
	/**
	 * 得到回显的列表页面。
	 * @return 列表页。
	 * @since 2.0.1
	 */
	protected IPage getEntityListPage(){
		return this.getRequestCycle().getPage(
				this.getPageName().substring(0,
						this.getPageName().lastIndexOf("Form"))
						+ "List");
	}
	/**
	 * 保存实体操作.
	 *
	 *
	 * @return 保存后的返回页面.
	 * @since 2.0
	 */
	public IPage doSaveEntityAction() { // 保存操作。
		saveOrUpdateEntity();
		return getEntityListPage();
	}
	/**
	 * 取消对一个实体的编辑或者新增。
	 *
	 * @return 取消后返回的页面。
	 * @since 2.0
	 */
	public IPage doCancelEntityAction(){
		return this.getEntityListPage();
	}
}
