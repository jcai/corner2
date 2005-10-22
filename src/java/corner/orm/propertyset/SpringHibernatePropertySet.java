package corner.orm.propertyset;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate.HibernateObjectRetrievalFailureException;

import com.opensymphony.module.propertyset.PropertyException;
import com.opensymphony.module.propertyset.hibernate.HibernatePropertySet;
import com.opensymphony.module.propertyset.hibernate.HibernatePropertySetDAO;
import com.opensymphony.module.propertyset.hibernate.PropertySetItem;

public class SpringHibernatePropertySet extends HibernatePropertySet implements
		InitializingBean {

    protected static Log log = LogFactory.getLog(HibernatePropertySet.class.getName());

    //~ Instance fields ////////////////////////////////////////////////////////

 
    private Long entityId;
    private String entityName;

	private HibernatePropertySetDAO dao;

    //~ Methods ////////////////////////////////////////////////////////////////

    public HibernatePropertySetDAO getHibernatePropertySetDAO(){
    	return dao;
    }
    public void setHibernatePropertySetDAO(HibernatePropertySetDAO dao){
    	this.dao=dao;
    }
    public void setEntityId(Long entityId){
    	this.entityId=entityId;
    }
    public void setEntityName(String entityName){
    	this.entityName=entityName;
    }
    public Collection getKeys(String prefix, int type) throws PropertyException {
        return dao.getKeys(entityName, entityId, prefix, type);
    }

    public int getType(String key) throws PropertyException {
        return findByKey(key).getType();
    }

    public boolean exists(String key) throws PropertyException {
        try {
            findByKey(key);

            return true;
        } catch (PropertyException e) {
            return false;
        }
    }

   
    public void remove(String key) throws PropertyException {
        dao.remove(entityName, entityId, key);
    }

    protected void setImpl(int type, String key, Object value) throws PropertyException {
    	PropertySetItem item = null;

        boolean update = true;
        try{
        	item = dao.findByKey(entityName, entityId, key);
        }catch(HibernateObjectRetrievalFailureException onfe){
        	item=null;
        }

        if (item == null) {
            update = false;
            item = dao.create(entityName, entityId.longValue(), key);
        } else if (item.getType() != type) {
            throw new PropertyException("Existing key '" + key + "' does not have matching type of " + type);
        }

        switch (type) {
        case BOOLEAN:
            item.setBooleanVal(((Boolean) value).booleanValue());

            break;

        case DOUBLE:
            item.setDoubleVal(((Double) value).doubleValue());

            break;

        case STRING:
        case TEXT:
            item.setStringVal((String) value);

            break;

        case LONG:
            item.setLongVal(((Long) value).longValue());

            break;

        case INT:
            item.setIntVal(((Integer) value).intValue());

            break;

        case DATE:
            item.setDateVal((Date) value);

            break;

        default:
            throw new PropertyException("type " + type + " not supported");
        }

        item.setType(type);

        dao.setImpl(item, update);
    }

    protected Object get(int type, String key) throws PropertyException {
        PropertySetItem item = findByKey(key);

        if (item.getType() != type) {
            throw new PropertyException("key '" + key + "' does not have matching type of " + type);
        }

        switch (type) {
        case BOOLEAN:
            return new Boolean(item.getBooleanVal());

        case DOUBLE:
            return new Double(item.getDoubleVal());

        case STRING:
        case TEXT:
            return item.getStringVal();

        case LONG:
            return new Long(item.getLongVal());

        case INT:
            return new Integer(item.getIntVal());

        case DATE:
            return item.getDateVal();
        }

        throw new PropertyException("type " + type + " not supported");
    }

    private PropertySetItem findByKey(String key) throws PropertyException {
        PropertySetItem item = dao.findByKey(entityName, entityId, key);

        if (item != null) {
            return item;
        } else {
            throw new PropertyException("Unknown key '" + key + "'");
        }
    }
	
	public void afterPropertiesSet() throws Exception {
		//Assert.notNull();
		//init();
	}
	public void init(Map config, Map args) {
        //super.init(config, args);
        this.entityId = (Long) args.get("entityId");
        this.entityName = (String) args.get("entityName");
	}

}
