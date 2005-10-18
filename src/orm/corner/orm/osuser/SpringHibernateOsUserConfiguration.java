//==============================================================================
//file :        SpringHibernateOsUserConfiguration.java
//project:      poisoning
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.osuser;
import com.opensymphony.user.provider.hibernate.OSUserHibernateConfigurationProvider;
import com.opensymphony.user.provider.hibernate.dao.HibernateGroupDAO;
import com.opensymphony.user.provider.hibernate.dao.HibernateUserDAO;

import corner.orm.propertyset.SpringHibernatePropertySetConfigurationProvider;
import corner.orm.spring.SpringContainer;

/**
 * 提供对osuser的配置支持.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-12
 */
public class SpringHibernateOsUserConfiguration extends SpringHibernatePropertySetConfigurationProvider implements
		OSUserHibernateConfigurationProvider {

	public HibernateGroupDAO getGroupDAO() {
		return (HibernateGroupDAO) SpringContainer.getInstance()
				.getApplicationContext().getBean("osuser.dao.group");
	}

	public HibernateUserDAO getUserDAO() {
		return (HibernateUserDAO) SpringContainer.getInstance()
				.getApplicationContext().getBean("osuser.dao.user");
	}

	

}
