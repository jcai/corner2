/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package corner.orm.osuser.v3;

import com.opensymphony.user.provider.CredentialsProvider;
import com.opensymphony.user.provider.hibernate.entity.HibernateUser;
import com.opensymphony.user.provider.hibernate.impl.HibernateUserImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author <a href="mailto:matthew@linjafoo.com">Matthew E. Porter</a>
 */
public class HibernateCredentialsProvider extends HibernateBaseProvider implements CredentialsProvider {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
	 * 
	 */
	private static final long serialVersionUID = -42816771623939366L;

	/**
     * Check password supplied matches that of User.
     */
    public boolean authenticate(String name, String password) {
        boolean result = false;
        HibernateUser user = getUserDAO().findUserByUsername(name);

        if (user != null) {
            result = user.authenticate(password.trim());
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Change password of user.
     */
    public boolean changePassword(String name, String password) {
        boolean result = false;

        HibernateUser user = getUserDAO().findUserByUsername(name);

        if (user != null) {
            user.setPassword(password.trim());
            result = getUserDAO().updateUser(user);
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Create new Entity with given name.
     *
     * @return Whether entity was successfully created.
     */
    public boolean create(String name) {
        HibernateUser user = new HibernateUserImpl();
        user.setName(name);

        return getUserDAO().saveUser(user);
    }

    /**
     * Flush the providers caches - if it is caching.
     *
     * Providers may implement their own caching strategies. This method merely indicates to the
     * provider that it should flush it's caches now.
     */
    public void flushCaches() {
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
        List users = getUserDAO().findUsers();
        List ret = new ArrayList();

        for (int i = 0; i < users.size(); i++) {
            HibernateUser user = (HibernateUser) users.get(i);
            ret.add(user.getName());
        }

        return Collections.unmodifiableList(ret);
    }

    /**
     * Create Entity with given name.
     *
     * @return Whether entity was successfully removed.
     */
    public boolean remove(String name) {
        int numberDeleted = getUserDAO().deleteUserByUsername(name);

        if (numberDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }
}
