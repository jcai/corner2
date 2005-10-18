package corner.orm.osuser.dao;

import java.io.Serializable;
import java.util.List;

import net.sf.hibernate.Hibernate;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import com.opensymphony.user.provider.hibernate.dao.HibernateGroupDAO;
import com.opensymphony.user.provider.hibernate.dao.HibernateQueries;
import com.opensymphony.user.provider.hibernate.entity.HibernateGroup;

public class SpringHibernateGroupDAOImpl extends HibernateDaoSupport implements
		HibernateGroupDAO {

	// ~ Methods
	// ////////////////////////////////////////////////////////////////

	public int deleteGroupByGroupname(String groupname) {
		return getHibernateTemplate().delete(
				HibernateQueries.GROUP_BY_GROUPNAME, groupname,
				Hibernate.STRING);
	}

	public HibernateGroup findGroupByGroupname(String groupname) {
		HibernateGroup group = null;
		List ret = getHibernateTemplate().find(
				HibernateQueries.GROUP_BY_GROUPNAME, groupname,
				Hibernate.STRING);

		if (ret.size() > 0) {
			group = (HibernateGroup) ret.get(0);
		}

		return group;
	}

	public List findGroups() {
		return getHibernateTemplate().find(HibernateQueries.ALL_GROUPS);
	}

	public boolean saveGroup(HibernateGroup group) {
		Serializable id =getHibernateTemplate().save(group);
		return (id!=null);
	}
}
