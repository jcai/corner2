/**
 * 
 */
package corner.orm.tapestry.table;

import java.util.Iterator;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.util.Assert;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;

/**
 * 基于IPersistent的的table模型。
 * 
 * @author Jun Tsai
 * @version $Revison$
 * @since 2006-5-24
 */
public class PersistentBasicTableModel implements IBasicTableModel {
	// 实体服务类
	private EntityService entityService;

	private IPersistentQueriable callback;
	/** 对行的数目进行了缓存**/
	private int rows = -1;
	
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
	 * 
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getRowCount()
	 */
	public int getRowCount() {
		if (rows == -1) {
			DetachedCriteria criteria = this.callback.createDetachedCriteria();
			criteria.setProjection(Projections.rowCount());

			callback.appendDetachedCriteria(criteria);
			rows = ((Integer) ((HibernateObjectRelativeUtils) this.entityService
					.getObjectRelativeUtils()).getHibernateTemplate()
					.findByCriteria(criteria).iterator().next()).intValue();
		} 
		return rows;
		
	}
	/**
	 * 
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getCurrentPageRows(int, int, org.apache.tapestry.contrib.table.model.ITableColumn, boolean)
	 */
	public Iterator getCurrentPageRows(final int nFirst, final int nPageSize,
			final ITableColumn column, final boolean sort) {
		DetachedCriteria criteria = this.callback.createDetachedCriteria();
		callback.appendDetachedCriteria(criteria);

		if (column != null) {

			criteria.addOrder(sort ? Order.asc(column.getColumnName()) : Order
					.desc(column.getColumnName()));
		}

		return ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate()
				.findByCriteria(criteria, nFirst, nPageSize).iterator();
	}

}
