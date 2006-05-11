//==============================================================================
//file :       $Id: HibernateObjectRelativeUtils.java,v 1.7 2005/05/13 10:04:50 acai Exp $
//project:     weed
//
//last change:	date:       $Date: 2005/05/13 10:04:50 $
//           	by:         $Author: acai $
//           	revision:   $Revision: 1.7 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group(http://cnjug.dev.java.net).
//license:	Apache License(http://www.apache.org/licenses/LICENSE-2.0)
//==============================================================================

package corner.orm.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import corner.util.PaginationBean;

/**
 * hibernate的O/R Mapping的实�?
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai </a>
 * @version $Revision: 1.7 $
 * @since 2004-12-31
 */

public class HibernateObjectRelativeUtils extends HibernateDaoSupport implements
		ObjectRelativeUtils {
	/**
	 * @see corner.orm.ObjectRelativeUtils#find(java.lang.String)
	 */
	public List find(String query) throws DataAccessException {		
		try {
			return getHibernateTemplate().find(query);
		} catch (HibernateObjectRetrievalFailureException horfe) {
			return new ArrayList();
		}
	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#load(java.lang.Class,
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
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> refClass, Serializable key) {
		//try {
			return (T) getHibernateTemplate().get(refClass, key);
		//} catch (HibernateObjectRetrievalFailureException horfe) {
		//	return null;
		//}

	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#save(java.lang.Object)
	 */
	public <T> Serializable save(T obj) throws DataAccessException {
		return getHibernateTemplate().save(obj);
	}
	/**
	 * 
	 * @see corner.orm.ObjectRelativeUtils#save(java.lang.Object, java.io.Serializable)
	 */
	public <T> void save(T obj,Serializable id) throws DataAccessException{
		getHibernateTemplate().save(obj,id);
	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#saveOrUpdate(java.lang.Object)
	 */
	public <T> void saveOrUpdate(T obj) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#update(java.lang.Object)
	 */
	public <T>void update(T obj) throws DataAccessException {
		getHibernateTemplate().update(obj);
	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#delete(java.lang.Object)
	 */
	public<T> void delete(T obj) throws DataAccessException {
		getHibernateTemplate().delete(obj);
	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#find(java.lang.String,
	 *      corner.entity.Pager)
	 */
	public List find(final String query, final PaginationBean pager)
			throws DataAccessException {
		return find(query, pager.getFirst(), pager.getPageSize());

	}

	/**
	 * @see corner.orm.ObjectRelativeUtils#find(java.lang.String, int,
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
	 * @see corner.orm.ObjectRelativeUtils#find(java.lang.String, int,
	 *      int)
	 */
	public List find(final String query,final Object param, final int first, final int maxResults)
			throws DataAccessException {
		try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query q = session.createQuery(query);
					q.setParameter(0,param);
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
	 * @see corner.orm.ObjectRelativeUtils#count(java.lang.String)
	 */
	public int count(String query) throws DataAccessException {
		List list = getHibernateTemplate().find(query);
		if (list.size() == 1)
			return ((Integer) list.get(0)).intValue();
		else
			return 0;
	}

	

	/**
	 * @see corner.orm.ObjectRelativeUtils#delete(java.lang.String)
	 */
	public int delete(String query) throws DataAccessException {		
		return getHibernateTemplate().delete(query);
	}
	
	
}