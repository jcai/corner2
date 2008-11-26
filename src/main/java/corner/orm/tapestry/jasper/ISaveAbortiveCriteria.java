package corner.orm.tapestry.jasper;

import org.hibernate.impl.CriteriaImpl;

/**
 * 可以获取,或者设置未设置session的CriteriaImpl
 * JasperEntityLinkService 用来获得打印发起页面的Criteria
 * 然后设置目标页面用来做查询操作的Criteria,目标页面使用该Criteria进行对记录进行筛选.
 * 
 * @author <a href="mailto:wlh@bjmaxinfo.com">wlh</a>
 * @version $Revision$
 * @since 2.5.1
 */
public interface ISaveAbortiveCriteria {
	
	/**
	 * 获得一个未设置session的Criteria
	 * @return
	 */
	public CriteriaImpl getAbortiveCriteria();
	
	/**
	 * 设置一个未设置session的Criteria
	 * @param criteria
	 */
	public void setAbortiveCriteria(CriteriaImpl criteria);
}
