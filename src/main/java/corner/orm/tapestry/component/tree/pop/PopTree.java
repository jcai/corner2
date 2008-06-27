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

package corner.orm.tapestry.component.tree.pop;

import java.text.ParseException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;

import corner.model.tree.ITreeAdaptor;
import corner.orm.tapestry.component.tree.BaseLeftTree;
import corner.orm.tapestry.component.tree.ITreeSelectModel;



/**
 * 弹出窗
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class PopTree extends BaseLeftTree{
	
	/** 选择的过滤器 **/
	@Parameter(defaultValue="ognl:new corner.orm.tapestry.component.tree.pop.PopTreeSelectModel()")
	public abstract ITreeSelectModel getSelectModel();
	
	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#getScript()
	 */
	@InjectScript("PopTree.script")
	public abstract IScript getScript();
	
	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#constructSelectModel()
	 */
	@Override
	protected ITreeSelectModel constructSelectModel() {
		return this.getSelectModel();
	}

	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#initTreeSelectModel(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected ITreeSelectModel initTreeSelectModel(IRequestCycle cycle) {
		ITreeSelectModel model = super.initTreeSelectModel(cycle);
		model.setParentPage(cycle.getPage(cycle.getParameter("parentPage").trim()));
		try {
			model.setReturnValues(new JSONObject(getReturnValues()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#leftTreeNodeJson(corner.orm.tapestry.component.tree.ITreeSelectModel)
	 */
	protected JSONObject leftTreeNodeJson(ITreeSelectModel model) {
		JSONArray jsonArr = new JSONArray();
		
		JSONObject subjson = null;
		
		JSONObject datejson = null;
		
		for(ITreeAdaptor node : model.getTreeList()){
			subjson = new JSONObject();
			subjson.put("id", node.getId());
			subjson.put("type", "leftTreeSite");
			
			datejson = new JSONObject();
			datejson.put("name", node.getNodeName());
			datejson.put("left", node.getLeft());
			datejson.put("right", node.getRight());
			datejson.put("depth", node.getDepth());
			datejson.put("thisEntity", this.getDataSqueezer().squeeze(node));
			
			if(model.getReturnValues() != null){	//如果不等于空则反抓出数据
				returnPropertys(datejson,node,model.getReturnValues());
			}
			
			subjson.put("data", datejson);
			
			jsonArr.put(subjson);
		}
		
		JSONObject json = new JSONObject();
		json.put("nodes", jsonArr);
		return json;
	}
}
