package corner.orm.tapestry.page.relative.support;

import org.apache.tapestry.IPage;

/**
 * 对关联对象的操作。
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public interface IRelativeObjectOperator<T, E> {

	/**
	 * 新增加一个关联对象的操作。
	 * 
	 * @param obj 供操作的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	public abstract IPage doNewRelativeAction(T obj, String pageName);

	/**
	 * 编辑一个关联对象的操作。
	 * <p>适用于one-to-many的操作。
	 * @param obj 供操作的对象。
	 * @param e 关联的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	public abstract IPage doEditRelativeEntityAction(T obj, E e, String pageName);

}