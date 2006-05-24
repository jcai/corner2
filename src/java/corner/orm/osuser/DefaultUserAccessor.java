//==============================================================================
//file :        DefaultUserAccessor.java
//project:      poisoning
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================
package corner.orm.osuser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.user.DuplicateEntityException;
import com.opensymphony.user.EntityNotFoundException;
import com.opensymphony.user.Group;
import com.opensymphony.user.ImmutableException;
import com.opensymphony.user.User;
import com.opensymphony.user.UserManager;
import com.opensymphony.util.TextUtils;

/**
 * 
 * 提供对osuser的操作。
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-9-12
 */
public class DefaultUserAccessor implements UserAccessor

{

	public DefaultUserAccessor() {
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#addUser(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String[])
	 */
	public User addUser(String username, String password, String email,
			String fullname, String groups[]) {
		User user;
		try {
			user = UserManager.getInstance().createUser(username.toLowerCase());
			user.setPassword(password);
			user.setEmail(email);
			user.setFullName(fullname);
			if (user.getPropertySet().exists("user.signup.date"))
				user.getPropertySet().remove("user.signup.date");
			user.getPropertySet().setDate("user.signup.date", new Date());
			if (groups != null) {
				for (int i = 0; i < groups.length; i++) {
					Group group = getGroupCreateIfNecessary(groups[i]);
					user.addToGroup(group);
				}

			}
			return user;
		} catch (DuplicateEntityException e) {
			throw new RuntimeException(e);
		} catch (ImmutableException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getUsers()
	 */
	@SuppressWarnings("unchecked")
	public <T> List<User> getUsers() {
		return UserManager.getInstance().getUsers();
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getUser(java.lang.String)
	 */
	public User getUser(String name) {
		if (!TextUtils.stringSet(name))
			return null;
		try {
			return UserManager.getInstance().getUser(name);
		} catch (EntityNotFoundException e) {
			return null;
		}

	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getGroups()
	 */
	public List getGroups() {
		return UserManager.getInstance().getGroups();
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#addGroup(java.lang.String)
	 */
	public Group addGroup(String name) {
		if (!TextUtils.stringSet(name))
			throw new IllegalArgumentException("Invalid group name specified.");
		try {
			return UserManager.getInstance().createGroup(name.toLowerCase());
		} catch (DuplicateEntityException e) {
			throw new RuntimeException(e);
		} catch (ImmutableException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see corner.orm.osuser.UserAccessor#removeGroup(com.opensymphony.user.Group)
	 */
	public void removeGroup(Group group) {
		try {
			User user;
			for (Enumeration members = group.members(); members
					.hasMoreElements(); group.removeUser(user))
				user = (User) members.nextElement();

			group.remove();
		} catch (ImmutableException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#removeUser(com.opensymphony.user.User)
	 */
	public void removeUser(User user) {
		try {
			removeUserFromAllGroups(user);
			user.remove();
		} catch (ImmutableException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#saveUser(com.opensymphony.user.User)
	 */
	public void saveUser(User user) {
		try {
			user.store();
		} catch (ImmutableException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getUsersByEmail(java.lang.String)
	 */
	public List getUsersByEmail(String email) {
		if (!TextUtils.stringSet(email))
			return Collections.EMPTY_LIST;
		Iterator it = UserManager.getInstance().getUsers().iterator();
		List<User> result = new ArrayList<User>();
		do {
			if (!it.hasNext())
				break;
			User user = (User) it.next();
			if (user.getEmail().equalsIgnoreCase(email))
				result.add(user);
		} while (true);
		return result;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getGroup(java.lang.String)
	 */
	public Group getGroup(String name) {
		if (!TextUtils.stringSet(name))
			return null;
		try {
			return UserManager.getInstance().getGroup(name);
		} catch (EntityNotFoundException e) {
			return null;
		}

	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getGroupCreateIfNecessary(java.lang.String)
	 */
	public Group getGroupCreateIfNecessary(String name) {
		if (!TextUtils.stringSet(name))
			throw new IllegalArgumentException("Invalid group name specified");
		try {
			return UserManager.getInstance().getGroup(name);
		} catch (EntityNotFoundException e) {
			return addGroup(name);
		}

	}

	protected void removeUserFromAllGroups(User user) {
		String groupName;
		for (Iterator i = user.getGroups().iterator(); i.hasNext(); getGroup(
				groupName).removeUser(user))
			groupName = (String) i.next();

	}

	/**
	 * @see corner.orm.osuser.UserAccessor#deactivateUser(com.opensymphony.user.User)
	 */
	public void deactivateUser(User user) {
		user.getPropertySet().setBoolean("atlassian.user.deactivated", true);
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#reactivateUser(com.opensymphony.user.User)
	 */
	public void reactivateUser(User user) {
		user.getPropertySet().setBoolean("atlassian.user.deactivated", false);
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getActiveUsers()
	 */
	public <T> List<User> getActiveUsers() {
		List<User> users = getUsers();
		List<User> activeUsers = new ArrayList<User>(users.size());
		Iterator<User> it = activeUsers.iterator();
		do {
			if (!it.hasNext())
				break;
			User user = (User) it.next();
			if (user.getPropertySet().getBoolean("atlassian.user.deactivated"))
				users.add(user);
		} while (true);
		return users;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#isLicensedToAddMoreUsers()
	 */
	public boolean isLicensedToAddMoreUsers() {
		return true;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getLicensingError()
	 */
	public String getLicensingError() {
		return null;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#isUserRemovable(com.opensymphony.user.User)
	 */
	public boolean isUserRemovable(User user) {
		return true;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getRemoveUserErrorMessage()
	 */
	public String getRemoveUserErrorMessage() {
		return null;
	}

	/**
	 * @see corner.orm.osuser.UserAccessor#getDefaultGroup()
	 */
	public String getDefaultGroup() {
		return "users";
	}
}