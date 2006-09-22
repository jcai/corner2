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

package corner.orm.tapestry.page;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
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
public class EntityPageTest extends BaseComponentTestCase {
	@Test
	public void testEntityPage() throws Exception{
		final Registry reg = buildFrameworkRegistry(new String[]{});
        
        
        
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
        IRequestCycle request=(IRequestCycle) reg.getService("tapestry.globals.IRequestCycle", IRequestCycle.class);
        
        assertNotNull(request);
        
        AbstractEntityFormPage page=(AbstractEntityFormPage) newInstance(AbstractEntityFormPage.class,new Object[]{"entityService",entityService,"pageName","AForm"});
        
        page.setEntity(new A());
        //page.doSaveEntityAction();
        
        
        
	}

}
