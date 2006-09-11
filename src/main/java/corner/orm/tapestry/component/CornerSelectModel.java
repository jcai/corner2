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

package corner.orm.tapestry.component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.service.EntityService;
import corner.util.TapestryHtmlFormatter;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 */
public class CornerSelectModel implements ISelectModel {
	public static final int nFirst = 0;
	public static final int nPageSize = 20;
	
    
    private EntityService entityService;
    
    
    /**
     * 被检索的类的名称
     */
    private Class queryClass;
    
    /**
     * 存储拼音或拼音缩写字符
     */
    private String labelField;
    
	
	/**
	 * 用于存储中文字符
	 */
    private String cnlabelField;
    
    /**
     * 通过输入的查询关键字检索出的所有符合条件的实体的列表
     */
    private List _values = new ArrayList();
		
    /**
     * 构造函数
	 * <p>通过构造函数注入EntityService,IpersistentQueriable,obj</p>
     * @param entityService 基础服务类
     * @param queryClass 下拉菜单中要现实的列表实体
     * @param labelField 用于进行拼音检索的字段
     * @param cnlabelField 用于进行中文检索的字段
     */
	public CornerSelectModel(EntityService entityService,Class queryClass, String labelField, String cnlabelField) {
		Defense.notNull(entityService, "entityService can't be null!");
		Defense.notNull(labelField, "label can't be null!");
		Defense.notNull(queryClass, "queryClass can't be null!");
		Defense.notNull(cnlabelField, "Model CNlabelField java beans expression can't be null.");
		this.queryClass = queryClass;
		this.cnlabelField = cnlabelField;
		this.labelField = labelField;
		this.entityService = entityService;
	}
	
	/**
	 * 从给定的对象集合中查找出符合条件的对象
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#filterValues(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map filterValues(String match) {
        Map ret = new HashMap();
        
        if (match == null)
            return ret;
        
        StringBuffer buffer = new StringBuffer("");
        buffer.append(match.trim());
        buffer.append("%");
        String filter = buffer.toString();

        _values = this.listAllMatchedValue(filter);
        for(Object obj:_values){

        	//需要保存关联的时候使用
        	//Object label = obj;
        	Object label = this.getCnLabelFor(obj);
        	String cnlabel = this.getCnLabelFor(obj);
        	ret.put(label, cnlabel);
        }
        return ret;
	}
	
	public List listAllMatchedValue(final String filter){
		return ((List) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate()
				.execute(new HibernateCallback(){
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria=session.createCriteria(queryClass);
						criteria.add(Restrictions.or(Restrictions.like(labelField,filter), Restrictions.like(cnlabelField,filter)));
						criteria.setFirstResult(nFirst);
						criteria.setMaxResults(nPageSize);
						return criteria.list();
					}}));
	}

	/**
	 * 得到cnlabelField的值
	 * @param value
	 * @return
	 */
    public String getCnLabelFor(Object value){
        try {
            
            if(value instanceof String){
            	return value.toString();
            }
            else{
            	return PropertyUtils.getProperty(value, TapestryHtmlFormatter.lowerFirstLetter(cnlabelField)).toString();
            }
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }    	
    }

	/**
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#getLabelFor(java.lang.Object)
	 */
	public String getLabelFor(Object value) {
        try {
            
            if(value instanceof String){
            	return value.toString();
            }
            else{
            	return PropertyUtils.getProperty(value, TapestryHtmlFormatter.lowerFirstLetter(labelField)).toString();
            }
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
	}
	
	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object value) {
        return value;
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object primaryKey) {
        return this.getPrimaryKey(primaryKey);
	}

	/**
	 * @return Returns the entityService.
	 */
	public EntityService getEntityService() {
		return entityService;
	}

	/**
	 * @param entityService The entityService to set.
	 */
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}
}
