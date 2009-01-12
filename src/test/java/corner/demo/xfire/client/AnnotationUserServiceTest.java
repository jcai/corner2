//==============================================================================
//file :        AnnotationUserServiceTest.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.client;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.xfire.service.AnnotationUserService;
import corner.demo.xfire.service.impl.AnnotationUserServiceImpl;
import corner.orm.tapestry.service.xfire.utils.XFireClientFactory;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class AnnotationUserServiceTest extends Assert {

	private String serviceURL = "http://127.0.0.1:8080/webservice/AnnotationUserService.xfire";

	@Test
	public void testAnnotationUserService() {
		AnnotationUserService service = null;
		if (service == null)
			service = XFireClientFactory.getJSR181Client(serviceURL, AnnotationUserService.class, AnnotationUserServiceImpl.class);
		
			List<String> list = service.findStaticUsers();
	        if(list != null && list.size()>0){
	        	for(String s:list){
	        		System.out.println("hello:"+s);
	        	}
	        } else {
	        	System.out.println("no static user here!");
	        }
	}

}
