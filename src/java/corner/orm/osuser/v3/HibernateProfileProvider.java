/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package corner.orm.osuser.v3;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetManager;

import com.opensymphony.user.provider.ProfileProvider;
import com.opensymphony.user.provider.hibernate.entity.HibernateGroup;
import com.opensymphony.user.provider.hibernate.entity.HibernateUser;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


/**
 * @author <a href="mailto:matthew@linjafoo.com">Matthew E. Porter</a>
 */
public class HibernateProfileProvider extends HibernateBaseProvider implements ProfileProvider {
    //~ Instance fields ////////////////////////////////////////////////////////

    /**
	 * 
	 */
	private static final long serialVersionUID = -4795290952064228151L;
	private String entityName;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Retrieve profile for User with given name.
    */
    public PropertySet getPropertySet(String name) {
        PropertySet propertySet;

        HashMap args = new HashMap();

        HibernateUser user = getUserDAO().findUserByUsername(name);

        if (user != null) {
            args.put("entityId", new Long(user.getId()));
            args.put("entityName", entityName + "_user");
        } else {
            HibernateGroup group = getGroupDAO().findGroupByGroupname(name);
            args.put("entityId", new Long(group.getId()));
            args.put("entityName", entityName + "_group");
        }

        //args.put("configurationProvider", configProvider);
        propertySet = PropertySetManager.getInstance("hibernate", args);

        return propertySet;
    }

    /**
    * Create new Entity with given name.
    *
    * @return Whether entity was successfully created.
    */
    public boolean create(String name) {
        return false;
    }

    /**
    * Flush the providers caches - if it is caching.
    *
    * Providers may implement their own caching strategies. This method merely indicates to the
    * provider that it should flush it's caches now.
    */
    public void flushCaches() {
        // do nothing
    }

    /**
    * Determine whether this UserProvider implementation is responsible for handling
    * this Entity.
    */
    public boolean handles(String name) {
        boolean result = false;
        HibernateUser user = getUserDAO().findUserByUsername(name);

        if (user != null) {
            result = true;
        } else {
            HibernateGroup group = getGroupDAO().findGroupByGroupname(name);
            result = (group != null);
        }

        return result;
    }

    /**
    * Called by UserManager before any other method.
    * Allows for UserProvider specific initialization.
    *
    * @param properties Extra properties passed across by UserManager.
    */
    public boolean init(Properties properties) {
        boolean result = super.init(properties);
        this.entityName = properties.getProperty("propertySetEntity", "OSUser");

        return result;
    }

    /**
    * Returns List of names (Strings) of all Entities that can be accessed by this UserProvider
    * If this UserProvider cannot retrieve a list of names, null is to be returned.
    * If there are no current Entities stored by this provider, an empty List is to be returned.
    * The order of names returned can be determined by the UserProvider (it may or may not be
    * relevant).
    *
    * This List should be immutable.
    */
    public List list() {
        return null;
    }

    /**
    * Create Entity with given name.
    *
    * @return Whether entity was successfully removed.
    */
    public boolean remove(String name) {
        return false;
    }
}
