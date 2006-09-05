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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.QueryParameters;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 提供显示本对象关联对象的集合。
 * 
 * @author Jun Tsai
 * @author Ghost
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

	private boolean isRewinding;

	private IPersistentQueriable callback;

	/**
	 * @deprecated Use {@link #RelativePersistentBasicTableModel(EntityService,T,String,boolean)} instead
	 */
	public RelativePersistentBasicTableModel(EntityService entityService,
			T rootedObj, String relativeProName) {
				this(entityService, rootedObj, relativeProName, false);
	}
	/**
	 * 根据isRewinding来产生一个列表
	 * @param entityService 实体服务类
	 * @param rootedObj 根对象.
	 * @param relativeProName 关联的属性.
	 * @param isRewinding 是否为rewinding
	 */
	public RelativePersistentBasicTableModel(EntityService entityService,
			T rootedObj, String relativeProName, boolean isRewinding) {
		this.rootedObj = rootedObj;
		this.relativeProName = relativeProName;
		this.entityService = entityService;
		this.isRewinding=isRewinding;
	}
	/**
	 * 根据isRewinding来产生一个列表
	 * @param entityService 实体服务类
	 * @param callback 提供查询时候参数的使用.
	 * @param rootedObj 根对象.
	 * @param relativeProName 关联的属性.
	 * @param isRewinding 是否为rewinding
	 */
	public RelativePersistentBasicTableModel(EntityService entityService,
			T rootedObj, String relativeProName, boolean isRewinding,IPersistentQueriable callback) {
		this.rootedObj = rootedObj;
		this.relativeProName = relativeProName;
		this.entityService = entityService;
		this.isRewinding=isRewinding;
		this.callback=callback;
	}

	private Collection getRelativeCollection() {
		return (Collection) BeanUtils.getProperty(rootedObj, relativeProName);
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
			final Collection c = this.getRelativeCollection();
			if (c == null) {
				return 0;
			}
			rows = ((Integer) ((HibernateObjectRelativeUtils) this.entityService
					.getObjectRelativeUtils()).getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query query=createQuery(session,c,"select count(*)",null);
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
		if(isRewinding){
			return null;
		}
		
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
						Query query = createQuery(session,c,null,orderStr);

						query.setFirstResult(nFirst);
						query.setMaxResults(nPageSize);

						return query.iterate();
					}
				}));
	}
	//提供对关联列表的查询
	private Query  createQuery(Session session,Collection c,String selectStr,String orderStr){
		//TODO 考虑缓存，创建尽可能少的对象
		
		
		StringBuffer sb=new StringBuffer();
		if(selectStr!=null){
			sb.append(selectStr).append(" ");
		}
			
		Query query;
		if(callback!=null){
			//========  考虑此处采用Example方式查询.
			Criteria criteria=callback.createCriteria(session);
			
			//附加查询条件
			callback.appendCriteria(criteria);
			String rootEntityName = ((CriteriaImpl) criteria).getEntityOrClassName();
			CriteriaQueryTranslator criteriaQuery = new CriteriaQueryTranslator(
					(SessionFactoryImplementor)session.getSessionFactory(), 
					( CriteriaImpl)criteria, 
					rootEntityName, 
					Criteria.ROOT_ALIAS
				);
			String where = criteriaQuery.getWhereCondition();
			QueryParameters qps=criteriaQuery.getQueryParameters();
			
			/*Example example=NewExpressionExample.create(new Object());
			TypedValue [] tvs=example.getTypedValues(criteria, criteriaQuery);
			example.toSqlString(criteria, criteriaQuery);
			Object [] values=new Object[tvs.length];
			Type [] types=new Type[tvs.length];
			for(int i=0;i<tvs.length;i++){
				values[i]=tvs[i].getValue();
				types[i]=tvs[i].getType();
			}
			String sql=example.toSqlString(criteria, criteriaQuery);
			*/
			if(where != null && where.length()>0){
				sb.append("where ").append(where);
			}
			if(orderStr!=null){
				sb.append(orderStr);
			}
			query = session.createFilter(c,sb.toString()).setParameters(qps.getPositionalParameterValues(),qps.getPositionalParameterTypes());
		}else{
			if(orderStr!=null){
				sb.append(orderStr);
			}
			query = session.createFilter(c,
					sb.toString());
				
		}
		
		return query;
		
	}

}
