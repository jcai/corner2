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

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Persist;

import corner.orm.tapestry.page.PoListPage;
import corner.service.svn.IVersionProvider;
import corner.service.svn.IVersionable;
import corner.service.svn.VersionResult;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public  abstract class AVersionListPage extends PoListPage implements IVersionProvider{
	/**
	 * 得到实体。
	 * @return 实体。
	 */
	@Persist("entity")
	public abstract <V extends IVersionable> V getVersionEntity();
	/***
	 * 设定实体。
	 * @param entity 实体。
	 */
	public abstract <V extends IVersionable>  void  setVersionEntity(V entity);
	
	public List getVersionInfo(){
		return this.getSubversionService().fetchVersionInfo(getVersionEntity());
	}
	
	public IPage doDifferencePage(VersionResult entity){
		AFormPage page = (AFormPage) this.getRequestCycle().getPage("gainPoint/AVersionForm");
		page.setEntity(getVersionEntity());
		page.setVersionNum(entity.getVersionNum());
		return page;
	}
	
	public IPage doViewChanges(){
		String [] ver = getCompare().split(",");
		AFormPage page = (AFormPage) this.getRequestCycle().getPage("gainPoint/AVersionForm");
		page.setEntity(getVersionEntity());
		page.setVersionNum(Long.valueOf(ver[0]));
		page.setOtherVersionNum(Long.valueOf(ver[1]));
		return page;
	}
	
	public abstract String getCompare();
	public abstract void setCompare(String compare);
}
