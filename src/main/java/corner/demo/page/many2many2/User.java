/**
 * 
 */
//==============================================================================
//file :        User.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================
package corner.demo.page.many2many2;

import java.io.Serializable;

/**
 * @author Ghost
 *
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7691365126310346087L;

	/**
	 * 默认构造方法
	 */
	public User(){
		
	}
	
	/**
	 * 带有两个参数的构造函数
	 * @param userName
	 * @param password
	 */
	public User(String userName,String password){
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * User实体主键
	 */
	private String id;
	
	/**
	 * User实体的名称
	 */
	private String userName;
	
	/**
	 * User实体的密码
	 */
	private String password;
	
	/**
	 * User中文名称
	 */
	private String cnName;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the cnName.
	 */
	public String getCnName() {
		return cnName;
	}

	/**
	 * @param cnName The cnName to set.
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
