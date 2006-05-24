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

	private IPersistentQueryCallback callback;

	private int rows = -1;

	public PersistentBasicTableModel(EntityService entityService,
			IPersistentQueryCallback callback) {
		Assert.notNull(callback);
		Assert.notNull(entityService);
		this.entityService = entityService;
		this.callback = callback;
	}

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
