//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.util.PaginationBean;

/**
 * 
 * Entity Service.
 * <p>
 * 提供了对实体的基本操作. 譬如:增加,删除,修改等.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-2
 */
public class EntityService {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityService.class);

	/** 对象关系utils* */
	protected ObjectRelativeUtils oru;
	

	/**
	 * 设定O/R M的util类.
	 * 
	 * @param oru
	 *            O/R M的util类.
	 */
	public void setObjectRelativeUtils(ObjectRelativeUtils oru) {
		this.oru = oru;
	}

	/**
	 * 得到 O/R M的util类.
	 * 
	 * @return O/R M的util类.
	 */
	public ObjectRelativeUtils getObjectRelativeUtils() {
		return oru;
	}
	/**
	 * 得到可执行的hibernate的模板类.
	 * @return hibernate模板类.
	 */
	public HibernateTemplate getHibernateTemplate(){
		return ((HibernateDaoSupport) oru).getHibernateTemplate();
	}

	/**
	 * 保存一个实体
	 * 
	 * @param <T>
	 *            实体
	 * @param entity
	 *            待保存的实体
	 * @return 保存后的实体.
	 */
	public <T> Serializable saveEntity(T entity) {
		return oru.save(entity);
	}

	/**
	 * 保存或者更新实体.
	 * 
	 * @param <T>
	 *            实体.
	 * @param entity
	 *            待保存或更新实体.
	 */
	public <T> void saveOrUpdateEntity(T entity) {
		oru.saveOrUpdate(entity);
	}

	/**
	 * 更新一个实体.
	 * 
	 * @param <T>
	 *            实体.
	 * @param entity
	 *            待更新的实体.
	 */
	public <T> void updateEntity(T entity) {
		oru.update(entity);
	}

	/**
	 * 装载一个实体.
	 * 
	 * @param <T>
	 *            实体.
	 * @param clazz
	 *            装载实体的类.
	 * @param keyValue
	 *            主健值.
	 * @return 持久化的实体.
	 */
	public <T> T loadEntity(Class<T> clazz, Serializable keyValue) {
		return oru.load(clazz, keyValue);
	}

	/**
	 * 查询一个实体.
	 * 
	 * @param <T>
	 *            实体
	 * @param clazz
	 *            实体类.
	 * @param keyValue
	 *            主健值.
	 * @return 实体,如果未找到,返回null.
	 * @since 2.0
	 */
	public <T> T getEntity(Class<T> clazz, Serializable keyValue) {
		return oru.get(clazz, keyValue);
	}

	/**
	 * 批量删除实体.
	 * 
	 * @param <T>
	 *            实体.
	 * @param ts
	 *            实体数组.
	 */
	public <T> void deleteEntities(T... ts) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("deleteEntities(List),delete entity list size [" + ts.length + "]"); //$NON-NLS-1$
		}

		for (T entity : ts) {
			try {

				oru.delete(entity);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				// donoting
			}
		}
	}

	/**
	 * 通过给定的实体ID来删除实体.
	 * 
	 * @param <T>
	 *            实体.
	 * @param clazz
	 *            待删除的实体.
	 * @param keyValue
	 *            主键值.
	 */
	public <T> void deleteEntityById(Class<T> clazz, Serializable keyValue) {
		T entity = this.getEntity(clazz, keyValue);
		if (entity != null) {
			oru.delete(entity);
		}
	}

	/**
	 * 通过给定的类的名称和主键值来删除实体.
	 * 
	 * @param clazzName
	 *            类名.
	 * @param key
	 *            主键值.
	 */
	@SuppressWarnings("unchecked")
	public void deleteEntityById(String clazzName, Serializable key) {
		try {
			Class clazz = Class.forName(clazzName);
			this.deleteEntityById(clazz, key);
		} catch (ClassNotFoundException e) {
			// do noting
		}

	}

	/**
	 * 通过给定的类的名称和主键值来得到实体.
	 * 
	 * @param clazzname
	 *            实体类的名称.
	 * @param key
	 *            主键值.
	 * @return 实体,当给定的类未发现时,返回null.
	 */
	@SuppressWarnings("unchecked")
	public Object getEntity(String clazzname, Serializable key) {
		Class clazz;
		try {
			clazz = Class.forName(clazzname);
			return this.getEntity(clazz, key);
		} catch (ClassNotFoundException e) {
			// do nothing
			logger.warn(e.getMessage());
			return null;
		}

	}

	/**
	 * 通过给定的类以及分页用的bean来得到list。
	 * 
	 * @param <T>
	 *            需要查找的类。
	 * @param clazz
	 *            类。
	 * @param pb
	 *            分页的bean。
	 * @return 列表.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(Class<T> clazz, PaginationBean pb) {
		return oru.find("from " + clazz.getName(), pb);
	}

	/**
	 * 判断一个实体是否是persist状态。
	 * 
	 * @param entity
	 *            待处理的实体。
	 * @return 是否持久化。
	 * @since 2.0.3
	 */
	public boolean isPersistent(final Object entity) {
		if (entity == null) {
			return false;
		}
		return ((Boolean) ((HibernateObjectRelativeUtils) getObjectRelativeUtils())
				.getHibernateTemplate().execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						final ClassMetadata classMetadata = session
								.getSessionFactory().getClassMetadata(
										getEntityClass(entity));
						return classMetadata != null
								&& classMetadata.getIdentifier(entity,
										EntityMode.POJO) != null;

					}
				})).booleanValue();
	}

	/**
	 * 得到持久化类的名称。
	 * 
	 * @param entity
	 *            待处理的持久化类。
	 * @return 持久化类的名称。
	 * @since 2.0.3
	 */
	public static Class getEntityClass(Object entity) {
		if (entity.getClass().getName().contains("CGLIB")) {
			return entity.getClass().getSuperclass();
		}
		return entity.getClass();
	}

	/**
	 * 得到一个持久化类的主建值。
	 * 
	 * @param object
	 *            持久化实例。
	 * @return 主建值。
	 * @since 2.0.3
	 */
	public Serializable getIdentifier(final Object object) {
		return (Serializable) ((HibernateObjectRelativeUtils) getObjectRelativeUtils())
				.getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.getIdentifier(object);

					}
				});

	}

	/**
	 * 找到所有的实体,通常用来做测试使用.
	 * 
	 * @param clazz
	 *            实体的类名.
	 * @return 实体的列表
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz) {
		return ((HibernateObjectRelativeUtils) getObjectRelativeUtils())
				.getHibernateTemplate().find("from " + clazz.getName());

	}

	/**
	 * 使用HQL实现对关联实体的批量删除
	 * 
	 * example: 目标:删除所有id属性为1的实体 A a = new A(); a.setId("1");
	 * this.doDeleteBatchRelativeEntityAction(A.class,"id",a);
	 * 此时将删除所有DB中id属性为1的A记录
	 * 
	 * @param clazz
	 *            被删除的实体Class
	 * @param propertyName
	 *            与被删除的实体关联的属性名称
	 * @param entity
	 *            与被删除实体关联的实体
	 * @return 被删除的记录数量
	 */
	public <T> int doDeleteBatchRelativeEntityAction(final Class clazz,
			final String propertyName, final T entity) {
		if (entity != null && isPersistent(entity)) {
			return ((Integer) ((HibernateObjectRelativeUtils) this
					.getObjectRelativeUtils()).getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							StringBuffer buffer = new StringBuffer("delete ");
							buffer.append(clazz.getName());
							buffer.append(" clazz ");
							buffer.append(" where clazz.");
							buffer.append(propertyName);
							buffer.append("=:");
							buffer.append(propertyName);
							return session.createQuery(buffer.toString())
									.setEntity(propertyName, entity)
									.executeUpdate();
						}
					})).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 获得一个组结果，带条件的
	 * 
	 * @param clazz
	 *            类
	 * @param criterion
	 *            条件
	 * @return
	 */
	public List getExistRelativeList(final Class clazz,
			final Criterion criterion) {
		return this.getExistRelativeList(clazz.getName(), criterion);
	}

	/**
	 * 获得一个组结果，带条件的
	 * 
	 * @param clazzName
	 *            类名
	 * @param criterion
	 *            条件
	 * @return
	 */
	public List getExistRelativeList(final String clazzName,
			final Criterion criterion) {
		return (List) ((HibernateObjectRelativeUtils) this
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazzName);

						criteria.add(criterion);

						return criteria.list();
					}
				});
	}

	/**
	 * 获得一个组结果的个数，带条件的
	 * 
	 * @param clazz
	 *            类
	 * @param criterion
	 *            条件
	 * @return
	 */
	public Integer getExistRelativeRowCount(final Class clazz,
			final Criterion criterion) {
		return this.getExistRelativeRowCount(clazz.getName(), criterion);
	}

	/**
	 * 获得一个组结果的个数，带条件的
	 * 
	 * @param clazzName
	 *            类名
	 * @param criterion
	 *            条件
	 * @return
	 */
	public Integer getExistRelativeRowCount(final String clazzName,
			final Criterion criterion) {
		return (Integer) ((HibernateObjectRelativeUtils) this
				.getObjectRelativeUtils()).getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazzName);

						criteria.add(criterion);
						criteria.setProjection(Projections.rowCount());

						return criteria.list().iterator().next();
					}
				});
	}
	//---------- Hibernate Template 的代理方法

	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object)
	 */
	public int bulkUpdate(String queryString, Object value) throws DataAccessException {
		return getHibernateTemplate().bulkUpdate(queryString, value);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object[])
	 */
	public int bulkUpdate(String queryString, Object[] values) throws DataAccessException {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String)
	 */
	public int bulkUpdate(String queryString) throws DataAccessException {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

	/**
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#clear()
	 */
	public void clear() throws DataAccessException {
		getHibernateTemplate().clear();
	}

	/**
	 * @param it
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#closeIterator(java.util.Iterator)
	 */
	public void closeIterator(Iterator it) throws DataAccessException {
		getHibernateTemplate().closeIterator(it);
	}

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#contains(java.lang.Object)
	 */
	public boolean contains(Object entity) throws DataAccessException {
		return getHibernateTemplate().contains(entity);
	}

	/**
	 * @param ex
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#convertHibernateAccessException(org.hibernate.HibernateException)
	 */
	public DataAccessException convertHibernateAccessException(HibernateException ex) {
		return getHibernateTemplate().convertHibernateAccessException(ex);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object, org.hibernate.LockMode)
	 */
	public void delete(Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().delete(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object)
	 */
	public void delete(Object entity) throws DataAccessException {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#deleteAll(java.util.Collection)
	 */
	public void deleteAll(Collection entities) throws DataAccessException {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @param filterName
	 * @return
	 * @throws IllegalStateException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#enableFilter(java.lang.String)
	 */
	public Filter enableFilter(String filterName) throws IllegalStateException {
		return getHibernateTemplate().enableFilter(filterName);
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return getHibernateTemplate().equals(obj);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#evict(java.lang.Object)
	 */
	public void evict(Object entity) throws DataAccessException {
		getHibernateTemplate().evict(entity);
	}

	/**
	 * @param action
	 * @param exposeNativeSession
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#execute(org.springframework.orm.hibernate3.HibernateCallback, boolean)
	 */
	public Object execute(HibernateCallback action, boolean exposeNativeSession) throws DataAccessException {
		return getHibernateTemplate().execute(action, exposeNativeSession);
	}

	/**
	 * @param action
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#execute(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object execute(HibernateCallback action) throws DataAccessException {
		return getHibernateTemplate().execute(action);
	}

	/**
	 * @param action
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#executeFind(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public List executeFind(HibernateCallback action) throws DataAccessException {
		return getHibernateTemplate().executeFind(action);
	}

	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object)
	 */
	public List find(String queryString, Object value) throws DataAccessException {
		return getHibernateTemplate().find(queryString, value);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object[])
	 */
	public List find(String queryString, Object[] values) throws DataAccessException {
		return getHibernateTemplate().find(queryString, values);
	}

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String)
	 */
	public List find(String queryString) throws DataAccessException {
		return getHibernateTemplate().find(queryString);
	}

	/**
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws DataAccessException {
		return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}

	/**
	 * @param criteria
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria) throws DataAccessException {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @param exampleEntity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object, int, int)
	 */
	public List findByExample(Object exampleEntity, int firstResult, int maxResults) throws DataAccessException {
		return getHibernateTemplate().findByExample(exampleEntity, firstResult, maxResults);
	}

	/**
	 * @param exampleEntity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object)
	 */
	public List findByExample(Object exampleEntity) throws DataAccessException {
		return getHibernateTemplate().findByExample(exampleEntity);
	}

	/**
	 * @param queryString
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public List findByNamedParam(String queryString, String paramName, Object value) throws DataAccessException {
		return getHibernateTemplate().findByNamedParam(queryString, paramName, value);
	}

	/**
	 * @param queryString
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	}

	/**
	 * @param queryName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object)
	 */
	public List findByNamedQuery(String queryName, Object value) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	/**
	 * @param queryName
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	public List findByNamedQuery(String queryName, Object[] values) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	/**
	 * @param queryName
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String)
	 */
	public List findByNamedQuery(String queryName) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	/**
	 * @param queryName
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws DataAccessException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
	}

	/**
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) throws DataAccessException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
	}

	/**
	 * @param queryName
	 * @param valueBean
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndValueBean(java.lang.String, java.lang.Object)
	 */
	public List findByNamedQueryAndValueBean(String queryName, Object valueBean) throws DataAccessException {
		return getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
	}

	/**
	 * @param queryString
	 * @param valueBean
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByValueBean(java.lang.String, java.lang.Object)
	 */
	public List findByValueBean(String queryString, Object valueBean) throws DataAccessException {
		return getHibernateTemplate().findByValueBean(queryString, valueBean);
	}

	/**
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#flush()
	 */
	public void flush() throws DataAccessException {
		getHibernateTemplate().flush();
	}

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(Class entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		return getHibernateTemplate().get(entityClass, id, lockMode);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class entityClass, Serializable id) throws DataAccessException {
		return getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
		return getHibernateTemplate().get(entityName, id, lockMode);
	}

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable)
	 */
	public Object get(String entityName, Serializable id) throws DataAccessException {
		return getHibernateTemplate().get(entityName, id);
	}

	/**
	 * @return
	 * @throws IllegalStateException
	 * @throws BeansException
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#getEntityInterceptor()
	 */
	public Interceptor getEntityInterceptor() throws IllegalStateException, BeansException {
		return getHibernateTemplate().getEntityInterceptor();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#getFetchSize()
	 */
	public int getFetchSize() {
		return getHibernateTemplate().getFetchSize();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#getFilterNames()
	 */
	public String[] getFilterNames() {
		return getHibernateTemplate().getFilterNames();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#getFlushMode()
	 */
	public int getFlushMode() {
		return getHibernateTemplate().getFlushMode();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#getJdbcExceptionTranslator()
	 */
	public SQLExceptionTranslator getJdbcExceptionTranslator() {
		return getHibernateTemplate().getJdbcExceptionTranslator();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#getMaxResults()
	 */
	public int getMaxResults() {
		return getHibernateTemplate().getMaxResults();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#getQueryCacheRegion()
	 */
	public String getQueryCacheRegion() {
		return getHibernateTemplate().getQueryCacheRegion();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		return getHibernateTemplate().getSessionFactory();
	}

	/**
	 * @param proxy
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) throws DataAccessException {
		getHibernateTemplate().initialize(proxy);
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#isAllowCreate()
	 */
	public boolean isAllowCreate() {
		return getHibernateTemplate().isAllowCreate();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#isAlwaysUseNewSession()
	 */
	public boolean isAlwaysUseNewSession() {
		return getHibernateTemplate().isAlwaysUseNewSession();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#isCacheQueries()
	 */
	public boolean isCacheQueries() {
		return getHibernateTemplate().isCacheQueries();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#isCheckWriteOperations()
	 */
	public boolean isCheckWriteOperations() {
		return getHibernateTemplate().isCheckWriteOperations();
	}

	/**
	 * @return
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#isExposeNativeSession()
	 */
	public boolean isExposeNativeSession() {
		return getHibernateTemplate().isExposeNativeSession();
	}

	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object)
	 */
	public Iterator iterate(String queryString, Object value) throws DataAccessException {
		return getHibernateTemplate().iterate(queryString, value);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object[])
	 */
	public Iterator iterate(String queryString, Object[] values) throws DataAccessException {
		return getHibernateTemplate().iterate(queryString, values);
	}

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String)
	 */
	public Iterator iterate(String queryString) throws DataAccessException {
		return getHibernateTemplate().iterate(queryString);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(Class entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
		return getHibernateTemplate().load(entityClass, id, lockMode);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable)
	 */
	public <T> T load(Class<T> entityClass, Serializable id) throws DataAccessException {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * @param entity
	 * @param id
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Object, java.io.Serializable)
	 */
	public void load(Object entity, Serializable id) throws DataAccessException {
		getHibernateTemplate().load(entity, id);
	}

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
		return getHibernateTemplate().load(entityName, id, lockMode);
	}

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable)
	 */
	public Object load(String entityName, Serializable id) throws DataAccessException {
		return getHibernateTemplate().load(entityName, id);
	}

	/**
	 * @param entityClass
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#loadAll(java.lang.Class)
	 */
	public List loadAll(Class entityClass) throws DataAccessException {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().lock(entity, lockMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().lock(entityName, entity, lockMode);
	}

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.Object)
	 */
	public Object merge(Object entity) throws DataAccessException {
		return getHibernateTemplate().merge(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.String, java.lang.Object)
	 */
	public Object merge(String entityName, Object entity) throws DataAccessException {
		return getHibernateTemplate().merge(entityName, entity);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.Object)
	 */
	public void persist(Object entity) throws DataAccessException {
		getHibernateTemplate().persist(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.String, java.lang.Object)
	 */
	public void persist(String entityName, Object entity) throws DataAccessException {
		getHibernateTemplate().persist(entityName, entity);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object, org.hibernate.LockMode)
	 */
	public void refresh(Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().refresh(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object)
	 */
	public void refresh(Object entity) throws DataAccessException {
		getHibernateTemplate().refresh(entity);
	}

	/**
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(Object entity, ReplicationMode replicationMode) throws DataAccessException {
		getHibernateTemplate().replicate(entity, replicationMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.String, java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(String entityName, Object entity, ReplicationMode replicationMode) throws DataAccessException {
		getHibernateTemplate().replicate(entityName, entity, replicationMode);
	}

	/**
	 * @param entity
	 * @param id
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.Object, java.io.Serializable)
	 */
	public void save(Object entity, Serializable id) throws DataAccessException {
		getHibernateTemplate().save(entity, id);
	}

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.Object)
	 */
	public Serializable save(Object entity) throws DataAccessException {
		return getHibernateTemplate().save(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param id
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.String, java.lang.Object, java.io.Serializable)
	 */
	public void save(String entityName, Object entity, Serializable id) throws DataAccessException {
		getHibernateTemplate().save(entityName, entity, id);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.String, java.lang.Object)
	 */
	public Serializable save(String entityName, Object entity) throws DataAccessException {
		return getHibernateTemplate().save(entityName, entity);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object entity) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.String, java.lang.Object)
	 */
	public void saveOrUpdate(String entityName, Object entity) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(entityName, entity);
	}

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(Collection entities) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * @param allowCreate
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setAllowCreate(boolean)
	 */
	public void setAllowCreate(boolean allowCreate) {
		getHibernateTemplate().setAllowCreate(allowCreate);
	}

	/**
	 * @param alwaysUseNewSession
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setAlwaysUseNewSession(boolean)
	 */
	public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
		getHibernateTemplate().setAlwaysUseNewSession(alwaysUseNewSession);
	}

	/**
	 * @param beanFactory
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		getHibernateTemplate().setBeanFactory(beanFactory);
	}

	/**
	 * @param cacheQueries
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setCacheQueries(boolean)
	 */
	public void setCacheQueries(boolean cacheQueries) {
		getHibernateTemplate().setCacheQueries(cacheQueries);
	}

	/**
	 * @param checkWriteOperations
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setCheckWriteOperations(boolean)
	 */
	public void setCheckWriteOperations(boolean checkWriteOperations) {
		getHibernateTemplate().setCheckWriteOperations(checkWriteOperations);
	}

	/**
	 * @param entityInterceptor
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setEntityInterceptor(org.hibernate.Interceptor)
	 */
	public void setEntityInterceptor(Interceptor entityInterceptor) {
		getHibernateTemplate().setEntityInterceptor(entityInterceptor);
	}

	/**
	 * @param entityInterceptorBeanName
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setEntityInterceptorBeanName(java.lang.String)
	 */
	public void setEntityInterceptorBeanName(String entityInterceptorBeanName) {
		getHibernateTemplate().setEntityInterceptorBeanName(entityInterceptorBeanName);
	}

	/**
	 * @param exposeNativeSession
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setExposeNativeSession(boolean)
	 */
	public void setExposeNativeSession(boolean exposeNativeSession) {
		getHibernateTemplate().setExposeNativeSession(exposeNativeSession);
	}

	/**
	 * @param fetchSize
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setFetchSize(int)
	 */
	public void setFetchSize(int fetchSize) {
		getHibernateTemplate().setFetchSize(fetchSize);
	}

	/**
	 * @param filter
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFilterName(java.lang.String)
	 */
	public void setFilterName(String filter) {
		getHibernateTemplate().setFilterName(filter);
	}

	/**
	 * @param filterNames
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFilterNames(java.lang.String[])
	 */
	public void setFilterNames(String[] filterNames) {
		getHibernateTemplate().setFilterNames(filterNames);
	}

	/**
	 * @param flushMode
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFlushMode(int)
	 */
	public void setFlushMode(int flushMode) {
		getHibernateTemplate().setFlushMode(flushMode);
	}

	/**
	 * @param constantName
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFlushModeName(java.lang.String)
	 */
	public void setFlushModeName(String constantName) {
		getHibernateTemplate().setFlushModeName(constantName);
	}

	/**
	 * @param jdbcExceptionTranslator
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setJdbcExceptionTranslator(org.springframework.jdbc.support.SQLExceptionTranslator)
	 */
	public void setJdbcExceptionTranslator(SQLExceptionTranslator jdbcExceptionTranslator) {
		getHibernateTemplate().setJdbcExceptionTranslator(jdbcExceptionTranslator);
	}

	/**
	 * @param maxResults
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setMaxResults(int)
	 */
	public void setMaxResults(int maxResults) {
		getHibernateTemplate().setMaxResults(maxResults);
	}

	/**
	 * @param queryCacheRegion
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#setQueryCacheRegion(java.lang.String)
	 */
	public void setQueryCacheRegion(String queryCacheRegion) {
		getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
	}

	/**
	 * @param sessionFactory
	 * @see org.springframework.orm.hibernate3.HibernateAccessor#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		getHibernateTemplate().setSessionFactory(sessionFactory);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object, org.hibernate.LockMode)
	 */
	public void update(Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().update(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object)
	 */
	public void update(Object entity) throws DataAccessException {
		getHibernateTemplate().update(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	public void update(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().update(entityName, entity, lockMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object)
	 */
	public void update(String entityName, Object entity) throws DataAccessException {
		getHibernateTemplate().update(entityName, entity);
	}
}
