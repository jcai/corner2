//==============================================================================
//file :        UserServiceTest.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.client;

import java.net.MalformedURLException;
import java.util.List;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.xfire.User;
import corner.demo.xfire.service.UserService;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class UserServiceTest extends Assert {

	@Test
	public void testUserService(){
		String serviceURL = "http://127.0.0.1:8080/webservice/UserService.xfire";
        Service serviceModel = new ObjectServiceFactory().create(UserService.class,null,serviceURL,null);
        XFireProxyFactory serviceFactory = new XFireProxyFactory();
        try
        {
        	UserService service = (UserService) serviceFactory.create(serviceModel, serviceURL);
            Client client = Client.getInstance(service);
            List<String> list = service.findStaticUsers();
            if(list != null && list.size()>0){
            	for(String s:list){
            		System.out.println("hello:"+s);
            	}
            } else {
            	System.out.println("no static user here!");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
	}
	
	@Test
	public void dynamicServiceTest(){
		String serviceURL = "http://127.0.0.1:8080/webservice/UserService.xfire";
        Service serviceModel = new ObjectServiceFactory().create(UserService.class,null,serviceURL,null);
        XFireProxyFactory serviceFactory = new XFireProxyFactory();
        try
        {
        	UserService service = (UserService) serviceFactory.create(serviceModel, serviceURL);
            Client client = Client.getInstance(service);
            User u = new User();
//            u.setLoginName("ghostbb@bjmaxinfo.com");
//            u.setPassWord("password");
            service.addUserAction("ghostbb@bjmaxinfo.com", "password");
            u = null;
            u = service.searchUserByLoginName("ghostbb@bjmaxinfo.com");
            if(u != null){
            	System.out.println("load user :"+u.getLoginName());
            } else {
            	System.out.println("u is null!");
            }
            
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
	}
}
