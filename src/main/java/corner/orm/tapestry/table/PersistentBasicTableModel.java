/**
 *
 */
package corner.orm.tapestry.table;

import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;

/**
 * 基于IPersistent的的table模型。
 *
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-24
 */
public class PersistentBasicTableModel implements IBasicTableModel {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(PersistentBasicTableModel.class);

	// 实体服务类
	private EntityService entityService;

	private IPersistentQueriable callback;
	/** 对行的数目进行了缓存**/
	private int rows = -1;

	private boolean isRewinding=false;

	/**
	 * 根据EntityService还有一个可查询的回掉类来构造一个table model.
	 * @param entityService 实体服务类.
	 * @param callback 查询回掉类.
	 */
	public PersistentBasicTableModel(EntityService entityService,
			IPersistentQueriable callback) {
		Assert.notNull(callback);
		Assert.notNull(entityService);
		this.entityService = entityService;
		this.callback = callback;
	}
	/**
	 * 根据EntityService还有一个可查询的回掉类来构造一个table model.
	 * @param entityService 实体服务类.
	 * @param callback 查询回掉类.
	 */
	public PersistentBasicTableModel(EntityService entityService,
			IPersistentQueriable callback,boolean isRewinding) {
		Assert.notNull(callback);
		Assert.notNull(entityService);
		this.entityService = entityService;
		this.callback = callback;
		this.isRewinding=isRewinding;
	}

	/**
	 *
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getRowCount()
	 */
	public int getRowCount() {
		if(isRewinding){
			return rows;
		}
		if (rows == -1) {
			rows=((Integer) ((HibernateObjectRelativeUtils) this.entityService
					.getObjectRelativeUtils()).getHibernateTemplate()
					.execute(new HibernateCallback(){

						public Object doInHibernate(Session session) throws HibernateException, SQLException {
							Criteria criteria=callback.createCriteria(session);
							criteria.setProjection(Projections.rowCount());
							callback.appendCriteria(criteria);

							return criteria.list().iterator().next();
						}})).intValue();

		}
		return rows;

	}
	/**
	 *
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getCurrentPageRows(int, int, org.apache.tapestry.contrib.table.model.ITableColumn, boolean)
	 */
	public Iterator getCurrentPageRows(final int nFirst, final int nPageSize,
			final ITableColumn column, final boolean sort) {
		if(isRewinding){
			if (logger.isDebugEnabled()) {
				logger
						.debug("is rewinding ,return false;");
			}

			return null;
		}
			return((Iterator) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate()
				.execute(new HibernateCallback(){

					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria=callback.createCriteria(session);
						callback.appendOrder(criteria);//增加排序 since 2.2.1
						callback.appendCriteria(criteria);

						if (column != null) {

							criteria.addOrder(sort ? Order.asc(column.getColumnName()) : Order
									.desc(column.getColumnName()));
						}

						criteria.setFirstResult(nFirst);
						criteria.setMaxResults(nPageSize);

						return criteria.list().iterator();
					}}));

	}

}
