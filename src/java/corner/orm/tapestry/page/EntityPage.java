//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import java.io.Serializable;

import org.apache.tapestry.IPage;

/**
 * 
 * 基本的实体操作接口。
 * @author Jun Tsai
 * @since 0.1
 * @param <T> 实体类
 */
public interface EntityPage<T  extends Object> extends IPage {
	/**
	 * 得到实体。
	 * @return 实体。
	 */
	public  abstract <E extends T> E  getEntity();
	/***
	 * 设定实体。
	 * @param entity 实体。
	 */
	public abstract <E extends T> void  setEntity(E entity);

	/**
	 * 根据实体的主键，得到一个实体，并设定实体。
	 * @param key 主键值
	 */
	public abstract void loadEntity(Serializable key);
}
