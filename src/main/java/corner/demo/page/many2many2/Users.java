//==============================================================================
//file :        Users.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================
package corner.demo.page.many2many2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ghost
 *
 */
public class Users {
	
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(Users.class);	
	/**
	 * 定义一个静态列表
	 */
	public static List<User> USER_LIST = new ArrayList<User>();
	public List<User> users;
	/**
	 * @return Returns the users.
	 */
	public List<User> getUsers() {
		return users;
	}
	/**
	 * @param users The users to set.
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Users(){
		users = new ArrayList<User>();
		User u1 = new User();
		u1.setId("1");
		u1.setUserName("cj");
		u1.setCnName("蔡君");
		logger.debug("caijun.hashCode:"+u1.hashCode());
		User u2 = new User();
		u2.setId("2");
		u2.setUserName("syh");
		u2.setCnName("宋艳豪");
		User u3 = new User();
		u3.setId("3");
		u3.setUserName("jzr");
		u3.setCnName("籍周润");
		User u4 = new User();
		u4.setId("4");
		u4.setUserName("zx");
		u4.setCnName("赵鑫");
		User u5 = new User();
		u5.setId("5");
		u5.setUserName("cj1");
		u5.setCnName("蔡君1");
		User u6 = new User();
		u6.setId("6");
		u6.setUserName("cj2");
		u6.setCnName("蔡君2");
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		users.add(u6);
	}
	
}
