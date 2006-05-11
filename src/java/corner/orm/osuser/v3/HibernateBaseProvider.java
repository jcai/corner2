/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package corner.orm.osuser.v3;

import com.opensymphony.user.Entity;
import com.opensymphony.user.provider.UserProvider;
import com.opensymphony.user.provider.hibernate.OSUserHibernateConfigurationProvider;
import com.opensymphony.user.provider.hibernate.dao.HibernateGroupDAO;
import com.opensymphony.user.provider.hibernate.dao.HibernateUserDAO;
import com.opensymphony.user.provider.hibernate.impl.OSUserHibernateConfigurationProviderImpl;

import com.opensymphony.util.ClassLoaderUtil;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;

import java.util.List;
import java.util.Properties;


/**
 * @author <a href="mailto:matthew@linjafoo.com">Matthew E. Porter</a>
 */
public abstract class HibernateBaseProvider implements UserProvider {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(HibernateBaseProvider.class.getName());

    //~ Instance fields ////////////////////////////////////////////////////////

    OSUserHibernateConfigurationProvider configProvider;

    //~ Methods ////////////////////////////////////////////////////////////////

    public HibernateGroupDAO getGroupDAO() {
        return configProvider.getGroupDAO();
    }

    public HibernateUserDAO getUserDAO() {
        return configProvider.getUserDAO();
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
     * Called by UserManager before any other method.
     * Allows for UserProvider specific initialization.
     *
     * @param properties Extra properties passed across by UserManager.
     */
    public boolean init(Properties properties) {
        boolean result = false;

        // lets see if we need to use a configurationProvider
        try {
            String configProviderClass = properties.getProperty("configuration.provider.class");
            configProvider = null;

            if (configProviderClass != null) {
                try {
                    configProvider = (OSUserHibernateConfigurationProvider) ClassLoaderUtil.loadClass(configProviderClass, this.getClass()).newInstance();
                } catch (Exception e) {
                    log.error("Unable to load configuration provider class: " + configProviderClass, e);

                    return false;
                }
            } else {
                configProvider = new OSUserHibernateConfigurationProviderImpl();
            }

            //configProvider.setupConfiguration(properties);

            /*if ("true".equals(properties.getProperty("create.tables", "false"))) {
                SchemaExport ex = new SchemaExport(configProvider.getConfiguration(), properties);
                ex.create(true, false);
            }*/

            result = true;
        } catch (MappingException me) {
            log.error("Unable to configure Hibernate.", me);
        } catch (HibernateException he) {
            log.error("Unable to obtain Hibernate SessionFactory.", he);
        }

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
     * Load Entity.
     *
     * @return Whether entity was successfully loaded.
     */
    public boolean load(String name, Entity.Accessor accessor) {
        accessor.setMutable(true);

        return true;
    }

    /**
     * Create Entity with given name.
     *
     * @return Whether entity was successfully removed.
     */
    public boolean remove(String name) {
        return false;
    }

    /**
     * Stores changes to Entity.
     *
     * @return Whether changes to entity were successfully stored.
     */
    public boolean store(String name, Entity.Accessor accessor) {
        return false;
    }
}
