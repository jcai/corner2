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
import corner.service.svn.VersionSaveUpdateEventListener;
import corner.service.svn.XStreamDelegate;
import corner.util.StringUtils;

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
			
			IVersionable entity = (IVersionable) page.getEntity();
			
			IVersionProvider conf = (IVersionProvider)page;
			
			long v1 = conf.getVersionNum();
			long v2 = conf.getOtherVersionNum();
			
			if(v1 == 0) {v1 = -1;}
			
			String json1 = null;
			String json2 = null;
			
			if(conf.isCompareLastVer()){
				v2 = Long.valueOf(StringUtils.replace(
						entity.getRevision(), VersionSaveUpdateEventListener.UNREVISION_VERSION, "")
						);
				
				json1 = XStreamDelegate.toJSON(entity);
				json2 = getJsonVersion(entity,v2);
			}else{
				json1 = getJsonVersion(entity,v1);
				json2 = getJsonVersion(entity,v2);
			}
			
			appendShowElement(writer, entity, conf, v1, v2);
			
			parms.put("json", json1);
			parms.put("json2", json2);
			getScript().execute(this, cycle, prs, parms);
		}
	}

	/**
	 * 加入信息
	 * @param writer
	 * @param v1
	 * @param v2
	 */
	private void appendShowElement(IMarkupWriter writer,IVersionable entity, IVersionProvider conf , long v1, long v2) {
		
		String v2show = String.valueOf(v2);
		
		if(conf.isCompareLastVer()){
			writer.print("当前 版本 与  版本: " + v2 + " 对比");
		}else {
			if(v2 == 0){
				writer.print("版本:" + v1);
				v2show = "";
			}else{
				writer.print("版本: " + v1 +" 与  版本: " + v2 + " 对比");
			}
		}
		
		writer.begin("div");
		writer.attribute("class", "diff");
			writer.begin("div");
			writer.attribute("id", "legend");
				writer.begin("dl");
					writer.begin("dt");
					writer.attribute("class", "add");
					writer.end("dt");
					writer.begin("dd");
					writer.print("Add");
					writer.end("dd");
					
					writer.begin("dt");
					writer.attribute("class", "mod");
					writer.end("dt");
					writer.begin("dd");
					writer.print("Edit");
					writer.end("dd");
					
					writer.begin("dt");
					writer.attribute("class", "cp");
					writer.end("dt");
					writer.begin("dd");
					writer.print("Delete");
					writer.end("dd");
				writer.end("dl");
			writer.end("div");
		writer.end("div");
		
		writer.begin("input");
		writer.attribute("type", "hidden");
		writer.attribute("id", "ver_hid");
		writer.attribute("value", String.valueOf(v1));
		writer.end("input");
		
		writer.begin("input");
		writer.attribute("type", "hidden");
		writer.attribute("id", "otherVer_hid");
		writer.attribute("value", v2show);
		writer.end("input");
		
	}

	/**
	 * 获得相应版本的json串
	 * @param entity 实体
	 * @param v 版本号
	 * @return 返回的json串
	 */
	private String getJsonVersion(IVersionable entity, long v) {
		
		if(v == 0){
			return NULL_JSON;
		}
		
		return getEntityService().isPersistent(entity)? this.getSubversionService().fetchObjectAsJson(entity, v):NULL_JSON;
	}
}