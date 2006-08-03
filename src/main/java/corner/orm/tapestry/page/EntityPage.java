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

package corner.orm.tapestry.page;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectObject;

import corner.service.EntityService;

/**
 *
 * 基本的实体操作接口。
 * @author Jun Tsai
 * @since 0.1
 * @param <T> 实体类
 */
public interface EntityPage<T  > extends IPage {
	/**
	 * 得到实体。
	 * @return 实体。
	 */
	public  abstract T getEntity();
	/***
	 * 设定实体。
	 * @param entity 实体。
	 */
	public abstract  void  setEntity(T entity);

	
	/**
	 * 得到EntityService.
	 * <p>提供基本的操作.
	 * @return entityService 实体服务类
	 * @since 2.0
	 */
	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();
	

}
