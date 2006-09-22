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

package corner.orm.tapestry.service.blob;

import java.io.ByteArrayInputStream;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.service.EntityService;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public class SqueezeBlobPageDelegateTest extends BaseComponentTestCase {
	@Test
	public void testSaveBlob() throws Exception{
		IUploadFile file=EasyMock.createMock(IUploadFile.class);
		EasyMock.expect(file.getStream()).andReturn(new ByteArrayInputStream("string".getBytes()));
		EasyMock.expect(file.getContentType()).andReturn("text/plain");
		
		final Registry reg = buildFrameworkRegistry(new String[]{});
		SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        EasyMock.replay(file);
        
        
		IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
				A.class, file, new A(),entityService);
		delegate.save();
        
        EasyMock.verify(file);
	}
}
