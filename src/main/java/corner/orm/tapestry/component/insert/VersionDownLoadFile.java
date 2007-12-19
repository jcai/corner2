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
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;

import corner.orm.tapestry.page.EntityPage;
import corner.service.EntityService;
import corner.service.svn.IVersionProvider;
import corner.service.svn.IVersionable;

/**
 * 显示指定版本文件信息
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionDownLoadFile extends BaseComponent{
	@InjectScript("VersionDownLoadFile.script")
	public abstract IScript getScript();
	
	/**
	 * 得到EntityService.
	 * <p>提供基本的操作.
	 * @return entityService 实体服务类
	 * @since 2.0
	 */
	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();
	
	/**
	 * @return
	 */
	@InjectObject("engine-service:versionDownLoads")
	public abstract IEngineService getVersionService();
	
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
			
			IVersionable entity = (IVersionable) page.getEntity();
			
			IVersionProvider conf = (IVersionProvider)page;
			
			long v1 = conf.getVersionNum();
			long v2 = conf.getOtherVersionNum();
			
			if(v1 == 0) {v1 = -1;}
			
			String link1 = null;
			String link2 = null;
			String link1show = null;
			String link2show = null;
			
			
			if(conf.isCompareLastVer()){	//如果是与不提交文件对比
				v2 = -1;	//获得最新的文件
				link1 = getLinkUrl(entity,v2);
				link2 = getLinkUrl(entity,-2);	//表示获得数据库中保存的文件
				
				link1show = "版本 " + entity.getRevision() + " 查看";
				link2show = "当前版本 查看";
			}else{
				link1 = getLinkUrl(entity,v1);
				link2 = getLinkUrl(entity,v2);
				if(v2 == 0){
					link1show = "查看";
				}else{
					link1show = "版本 " + conf.getVersionShowNum() + " 查看";
					link2show = "版本 " + conf.getOtherVersionShowNum() + " 查看";
				}
			}
			
			parms.put("link1", link1);
			parms.put("link2", link2);
			parms.put("link1show", link1show);
			parms.put("link2show", link2show);
			
			getScript().execute(this, cycle, prs, parms);
		}
	}
	
	/**
	 * 是否需要显示功能按钮，如果是返回true，反之亦然
	 */
	public boolean isShowButton(){
		IPage page = getPage().getRequestCycle().getPage();
		
		IVersionProvider conf = (IVersionProvider)page;
		
		long v2 = conf.getOtherVersionNum();
		
		if(v2 == 0){
			if(conf.isCompareLastVer())
				return true;
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 获得相应版本的Url
	 * @param entity 实体
	 * @param v 版本号
	 */
	private String getLinkUrl(IVersionable entity, long v) {
		Object[] obj = new Object[2];
		obj[0] = entity;
		obj[1] = v;
		ILink link = this.getVersionService().getLink(true, obj);
		return link.getURL();
	}
}
