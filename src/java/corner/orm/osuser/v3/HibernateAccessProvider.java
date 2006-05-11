/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package corner.orm.osuser.v3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.user.provider.AccessProvider;
import com.opensymphony.user.provider.hibernate.entity.HibernateGroup;
import com.opensymphony.user.provider.hibernate.entity.HibernateUser;
import com.opensymphony.user.provider.hibernate.impl.HibernateGroupImpl;


/**
 * @author <a href="mailto:matthew@linjafoo.com">Matthew E. Porter</a>
 */
public class HibernateAccessProvider extends HibernateBaseProvider implements AccessProvider {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
	 * 
	 */
	private static final long serialVersionUID = -600303308892482259L;

	/**
    * Add user to group.
    *
    * @return Whether user was successfully added to group.
    */
    public boolean addToGroup(String username, String groupname) {
        boolean result = false;

        HibernateUser user = getUserDAO().findUserByUsername(username);
        HibernateGroup group = getGroupDAO().findGroupByGroupname(groupname);

        if ((user != null) && (group != null)) {
            user.addGroup(group);
            result = getUserDAO().updateUser(user);
        }

        return result;
    }

    /**
    * Create new Entity with given name.
    *
    * @return Whether entity was successfully created.
    */
    public boolean create(String name) {
        HibernateGroup group = new HibernateGroupImpl();
        group.setName(name);

        return getGroupDAO().saveGroup(group);
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
        HibernateUser user = getUserDAO().findUserByUsername(name);

        if (user == null) {
            HibernateGroup group = getGroupDAO().findGroupByGroupname(name);

            if (group == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
    * Find out whether given user is member of given group.
    *
    * @return Whether user is member of group.
    */
    public boolean inGroup(String username, String groupname) {
        boolean result = false;

        HibernateUser user = getUserDAO().findUserByUsernameAndGroupname(username, groupname);

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
        List groups = getGroupDAO().findGroups();

        if (groups != null) {
            List ret = new ArrayList();

            for (int i = 0; i < groups.size(); i++) {
                HibernateGroup hibernateGroup = (HibernateGroup) groups.get(i);
                ret.add(hibernateGroup.getName());
            }

            return Collections.unmodifiableList(ret);
        } else {
            return null;
        }
    }

    /**
    * List all groups that contain a user.
    *
    * @return List containing Strings of groupnames. If no groups found,
    *         empty list should be returned. If feature not supported
    *         by UserProvider, null shall be returned. This List should
    *         be immutable.
    */
    public List listGroupsContainingUser(String username) {
        HibernateUser user = getUserDAO().findUserByUsername(username);

        return Collections.unmodifiableList(user.getGroupNameList());
    }

    /**
    * List all users that are contained within a group.
    *
    * @return List containing Strings of usernames. If no users found,
    *         empty list should be returned. If feature not supported
    *         by UserProvider, null shall be returned. This List should
    *         be immutable.
    */
    public List listUsersInGroup(String groupname) {
        HibernateGroup group = getGroupDAO().findGroupByGroupname(groupname);

        return Collections.unmodifiableList(group.getUserNameList());
    }

    /**
    * Create Entity with given name.
    *
    * @return Whether entity was successfully removed.
    */
    public boolean remove(String name) {
        int numberDeleted = getGroupDAO().deleteGroupByGroupname(name);

        if (numberDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * Remove user from group.
    *
    * @return Whether user was successfully removed from group.
    */
    public boolean removeFromGroup(String username, String groupname) {
        boolean result = false;
        HibernateUser user = getUserDAO().findUserByUsernameAndGroupname(username, groupname);

        if (user != null) {
            Iterator groupsIter = user.getGroups().iterator();

            while ((groupsIter.hasNext()) && (result == false)) {
                HibernateGroup group = (HibernateGroup) groupsIter.next();

                if (group.getName().equals(groupname)) {
                    user.getGroups().remove(group);
                    result = getUserDAO().updateUser(user);
                }
            }
        } else {
            result = true;
        }

        return result;
    }
}
