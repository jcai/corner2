/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Persist;

import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.page.EntityPage;

/**
 * 有关联关系的页面，通常为了记录根对象。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 * @param T 基础的实体对象。
 * @param E 基础对象所关联的对象。
 */
public interface IPageRooted<T,E> extends IPage,EntityPage<E>{
	/**
	 * 得到基础的实体对象。
	 * @return 基础的实体对象。
	 */
	@Persist("entity")
	public abstract T getRootedObject();
	/**
	 * 设定基础的对象。
	 * @param obj 关联的根对象。
	 */
	public abstract void setRootedObject(T obj);
	
	/**
	 * 得到返回的根对象表单的页面。
	 * @return 根对象的表单页面。
	 */
	public abstract AbstractEntityFormPage<T> getRootFormPage();
	
	
}
