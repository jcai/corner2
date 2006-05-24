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
public interface IPersistentQueryCallback {

	public DetachedCriteria createDetachedCriteria();
	public void appendDetachedCriteria(DetachedCriteria criteria);
}
