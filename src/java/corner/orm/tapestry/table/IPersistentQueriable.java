/**
 * 
 */
package corner.orm.tapestry.table;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 针对hibernate查询提供的接口服务。
 * @author Jun Tsai
 * @version $Revison$
 * @since 2006-5-24
 */
public interface IPersistentQueriable {
	/**
	 * 创建一个offline的Cirteria
	 * @return criteria detached
	 */
	public DetachedCriteria createDetachedCriteria();
	/**
	 * 对查询进行一个性化的设置。
	 * @param criteria
	 */
	public void appendDetachedCriteria(DetachedCriteria criteria);
}
