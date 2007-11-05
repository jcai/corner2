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

package corner.orm.tapestry.component.insert;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.form.IFormComponent;

import corner.orm.tapestry.page.EntityPage;
import corner.service.EntityService;
import corner.service.svn.IVersionProvider;
import corner.service.svn.IVersionable;

/**
 * 版本管理器，用来返回相应的版本json串
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionManage extends BaseComponent implements IFormComponent,IVersionProvider{
	
	@InjectScript("VersionManage.script")
	public abstract IScript getScript();
	
	
	private static String NULL_JSON = "{\"entity\":{\"id\":\"\"}}";
	
	/**
	 * 得到EntityService.
	 * <p>提供基本的操作.
	 * @return entityService 实体服务类
	 * @since 2.0
	 */
	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderComponent(writer, cycle);
		
		if (!cycle.isRewinding()) {
			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
			Map<String, String> parms = new HashMap<String, String>();
			
			EntityPage page = (EntityPage)getPage().getRequestCycle().getPage();
			
			Object entity = page.getEntity();
			long v = ((IVersionProvider)page).getVersionNum();
			
			if(v == 0) {v = -1;}
			
			parms.put("json", getEntityService().isPersistent(entity)? this.getSubversionService().fetchObjectAsJson((IVersionable) entity, v):NULL_JSON);
			getScript().execute(this, cycle, prs, parms);
		}
	}
}