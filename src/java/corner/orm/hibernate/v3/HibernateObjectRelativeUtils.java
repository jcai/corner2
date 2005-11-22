package corner.orm.hibernate.v3;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.util.PaginationBean;

public class HibernateObjectRelativeUtils extends HibernateDaoSupport implements ObjectRelativeUtils {

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#find(java.lang.String)
	 */
	public List find(String query) throws DataAccessException {
		try {
			return getHibernateTemplate().find(query);
		} catch (HibernateObjectRetrievalFailureException horfe) {
			return new ArrayList();
		}
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#load(java.lang.Class,
	 *      java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(Class<T> refClass, Serializable key) {
		//try {
			return (T) getHibernateTemplate().load(refClass, key);
		//} catch (HibernateObjectRetrievalFailureException horfe) {
		//	return null;
		//}

	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#save(java.lang.Object)
	 */
	public <T> Serializable save(T obj) throws DataAccessException {
		return getHibernateTemplate().save(obj);
	}
	/**
	 * 
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#save(java.lang.Object, java.io.Serializable)
	 */
	public <T> void save(T obj,Serializable id) throws DataAccessException{
		getHibernateTemplate().save(obj,id);
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#saveOrUpdate(java.lang.Object)
	 */
	public <T> void saveOrUpdate(T obj) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#update(java.lang.Object)
	 */
	public <T>void update(T obj) throws DataAccessException {
		getHibernateTemplate().update(obj);
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#delete(java.lang.Object)
	 */
	public<T> void delete(T obj) throws DataAccessException {
		getHibernateTemplate().delete(obj);
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#find(java.lang.String,
	 *      org.cnjug.weed.entity.Pager)
	 */
	public List find(final String query, final PaginationBean pager)
			throws DataAccessException {
		return find(query, pager.getFirst(), pager.getPageSize());

	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#find(java.lang.String, int,
	 *      int)
	 */
	public List find(final String query, final int first, final int maxResults)
			throws DataAccessException {
		try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query q = session.createQuery(query);

					q.setFirstResult(first);
					q.setMaxResults(maxResults);
					return q.list();
				}
			});
		} catch (HibernateObjectRetrievalFailureException horfe) {
			return new ArrayList();
		}
	}

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#count(java.lang.String)
	 */
	public int count(String query) throws DataAccessException {
		List list = getHibernateTemplate().find(query);
		if (list.size() == 1)
			return ((Integer) list.get(0)).intValue();
		else
			return 0;
	}

	

	/**
	 * @see org.cnjug.weed.orm.ObjectRelativeUtils#delete(java.lang.String)
	 */
	public int delete(String query) throws DataAccessException {
		 //getHibernateTemplate().deleteAll().delete(query);
		//TDODO: delete a query
		 return 0;
	}

}
