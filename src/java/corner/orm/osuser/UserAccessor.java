//==============================================================================
//file :        UserAccessor.java
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

import java.util.List;

import com.opensymphony.user.Group;
import com.opensymphony.user.User;

/**
 * 用户操作接口。
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-12
 */
public interface UserAccessor {

	public abstract User addUser(String username, String password,
			String email, String fullname, String groups[]);

	
	public abstract <T> List<User> getUsers();

	public abstract User getUser(String name);

	public abstract List getGroups();

	public abstract Group addGroup(String name);

	public abstract void removeGroup(Group group);

	public abstract void removeUser(User user);

	public abstract void saveUser(User user);

	public abstract List getUsersByEmail(String email);

	public abstract Group getGroup(String name);

	public abstract Group getGroupCreateIfNecessary(String name);

	public abstract void deactivateUser(User user);

	public abstract void reactivateUser(User user);

	public abstract <T> List<User> getActiveUsers();

	public abstract boolean isLicensedToAddMoreUsers();

	public abstract String getLicensingError();

	public abstract boolean isUserRemovable(User user);

	public abstract String getRemoveUserErrorMessage();

	public abstract String getDefaultGroup();

}