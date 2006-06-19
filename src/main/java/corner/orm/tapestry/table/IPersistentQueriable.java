/**
 *
 */
package corner.orm.tapestry.table;
import org.hibernate.Criteria;
import org.hibernate.Session;


/**
 * 针对hibernate查询提供的接口服务。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-24
 */
public interface IPersistentQueriable {
	/**
	 * 创建一个offline的Cirteria
	 * @return criteria detached
	 */
	public Criteria createCriteria(Session session);
	/**
	 * 对查询进行一个性化的设置。
	 * @param criteria
	 */
	public void appendCriteria(Criteria criteria);
}
