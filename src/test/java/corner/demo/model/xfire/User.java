/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: User.java 3928 2008-01-15 03:28:16Z jcai $
 * created at:2007-07-13
 */

package corner.demo.model.xfire;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 * 
 * 用户信息表(ML_S_USER).
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3928 $
 * @since 0.0.1
 * @hibernate.class table="ML_S_USER" comment="用户信息表"
 * @hibernate.cache usage="read-write"
 * @hibernate.meta attribute="class-description" value="用户信息表(ML_S_USER)."
 */
@Entity(name="ML_S_USER")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User extends AbstractModel {

	private static final long serialVersionUID = 8994249149120946344L;
	

	/**
	 * 用户部门名称
	 * 
	 * @hibernate.property
	 * @hibernate.column name="DEPARTMENT_NAME" comment="用户部门名称." length="100"
	 * @hibernate.meta attribute="field-description" value="用户部门名称"
	 */
	private String departmentName;
	
	/**
	 * 用户部门编码
	 * 
	 * @hibernate.property
	 * @hibernate.column name="DEPARTMENT_NO" comment="用户部门编码." length="3"
	 * @hibernate.meta attribute="field-description" value="用户部门编码"
	 */
	private String departmentNo;

	/**
	 * 联系电话
	 * 
	 * @hibernate.property
	 * @hibernate.column name="TEL_NO" comment="联系电话." length="20"
	 * @hibernate.meta attribute="field-description" value="联系电话"
	 */
	private String telNo;

	/**
	 * 移动电话
	 * 
	 * @hibernate.property
	 * @hibernate.column name="MOBILE_NO" comment="移动电话." length="20"
	 * @hibernate.meta attribute="field-description" value="移动电话"
	 */
	private String mobileNo;

	/**
	 * 简要说明
	 * 
	 * @hibernate.property
	 * @hibernate.column name="SHORT_DESC" comment="简要说明." length="2000"
	 * @hibernate.meta attribute="field-description" value="简要说明"
	 */
	private String shortDesc;

	/**
	 * 用户登录的用户名
	 * 
	 * @hibernate.property
	 * @hibernate.column name="LOGIN_NAME" comment="用户登录的用户名." length="100"
	 * @hibernate.meta attribute="field-description" value="用户登录的用户名"
	 */
	private String loginName;

	/**
	 * 电子邮件
	 * 
	 * @hibernate.property
	 * @hibernate.column name="EMAIL" comment="电子邮件." length="50"
	 * @hibernate.meta attribute="field-description" value="电子邮件"
	 */
	private String email;

	/**
	 * 用户密码,PassWord,Char(20).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="PASS_WORD" comment="用户密码." length="20"
	 * @hibernate.meta attribute="field-description"
	 *                 value="用户密码,PassWord,Char(20)."
	 */
	private String passWord;

	/**
	 * 联系电话,TelNo,Varchar(80).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="USER_NAME" comment="联系电话." length="20"
	 * @hibernate.meta attribute="field-description"
	 *                 value="联系电话,TelNo,Varchar(80)."
	 */
	private String userName;

	/**
	 * 用户类型,userType,Char(1).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="USER_TYPE" comment="用户类型." length="1"
	 * @hibernate.meta attribute="field-description"
	 *                 value="用户类型,User_Type,Char(1)."
	 */
	private String userType;

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @see com.bjmaxinfo.model.IPasswd#getPassWord()
	 */
	public String getPassWord() {
		return this.passWord;
	}

	/**
	 * @see com.bjmaxinfo.model.IPasswd#setPassWord(java.lang.String)
	 */
	public void setPassWord(String PassWord) {
		this.passWord = PassWord;
	}


	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}


	/**
	 * @return Returns the departmentName.
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            The departmentName to set.
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the shortDesc.
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * @param shortDesc
	 *            The shortDesc to set.
	 */
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * @return Returns the telNo.
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo
	 *            The telNo to set.
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	/**
	 * @return Returns the mobileNo.
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo
	 *            The mobileNo to set.
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	/**
	 * @return Returns the departmentNo.
	 */
	public String getDepartmentNo() {
		return departmentNo;
	}

	/**
	 * @param departmentNo The departmentNo to set.
	 */
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	
    /* bean properties begin */
    public static final String SYS_ROLES_PRO_NAME="sysRoles";
    public static final String USER_DEPARTMENT_PRO_NAME="userDepartment";
    public static final String DEPARTMENT_NAME_PRO_NAME="departmentName";
    public static final String DEPARTMENT_NO_PRO_NAME="departmentNo";
    public static final String TEL_NO_PRO_NAME="telNo";
    public static final String MOBILE_NO_PRO_NAME="mobileNo";
    public static final String SHORT_DESC_PRO_NAME="shortDesc";
    public static final String LOGIN_NAME_PRO_NAME="loginName";
    public static final String EMAIL_PRO_NAME="email";
    public static final String PASS_WORD_PRO_NAME="passWord";
    public static final String USER_NAME_PRO_NAME="userName";
    public static final String USER_TYPE_PRO_NAME="userType";
    /* bean properties end */

}
