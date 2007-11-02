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

package corner.demo.page.gainPoint;

import java.util.List;

import org.apache.tapestry.annotations.Persist;

import corner.orm.tapestry.page.PoListPage;
import corner.service.svn.IVersionProvider;
import corner.service.svn.IVersionable;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AVersionListPage <V extends IVersionable> extends PoListPage implements IVersionProvider{
	/**
	 * 得到实体。
	 * @return 实体。
	 */
	@Persist("entity")
	public abstract V getVersionEntity();
	/***
	 * 设定实体。
	 * @param entity 实体。
	 */
	public abstract  void  setVersionEntity(V entity);
	
	public List getVersionInfo(){
		return this.getSubversionService().fetchVersionInfo(getVersionEntity());
	}
}
