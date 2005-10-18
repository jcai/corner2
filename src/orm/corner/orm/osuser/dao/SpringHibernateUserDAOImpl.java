package corner.orm.osuser.dao;

import java.io.Serializable;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import com.opensymphony.user.provider.hibernate.dao.HibernateQueries;
import com.opensymphony.user.provider.hibernate.dao.HibernateUserDAO;
import com.opensymphony.user.provider.hibernate.entity.HibernateUser;

public class SpringHibernateUserDAOImpl extends HibernateDaoSupport implements
		HibernateUserDAO {

	public int deleteUserByUsername(String username) {
		return getHibernateTemplate().delete(HibernateQueries.USER_BY_USERNAME,
				username, Hibernate.STRING);
	}

	public HibernateUser findUserByUsername(String username) {
		HibernateUser user = null;
		List ret = getHibernateTemplate().find(
				HibernateQueries.USER_BY_USERNAME, username, Hibernate.STRING);

		if (ret.size() > 0) {
			user = (HibernateUser) ret.get(0);
		}

		return user;
	}

	public HibernateUser findUserByUsernameAndGroupname(String userName,
			String groupName) {
		HibernateUser user = null;

		List results = getHibernateTemplate().find(
				HibernateQueries.USER_BY_USERNAME_AND_GROUPNAME,
				new Object[] { userName, groupName },
				new Type[] { Hibernate.STRING, Hibernate.STRING });

		if (results.size() == 0) {
			return null;
		}
		user = (HibernateUser) results.get(0);
		return user;
	}

	public List findUsers() {
		return getHibernateTemplate().find(HibernateQueries.ALL_USERS);
	}

	public boolean saveUser(HibernateUser user) {
		Serializable id = null;
		id = getHibernateTemplate().save(user);
		return (id != null);
	}

	public boolean updateUser(HibernateUser user) {
		getHibernateTemplate().saveOrUpdate(user);
		return true;
	}

}
