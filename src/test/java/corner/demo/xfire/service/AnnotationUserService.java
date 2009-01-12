//==============================================================================
//file :        IUserManager.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.service;

import java.util.List;

import javax.jws.WebService;

import corner.demo.model.xfire.User;

/**
 * 用户管理Service接口
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
@WebService(targetNamespace = "http://localhost/webservice")
public interface AnnotationUserService {

	public void addUserAction(String loginName, String passWord);
	
	public void updateUserAction(String uId, String loginName, String passWord);
	
	public void deleteUserAction(String uId);
	
	public User searchUserByLoginName(String loginName);
	
	public List<String> findStaticUsers();
}
