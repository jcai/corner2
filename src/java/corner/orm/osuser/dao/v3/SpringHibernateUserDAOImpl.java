package corner.orm.osuser.dao.v3;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.user.provider.hibernate.dao.HibernateQueries;
import com.opensymphony.user.provider.hibernate.dao.HibernateUserDAO;
import com.opensymphony.user.provider.hibernate.entity.HibernateUser;

public class SpringHibernateUserDAOImpl extends HibernateDaoSupport implements
		HibernateUserDAO {

	public int deleteUserByUsername(String username) {
		try{
			getHibernateTemplate().delete( findUserByUsername(username));
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public HibernateUser findUserByUsername(String username) {
		HibernateUser user = null;
		List ret = getHibernateTemplate().findByNamedQuery(
				HibernateQueries.USER_BY_USERNAME, username);

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
				new Object[] { userName, groupName });

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
