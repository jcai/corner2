//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

public class AbstractCornerTest extends AbstractTransactionalDataSourceSpringContextTests {
	protected EntityService entityService;
	
	@Override
	protected String[] getConfigLocations() {
		
		return new String[]{"classpath:/config/spring/application-*.xml"};
		
	}
	protected ConfigurableApplicationContext loadContextLocations(String[] locations) {
		
		return (ConfigurableApplicationContext) SpringContainer.getInstance().getApplicationContext();
	}
	/**
	 * @return Returns the entityService.
	 */
	public EntityService getEntityService() {
		return entityService;
	}
	/**
	 * @param entityService The entityService to set.
	 */
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

}
