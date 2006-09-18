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
	private ISelectFilter filter;
		
    /**
     * 默认的构造函数
     *<p>提供一个默认的构造方法,需要的参数可以通过set方法注入</p>
     */
    public CornerSelectModel(){
    	
    }
    
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
		return this.filter.filterValues(match);
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

	/**
	 * @return Returns the cnlabelField.
	 */
	public String getCnlabelField() {
		return cnlabelField;
	}

	/**
	 * @param cnlabelField The cnlabelField to set.
	 */
	public void setCnlabelField(String cnlabelField) {
		this.cnlabelField = cnlabelField;
	}

	/**
	 * @return Returns the labelField.
	 */
	public String getLabelField() {
		return labelField;
	}

	/**
	 * @param labelField The labelField to set.
	 */
	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	/**
	 * @return Returns the queryClass.
	 */
	public Class getQueryClass() {
		return queryClass;
	}

	/**
	 * @param queryClass The queryClass to set.
	 */
	public void setQueryClass(Class queryClass) {
		this.queryClass = queryClass;
	}

	public void setFilter(ISelectFilter filter) {
		this.filter=filter;
		
	}
}
