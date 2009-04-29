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

package corner.orm.tapestry.component.tree.tafeltree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;
import corner.model.tree.ITreeAdaptor;
import corner.service.tree.TreeService;
import corner.service.tree.tafeltree.ITafelTreeStructService;

/**
 * @author <a href=mailto:wlh@bjmaxinfo.com>wlh</a>
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class TafelTree extends BaseComponent {

	@InjectObject("spring:treeService")
	public abstract TreeService getTreeService();

	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#getScript()
	 */
	@InjectScript("TafelTree.script")
	public abstract IScript getScript();

	@Parameter(defaultValue = "literal:myTree")
	public abstract String getTreeId();

	@Parameter(defaultValue = "literal:/images/treeImgs/")
	public abstract String getImgBase();
	
	@Parameter(defaultValue = "literal:root_1")
	public abstract String getRootText();

	@Parameter
	public abstract String getExpendElementId();

	@Parameter
	public abstract String getCollapseElementId();

	@Parameter
	public abstract ITafelTreeStructService getConstructor();

	@Parameter(required = true)
	public abstract String getQueryClassName();

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);
		Map<String, Object> parms = new HashMap<String, Object>();

		parms.put("treeId", this.getTreeId());
		parms.put("imgBase", this.getImgBase());

		parms.put("expendElementId", this.getExpendElementId());
		parms.put("collapseElementId", this.getCollapseElementId());

		parms.put("treeStruct", this.getTreeJsonStr());
		parms.put("treeConfig", this.getTreeCfgJsonStr());

		getScript().execute(this, cycle, pageRenderSupport, parms);
	}

	/**
	 * 树的一些配置信息
	 * @return
	 */
	private String getTreeCfgJsonStr() {
		if (this.getConstructor() == null
				|| this.getConstructor().getTreeConfig() == null) {
			// width default value : 100%
			// height default value : auto
			return "{'generate' : true," + "'imgBase' : '" + this.getImgBase()
					+ "'," + "'defaultImg' : 'page.gif',"
					+ "'defaultImgOpen' : 'folderopen.gif',"
					+ "'defaultImgClose' : 'folder.gif'," + "'width' : '100%',"
					+ "'height' : 'auto'," + "'openAtLoad' : true,"
					+ "'cookies' : false" + "}";
		} else {
			return this.getConstructor().getTreeConfig().toString();
		}
	}

	/**
	 * 获得树形结构JSON字符串
	 * 
	 * @return
	 */
	private String getTreeJsonStr() {
		if (this.getConstructor() == null) {
//			String jsonStr = "[{'id' : 'root_1','txt' : 'Root 1','img' : 'folder.gif',"
//				+ "'imgopen' : 'folderopen.gif',"
//				+ "'imgclose' : 'folder.gif','items' : [{'id' : 'branch_1','txt' : 'Branch 1','items' : [{'id' : 'branch_1_1','txt' : 'Branch 1_1'},{'id' : 'branch_1_2','txt' : 'Branch_1_2'}]"
//				+ "},{'id' : 'branch_2','txt' : 'Branch 2'}]}]";
			
			JSONArray rootArray = new JSONArray();
			JSONObject root= new JSONObject();
			
			root.put("id", "root_1");
			root.put("txt", this.getRootText());
			root.put("items", constructorDefault(null));
			root.put("img", this.getImgBase()+"base.gif");
			rootArray.put(root);
//			return jsonStr;
			return rootArray.toString();
		} else {
			return this.getConstructor().getTreeStruct().toString();
		}
	}

	/**
	 * 递归构造json
	 * @return
	 */
	private JSONArray constructorDefault(List list) {

		if(list == null){
			list = this.getTreeService().getDepthTree(this.getPage(), this.getQueryClassName(), null, 1, 0, 0);
		}
		
		JSONArray nodes = new JSONArray();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ITreeAdaptor node = (ITreeAdaptor) iterator.next();
			JSONObject jsonNode = new JSONObject();
			
			jsonNode.put("id", node.getId());
			jsonNode.put("txt", node.getNodeName());
			
			if((node.getRight() - node.getLeft()) != 1){
				list = this.getTreeService().getDepthTree(this.getPage(), this.getQueryClassName(), null, node.getDepth()+1, node.getLeft(), node.getRight());
				jsonNode.put("items", this.constructorDefault(list));
			}
			
			nodes.put(jsonNode);
		}
		
		return nodes;
	}
}
