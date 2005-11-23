package corner.orm.osuser.dao.v3;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.user.provider.hibernate.dao.HibernateGroupDAO;
import com.opensymphony.user.provider.hibernate.dao.HibernateQueries;
import com.opensymphony.user.provider.hibernate.entity.HibernateGroup;

public class SpringHibernateGroupDAOImpl extends HibernateDaoSupport implements
		HibernateGroupDAO {

	// ~ Methods
	// ////////////////////////////////////////////////////////////////

	public int deleteGroupByGroupname(String groupname) {
		try{
			getHibernateTemplate().delete(findGroupByGroupname(groupname));
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	public HibernateGroup findGroupByGroupname(String groupname) {
		HibernateGroup group = null;
		List ret = getHibernateTemplate().findByNamedQuery(HibernateQueries.GROUP_BY_GROUPNAME, groupname);

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
