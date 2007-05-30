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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.util.BeanUtils;
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
	 * 使用HQL实现对关联实体的批量更新
	 * 
	 * example: 目标:更新所有id为1的实体中name属性为 A a = new A(); a.setId("1");
	 * this.doDeleteBatchRelativeEntityAction(A.class,"id",a);
	 * 此时将删除所有DB中id属性为1的A记录
	 * 
	 * @param clazz
	 *            被更新的实体Class
	 * @param updatePropertyName
	 *            被更新的属性名称
	 * @param conditionPropertyName
	 *            被更新的实体满足的条件属性名称
	 * @param entity
	 *            一个被更新的实体的instance,设置好,updatePropertyName,conditionPropertyName属性的参数
	 * @return 被更新的记录数量
	 */
	public <T> int doUpdateBatchRelativeEntityAction(final Class clazz,
			final String updatePropertyName,
			final String conditionPropertyName, final T entity) {
//		if (entity != null) {
//			return ((Integer) ((HibernateObjectRelativeUtils) this
//					.getObjectRelativeUtils()).getHibernateTemplate().execute(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session)
//								throws HibernateException, SQLException {
//							StringBuffer buffer = new StringBuffer("update ");
//							buffer.append(clazz.getName());
//							buffer.append(" clazz ");
//							buffer.append(" set clazz.");
//							buffer.append(updatePropertyName);
//							buffer.append("=:");
//							buffer.append(updatePropertyName);
//							buffer.append(" where clazz.");
//							buffer.append(conditionPropertyName);
//							buffer.append("=:");
//							buffer.append(conditionPropertyName);
//							return session
//									.createQuery(buffer.toString())
//									.setString(
//											updatePropertyName,
//											(String) BeanUtils.getProperty(
//													entity, updatePropertyName))
//									.setString(
//											conditionPropertyName,
//											(String) BeanUtils.getProperty(
//													entity,
//													conditionPropertyName))
//									.executeUpdate();
//						}
//					})).intValue();
//		} else {
//			return 0;
//		}
		return 0;
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
}
