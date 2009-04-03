//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.components.ForBean;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.model.ICodeModel;
import corner.orm.hibernate.IPersistModel;
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.util.StringUtils;


/**
 * 抽象的,在一个页面上显示编辑和该实体列表的页面类
 * 
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class AbstractEntityFormListPage<T  extends IPersistModel> extends AbstractEntityFormPage<T> implements IPersistentQueriable{

	/**
	 * 取得当前页面操作的实体类
	 * 
	 * @return {@link Class}
	 * @throws ClassNotFoundException 
	 */
	public Class<T> getEntityClass(){
		Class<T> clazz = null;
		
		if(StringUtils.notBlank(getEntityClassName())){
			try {
				clazz = (Class<T>) Class.forName(getEntityClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			clazz = getEntityService().getEntityClass(getEntity());
		}
		return clazz;
	}
	
	/**
	 * String的className
	 */
	public abstract String getEntityClassName();
	
	/**
	 * For循环中使用的临时实体
	 * @return T
	 */
	public abstract T getObj();
	public abstract void setObj(T obj);
	
	/**
	 * 编辑当前实体
	 * @param entity
	 * @return {@link IPage}当前页面
	 */
	public IPage doEditEntityAction(T entity){
		this.setEntity(entity);
		return this;
	}
	
	/**
	 * 删除当前给定实体
	 * @param entity
	 * @return {@link IPage}当前页面
	 */
	public IPage doDeleteEntityAction(T entity){
		this.getEntityService().delete(entity);
		this.setEntity(getClassInstance());
		return this;
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityFormPage#getEntityListPage()
	 */
	@Override
	protected IPage getEntityListPage() {
		this.setEntity(getClassInstance());
		return this;
	}
	
	/**
	 * 根据给定的entityClass,返回对应的实体
	 * 
	 * @return 实现了{@link IPersistModel}接口的实例
	 */
	protected T getClassInstance(){
		T model = null;
		try {
			model = getEntityClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("实例化给定的实体时异常:"+e.getMessage());
		}
		
		return model;
	}
	
	/**
	 * 显示页面中实体列表
	 * 
	 * @return {@link List}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllEntityList(){
		return this.getEntityService().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = createCriteria(session);
				appendCriteria(criteria);
				appendOrder(criteria);
				return criteria.list();
			}
			
		});
	}
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity(java.lang.Object)
	 */
	@Override
	protected void saveOrUpdateEntity(Object entity) {
		updateAbcCode(this.getEntity());//初始化拼音码
		super.saveOrUpdateEntity(entity);
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		updateAbcCode(this.getEntity());//初始化拼音码
		super.saveOrUpdateEntity();
	}

	/**
	 * 初始化拼音
	 * 
	 * @param entity
	 */
	protected void updateAbcCode(T entity) {
		if (entity instanceof ICodeModel) {
			ICodeModel modelCoded = (ICodeModel) entity;
			modelCoded.initAbcCode();
		}
	}

	@Component(type="For",bindings={"source=getAllEntityList()","value=obj"})
	public abstract ForBean getForEntityField();

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#appendCriteria(org.hibernate.Criteria)
	 */
	public void appendCriteria(Criteria criteria) {
		//do nothing
	}

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#appendOrder(org.hibernate.Criteria)
	 */
	public void appendOrder(Criteria criteria) {
		//do nothing
	}

	/**
	 * @see corner.orm.tapestry.table.IPersistentQueriable#createCriteria(org.hibernate.Session)
	 */
	public Criteria createCriteria(Session session) {
		return session.createCriteria(getEntityClass());
	}
	
	//=====  加入直接操作实体的DirectLink连接
	/**
	 * 编辑实体的连接
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doEditEntityAction","parameters=obj"})
	public abstract IComponent getEditEntityLink();

	/**
	 * 删除实体的连接
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doDeleteEntityAction","parameters=obj"})
	public abstract IComponent getDeleteEntityLink();
}
