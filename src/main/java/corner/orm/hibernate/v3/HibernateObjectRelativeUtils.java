// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-11-22
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

/**
 * 针对hibernate3.0的主键生成策略.
 * <p>生成时间格式的主键值.譬如:20050203120312
 * @author Jun Tsai
 *
 */
public class HibernateObjectRelativeUtils extends HibernateDaoSupport implements ObjectRelativeUtils {
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
		return (T) this.getHibernateTemplate().load(refClass,key);

	}
	/**
	 * @see corner.orm.ObjectRelativeUtils#load(java.lang.Class,
	 *      java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> refClass, Serializable key) {
		return (T) this.getHibernateTemplate().get(refClass,key);

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
	public List find(final String query, final Object param,final int first, final int maxResults)
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
	public int delete(final String query) throws DataAccessException {
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return new Integer(session.createQuery(query).executeUpdate());
			}})).intValue();
		
		 
	}
	/**
	 * @see corner.orm.ObjectRelativeUtils#delete(java.lang.String)
	 */
	public int delete(final String query,final Object param) throws DataAccessException {
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return new Integer(session.createQuery(query).setParameter(0,param).executeUpdate());
			}})).intValue();
		
		 
	}
}
