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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import corner.service.EntityService;

/**
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.2.1
 */
public class CornerPageTestCase extends BaseComponentTestCase {
	protected Registry reg;
	protected EntityService entityService;
	@BeforeMethod
	public void buildFrameRegistry() throws Exception{
		reg = buildFrameworkRegistry(new String[]{});
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
    	
	}
	@AfterMethod
	public void closeRegistry(){
		reg.shutdown();
	}
}
