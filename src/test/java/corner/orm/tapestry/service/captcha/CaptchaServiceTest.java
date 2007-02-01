//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.captcha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;
import org.apache.tapestry.web.WebSession;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public class CaptchaServiceTest extends BaseComponentTestCase{

	@Test
	public void test_captcha() throws IOException{
		IRequestCycle cycle=newCycle();
		WebResponse response=newMock(WebResponse.class);
		WebRequest request = newRequest();
		WebSession session=newMock(WebSession.class);
		
//		EasyMock.expect(request.getSession(true)).andReturn(session);
		
//		EasyMock.expect(session.getId()).andReturn("sessionid");
		
		response.setContentLength(EasyMock.anyInt());
		FileOutputStream out=new FileOutputStream(new File("target/test.jpg"));
		EasyMock.expect(response.getOutputStream( EasyMock.isA(ContentType.class))).andReturn(out);
		
		replay();
		CaptchaService service=new CaptchaService(){

			@Override
			protected void recordRandomString(String randomStr) {
				//do nothing
			}
			
		};
		service.setResponse(response);
		service.setRequest(request);
		
		service.service(cycle);
		verify();
		
	}
	
	
}
