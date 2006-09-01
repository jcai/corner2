//==============================================================================
//file :        DoubleSearchAutocompleteModel.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.page.many2many2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.orm.tapestry.table.IPersistentQueriable;
import corner.service.EntityService;

/**
 * 支持中文和拼音双检索的AutocompleteModel
 * @author Ghost
 * @version $Revision$
 * @since 0.9.9.2
 */
public class DoubleSearchAutocompleteModel implements IAutocompleteModel {

	public static final int nFirst = 0;
	public static final int nPageSize = 20;
	
    
    private EntityService entityService;
    
    private IPersistentQueriable callback;
    
    /**
     * 存储对象的key信息
     */
    private String keyField;
    
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
     * @param callback 调用该操作的实体(该实体实现了IpersistentQueriable接口)
     * @param keyField 带查询的实体的key字段，可以通过该字唯一标示该实体
     * @param labelField 用于进行拼音检索的字段
     * @param cnlabelField 用于进行中文检索的字段
     */
	public DoubleSearchAutocompleteModel(EntityService entityService,IPersistentQueriable callback,String keyField, String labelField, String cnlabelField) {
		Defense.notNull(entityService, "entityService can't be null!");
		Defense.notNull(callback, "callback can't be null!");
		Defense.notNull(labelField, "label can't be null!");
		Defense.notNull(cnlabelField, "Model CNlabelField java beans expression can't be null.");
		this.keyField = keyField;
		this.cnlabelField = cnlabelField;
		this.labelField = labelField;
		this.entityService = entityService;
		this.callback = callback;
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
        
        StringBuffer buffer = new StringBuffer("%");
        buffer.append(match.trim().toLowerCase());
        buffer.append("%");
        String filter = buffer.toString();

        _values = this.listAllMatchedValue(filter);
        for(Object obj:_values){
        	String label = this.getLabelFor(obj);
        	String cnlabel = this.getCnlabelFor(obj);
        	ret.put(label, cnlabel);
        }
        return ret;
	}
	
	public List listAllMatchedValue(final String filter){
		return ((List) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate()
				.execute(new HibernateCallback(){

					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria=callback.createCriteria(session);
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
    public String getCnlabelFor(Object value){
        try {
            
            return PropertyUtils.getProperty(value, cnlabelField).toString();
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }    	
    }

	/**
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#getLabelFor(java.lang.Object)
	 */
	public String getLabelFor(Object value) {
        try {
            
            return PropertyUtils.getProperty(value, labelField).toString();
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object value) {
        try {
            
            return PropertyUtils.getProperty(value, keyField);
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object primaryKey) {
        for (int i = 0; i < _values.size(); i++) {
            
            Object value = _values.get(i);
            if (getPrimaryKey(value).toString().equals(primaryKey.toString()))
                return value;
        }
        
        return null;
	}

	/**
	 * @return Returns the callback.
	 */
	public IPersistentQueriable getCallback() {
		return callback;
	}

	/**
	 * @param callback The callback to set.
	 */
	public void setCallback(IPersistentQueriable callback) {
		this.callback = callback;
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