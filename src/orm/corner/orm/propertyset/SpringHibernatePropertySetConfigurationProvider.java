//==============================================================================
//file :        SpringHibernateConfigurationProvider.java
//project:      poisoning
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.propertyset;

import java.util.Map;

import net.sf.hibernate.cfg.Configuration;

import com.opensymphony.module.propertyset.hibernate.HibernateConfigurationProvider;
import com.opensymphony.module.propertyset.hibernate.HibernatePropertySetDAO;

import corner.orm.spring.SpringContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpringHibernatePropertySetConfigurationProvider implements
		HibernateConfigurationProvider {

	private static final Log log = LogFactory
		.getLog(SpringHibernatePropertySetConfigurationProvider.class);

	public Configuration getConfiguration() {
		
		log.error("fail to get configuration!");
		return null;
	}

	public HibernatePropertySetDAO getPropertySetDAO() {
		return (HibernatePropertySetDAO) SpringContainer.getInstance().getApplicationContext().getBean("propertyset.dao");
	}

	public void setupConfiguration(Map arg0) {
	}

}
