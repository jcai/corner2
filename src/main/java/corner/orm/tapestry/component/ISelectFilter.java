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
 * 提供自定义查询方法的接口
 * <p>使用CornerSelect可以根据自己的需求定义查询方法类，但是该类必须实现本接口，如需复写，请参考DefaultSelectFilter</p>
 * @author Ghost
 * @version $Revision$
 * @since 2.1.1
 * @deprecated
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
	 * 根据用户的输入返回结果
	 * @param match
	 * @return
	 */
	public Map filterValues(String match) ;
	
	/**
	 * 设置EntityService
	 * @param entityService
	 */
	public void setEntityService(EntityService entityService);
	
	/**
	 * 取得EntityService
	 * @return
	 */
	public EntityService getEntityService();
	
	/**
	 * 设置label
	 * @param label
	 */
	public void setLabel(String label);
	
	/**
	 * 返回label
	 * @return
	 */
	public String getLabel();
	
	/**
	 * 设置cnlabel
	 * @param cnLabel
	 */
	public void setCnLabel(String cnLabel);
	
	/**
	 * 返回cnlabel
	 * @return
	 */
	public String getCnLabel();
	
	/**
	 * 设置查询实体类
	 * @param queryClass
	 */
	public void setQueryClass(Class queryClass);
	
	/**
	 * 返回查询实体类
	 * @return
	 */
	public Class getQueryClass();
}
