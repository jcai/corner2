/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.table;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 提供显示本对象关联对象的集合。
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.4
 */
public class RelativePersistentBasicTableModel<T> implements IBasicTableModel {
	/**
	 * 关联属性名称。
	 */
	private String relativeProName;

	/**
	 * 基对象。
	 */
	private T rootedObj;

	private int rows = -1;

	private EntityService entityService;

	public RelativePersistentBasicTableModel(EntityService entityService,
			T rootedObj, String relativeProName) {
		this.rootedObj = rootedObj;
		this.relativeProName = relativeProName;
		this.entityService = entityService;
	}

	private Collection getRelativeCollection() {
		return (Collection) BeanUtils.getProperty(rootedObj, relativeProName);
	}

	/**
	 * 
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getRowCount()
	 */
	public int getRowCount() {

		if (rows == -1) {
			final Collection c = this.getRelativeCollection();
			if (c == null) {
				return 0;
			}
			rows = ((Integer) ((HibernateObjectRelativeUtils) this.entityService
					.getObjectRelativeUtils()).getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query = session.createFilter(c,
									"select count(*)");
							return query.iterate().next();
						}
					})).intValue();

		}
		return rows;

	}

	/**
	 * 
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getCurrentPageRows(int,
	 *      int, org.apache.tapestry.contrib.table.model.ITableColumn, boolean)
	 */
	public Iterator getCurrentPageRows(final int nFirst, final int nPageSize,
			final ITableColumn column, final boolean sort) {
		final Collection c = this.getRelativeCollection();
		if (c == null) {
			return Collections.EMPTY_LIST.iterator();
		}

		return ((Iterator) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String orderStr = "";

						if (column != null) {

							orderStr = "order by " + column.getColumnName()
									+ (sort ? " " : " desc");
						}
						Query query = session.createFilter(
								getRelativeCollection(), orderStr);

						query.setFirstResult(nFirst);
						query.setMaxResults(nPageSize);

						return query.iterate();
					}
				}));
	}

}
