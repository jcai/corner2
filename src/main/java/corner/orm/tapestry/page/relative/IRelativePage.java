/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

/**
 * 有关联关系的页面，通常为了记录根对象。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public interface IRelativePage<T> {
	/**
	 * 得到基础的实体对象。
	 * @return 基础的实体对象。
	 */
	public abstract T getRootedObject();
	/**
	 * 设定基础的对象。
	 * @param obj 关联的根对象。
	 */
	public abstract void setRootedObject(T obj);
}
