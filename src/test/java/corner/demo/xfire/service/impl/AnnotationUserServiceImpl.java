//==============================================================================
//file :        UserServiceImpl.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.demo.model.xfire.User;
import corner.demo.xfire.service.AnnotationUserService;
import corner.service.EntityService;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
@WebService(serviceName = "AnnotationUserService", endpointInterface = "corner.demo.xfire.service.AnnotationUserService")
public class AnnotationUserServiceImpl extends EntityService implements AnnotationUserService{

	/**
	 * @see corner.demo.xfire.service.AnnotationUserService#addUserAction(java.lang.String, java.lang.String)
	 */
	public void addUserAction(String loginName, String passWord) {
		User u = new User();
		u.setLoginName(loginName);
		u.setPassWord(passWord);
		this.saveOrUpdate(u);
		this.flush();
	}

	/**
	 * @see com.bjmaxinfo.webservice.UserService#deleteUserAction(java.lang.String)
	 */
	public void deleteUserAction(String id) {
		User u = this.load(User.class, id);
		this.delete(u);
	}

	/**
	 * @see com.bjmaxinfo.webservice.UserService#updateUserAction(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateUserAction(String id, String loginName, String passWord) {
		User u = this.load(User.class, id);
		u.setLoginName(loginName);
		u.setPassWord(passWord);
		this.save(u);
	}
	
	/**
	 * @see com.bjmaxinfo.webservice.UserService#findStaticUsers()
	 */
	public List<String> findStaticUsers(){
		List<String> list = new ArrayList<String>();
		list.add("Ghostbb");
		list.add("xf");
		list.add("lsq");
		return list;
	}

	/**
	 * @see com.bjmaxinfo.webservice.UserService#searchUserByLoginName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public User searchUserByLoginName(final String loginName) {
		List<User> list = this.executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(User.class);
				criteria.add(Restrictions.eq(User.LOGIN_NAME_PRO_NAME, loginName));
				return criteria.list();
			}
			
		});
		if(list.size() >0){
			return list.get(0);
		} else {
			return null;
		}
	}

}
