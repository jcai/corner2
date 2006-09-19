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

package corner.orm.tapestry.component;

import java.util.Map;

import corner.service.EntityService;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1.1
 */
public interface ISelectFilter {

	/**
	 * 查询的起始位置
	 */
	public static final int nFirst = 0;
	/**
	 * 每次查询的数量，控制每次查询只查找前20条纪录
	 */
	public static final int nPageSize = 20;	
	
	/**
	 * 
	 * @param match
	 * @return
	 */
	public Map filterValues(String match) ;
	
	/**
	 * 
	 * @param entityService
	 */
	public void setEntityService(EntityService entityService);
	
	/**
	 * 
	 * @return
	 */
	public EntityService getEntityService();
	
	public void setLabel(String label);
	
	public String getLabel();
	
	public void setCnLabel(String cnLabel);
	
	public String getCnLabel();
	
	public void setQueryClass(Class queryClass);
	
	public Class getQueryClass();
}
