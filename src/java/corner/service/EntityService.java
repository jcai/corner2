//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.service;



import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.util.PaginationBean;

/**
 * 
 * Entity Service.
 * <p>提供了对实体的基本操作.
 * 譬如:增加,删除,修改等.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-2
 */
public class EntityService {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityService.class);

	/**对象关系utils**/
	protected ObjectRelativeUtils oru;

	/**
	 * 设定O/R M的util类.
	 * @param oru O/R M的util类.
	 */
	public void setObjectRelativeUtils(ObjectRelativeUtils oru) {
		this.oru = oru;
	}
	/**
	 * 得到 O/R M的util类.
	 * @return O/R M的util类.
	 */
	public ObjectRelativeUtils getObjectRelativeUtils() {
		return oru;
	}
	
	/**
	 * 保存一个实体
	 * @param <T> 实体
	 * @param entity 待保存的实体
	 * @return 保存后的实体.
	 */
	public <T> Serializable saveEntity(T entity) {
		return oru.save(entity);
	}
	/**
	 * 保存或者更新实体.
	 * @param <T> 实体.
	 * @param entity 待保存或更新实体.
	 */
	public <T> void saveOrUpdateEntity(T entity) {
		oru.saveOrUpdate(entity);
	}
	/**
	 * 更新一个实体.
	 * @param <T> 实体.
	 * @param entity 待更新的实体.
	 */
	public <T> void updateEntity(T entity) {
		oru.update(entity);
	}
	
	/**
	 * 装载一个实体.
	 * @param <T> 实体.
	 * @param clazz 装载实体的类.
	 * @param keyValue 主健值.
	 * @return 持久化的实体.
	 */
	public <T> T loadEntity(Class<T> clazz, Serializable keyValue) {
		return oru.load(clazz, keyValue);
	}
	/**
	 * 查询一个实体.
	 * @param <T> 实体
	 * @param clazz 实体类.
	 * @param keyValue 主健值.
	 * @return 实体,如果未找到,返回null.
	 * @since 2.0
	 */
	public <T> T getEntity(Class<T> clazz, Serializable keyValue) {
		return oru.get(clazz, keyValue);
	}
	
	
	/**
	 * 批量删除实体.
	 * @param <T> 实体.
	 * @param ts 实体数组.
	 */
	public <T> void deleteEntities(T... ts) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("deleteEntities(List),delete entity list size [" + ts.length + "]"); //$NON-NLS-1$
		}

		for (T entity : ts) {
			try{
				
				oru.delete(entity);
			}catch(Exception e){
				logger.warn(e.getMessage());
				//donoting
			}
		}
	}
	/**
	 * 通过给定的实体ID来删除实体.
	 * @param <T> 实体. 
	 * @param clazz 待删除的实体.
	 * @param keyValue 主键值.
	 */
	public <T> void deleteEntityById(Class<T> clazz,Serializable keyValue) {
		T entity=this.getEntity(clazz,keyValue);
		if(entity!=null){
			oru.delete(entity);
		}
	}
	/**
	 * 通过给定的类的名称和主键值来删除实体.
	 * @param clazzName 类名.
	 * @param key 主键值.
	 */
	@SuppressWarnings("unchecked")
	public void deleteEntityById(String clazzName, Serializable key) {
		try {
			Class clazz=Class.forName(clazzName);
			this.deleteEntityById(clazz,key);
		} catch (ClassNotFoundException e) {
			//do noting 
		}
		
	}
	/**
	 * 通过给定的类的名称和主键值来得到实体.
	 * @param clazzname 实体类的名称.
	 * @param key 主键值.
	 * @return 实体,当给定的类未发现时,返回null.
	 */
	public Object getEntity(String clazzname, Serializable key) {
		Class clazz;
		try {
			clazz = Class.forName(clazzname);
			return this.getEntity(clazz,key);
		} catch (ClassNotFoundException e) {
			//do nothing
			logger.warn(e.getMessage());
			return null;
		}
		
		
	}
	/**
	 * 通过给定的类以及分页用的bean来得到list。
	 * @param <T> 需要查找的类。
	 * @param clazz 类。
	 * @param pb 分页的bean。
	 * @return 列表.
	 */
	public <T> List<T> find(Class<T> clazz, PaginationBean pb) { 
		return oru.find("from " + clazz.getName(), pb); 
	} 
}
