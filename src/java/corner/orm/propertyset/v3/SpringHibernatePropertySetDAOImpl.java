package corner.orm.propertyset.v3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.module.propertyset.hibernate.HibernatePropertySetDAO;
import com.opensymphony.module.propertyset.hibernate.PropertySetItem;
import com.opensymphony.module.propertyset.hibernate.PropertySetItemImpl;

public class SpringHibernatePropertySetDAOImpl extends HibernateDaoSupport implements
		HibernatePropertySetDAO {

//	~ Methods ////////////////////////////////////////////////////////////////

    public void setImpl(PropertySetItem item, boolean isUpdate) {
    	if (isUpdate) {
            getHibernateTemplate().update(item);
        } else {
        	getHibernateTemplate().save(item);
        }
    }

    public Collection getKeys(final String entityName, final Long entityId, final String prefix, final int type) {
    	List list = (List)getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
            	Query query;

                if ((prefix != null) && (type > 0)) {
                    query = session.getNamedQuery("all_keys_with_type_like");
                    query.setString("like", prefix + '%');
                    query.setInteger("type", type);
                } else if (prefix != null) {
                    query = session.getNamedQuery("all_keys_like");
                    query.setString("like", prefix + '%');
                } else if (type > 0) {
                    query = session.getNamedQuery("all_keys_with_type");
                    query.setInteger("type", type);
                } else {
                    query = session.getNamedQuery("all_keys");
                }

                query.setString("entityName", entityName);
                query.setLong("entityId", entityId.longValue());

                return query.list();
            }

        });
    	return list;

    }

    public PropertySetItem create(String entityName, long entityId, String key) {
        return new PropertySetItemImpl(entityName, entityId, key);
    }

    public PropertySetItem findByKey(final String entityName, final Long entityId, final String key) {
    	return (PropertySetItem)getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
            	try{
            		return session.load(PropertySetItemImpl.class, new PropertySetItemImpl(entityName, entityId.longValue(), key));
            	}catch(HibernateException he){
            		return null;
            	}
            }

        });

    }

    public void remove(final String entityName, final Long entityId) {
    	getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException {
//				hani: todo this needs to be optimised rather badly, but I have no idea how
	            Collection keys = getKeys(entityName, entityId, null, 0);
	            Iterator iter = keys.iterator();

	            while (iter.hasNext()) {
	                String key = (String) iter.next();
	                session.delete(SpringHibernatePropertySetDAOImpl.this.findByKey(entityName, entityId, key));
	            }
	            return null;
	            
			}});
    	
    	

    }

    public void remove(String entityName, Long entityId, String key) {
    	 PropertySetItem item = findByKey(entityName, entityId, key);
         if(item == null)
         {
             return;
         } else
         {
             getHibernateTemplate().delete(item);
             return;
         }
    }
    

}
