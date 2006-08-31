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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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

    private List _values;
    
    private EntityService entityService;
    
    private IPersistentQueriable callback;
    
	
	/**
	 * 用于存储中文字符
	 */
    private String _cnlabelExpression;
	
	
	/**
	 * 重载构造函数，用于提供具有拼音和中文功能的检索
	 * @param values 
	 * @param keyField
	 * @param labelField
	 * @param cnlabelField
	 */
	public DoubleSearchAutocompleteModel(List values, String keyField, String labelField, String cnlabelField) {
		Defense.notNull(values, "Value list can't be null.");
		Defense.notNull(cnlabelField, "Model CNlabelField java beans expression can't be null.");
		_cnlabelExpression = cnlabelField;
		_values = values;
	}
	
	/**
	 * 构造函数
	 * <p>通过构造函数注入EntityService,IpersistentQueriable</p>
	 * @param values 
	 * @param keyField
	 * @param labelField
	 * @param cnlabelField
	 */
	public DoubleSearchAutocompleteModel(EntityService entityService,IPersistentQueriable callback, String keyField, String labelField, String cnlabelField) {
		Defense.notNull(entityService, "entityService can't be null!!!!!!!!!");
		Defense.notNull(callback, "callback can't be null!!!!!!!!!!!!");
		Defense.notNull(cnlabelField, "Model CNlabelField java beans expression can't be null.");
		_cnlabelExpression = cnlabelField;
		this.entityService = entityService;
		this.callback = callback;
	}
	
	/**
	 * 从给定的对象集合中查找出符合条件的对象
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#filterValues(java.lang.String)
	 */
	public Map filterValues(String match) {
        Map ret = new HashMap();
        
        if (match == null)
            return ret;
        
        String filter = match.trim().toLowerCase();
        
        for (int i = 0; i < _values.size(); i++) {
            
            Object value = _values.get(i);
            String label = getLabelFor(value);
            String cnlabel = getCnlabelFor(value);
            
            if (label.toLowerCase().indexOf(filter) > -1 || cnlabel.toLowerCase().indexOf(filter)> -1)
                ret.put(value, cnlabel);
        }
        
        return ret;
	}
	
	public Iterator iteratorAllValue(String match){
		return ((Iterator) ((HibernateObjectRelativeUtils) this.entityService
				.getObjectRelativeUtils()).getHibernateTemplate()
				.execute(new HibernateCallback(){

					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria=callback.createCriteria(session);
						callback.appendCriteria(criteria);
//						criteria.setFirstResult(nFirst);
//						criteria.setMaxResults(nPageSize);
						return criteria.list().iterator();
					}}));
	}

	/**
	 * 得到cnlabelField的值
	 * @param value
	 * @return
	 */
    public String getCnlabelFor(Object value){
        try {
            
            return PropertyUtils.getProperty(value, _cnlabelExpression).toString();
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }    	
    }

	/**
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#getLabelFor(java.lang.Object)
	 */
	public String getLabelFor(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object primaryKey) {
		// TODO Auto-generated method stub
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
