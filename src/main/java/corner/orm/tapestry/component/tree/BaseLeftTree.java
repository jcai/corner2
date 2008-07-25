package corner.orm.tapestry.component.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.ResponseBuilder;

import corner.service.EntityService;
import corner.service.tree.TreeService;
import corner.util.BeanUtils;

/**
 * 左邻树
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class BaseLeftTree extends BaseComponent implements IDirect{

	public abstract IScript getScript();

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		//处理ajax请求
		if (!cycle.isRewinding() && getResponse().isDynamic()) {
			renderPrototypeComponent(writer, cycle);
			return;
		}
		
		writer.begin("span");
		writer.attribute("id", this.getClientId());
		super.renderComponent(writer, cycle);
		writer.end("span");
		
		if (!cycle.isRewinding()) {
			PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
			
			ILink link = getDirectService().getLink(true,
					new DirectServiceParameter(this));
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("clientId", this.getClientId());
			parms.put("linkUrl", link.getURL());
			parms.put("title", getTitle());
			parms.put("verifyLeafNode", isVerifyLeafNode());
			
			appendParamet(parms);
			
			getScript().execute(this, cycle, pageRenderSupport, parms);
		}
	}
	
	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		
	}
	
	/**
	 * @param writer
	 * @param cycle
	 */
	protected void renderPrototypeComponent(IMarkupWriter writer,
			IRequestCycle cycle) {
		ITreeSelectModel model = initTreeSelectModel(cycle);
		
		model.setTreeList(getTreeService().getDepthTree(model.getParentPage(), model.getQueryClassName(), model.getDepends(), model.getNextDepth(), model.getLeft(),
				model.getRight()));
		
		JSONObject json = leftTreeNodeJson(model);
		
		writer.printRaw(json.toString());
	}

	/**
	 * @see org.apache.tapestry.IDynamicInvoker#getUpdateComponents()
	 */
	public List getUpdateComponents() {
		List<String> list =new ArrayList<String>();
		list.add(this.getClientId());
		return list;
	}

	/**
	 * @param model
	 * @return
	 */
	protected abstract JSONObject leftTreeNodeJson(ITreeSelectModel model);

	/**
	 * @param cycle
	 * @return
	 */
	protected ITreeSelectModel initTreeSelectModel(IRequestCycle cycle) {
		ITreeSelectModel model = this.constructSelectModel();
		model.setLeft(Integer.valueOf(cycle.getParameter("left").trim()));
		model.setRight(Integer.valueOf(cycle.getParameter("right").trim()));
		model.setDepth(Integer.valueOf(cycle.getParameter("depth").trim()));
		model.setQueryClassName(cycle.getParameter("queryClassName").trim());
		model.setDepends(cycle.getParameter("dependFields").trim().split(","));
		return model;
	}
	
	/**
	 * 处理返回的结果
	 * @param datejson
	 * @param node
	 * @param returnValues
	 */
	protected void returnPropertys(JSONObject datejson, Object node,
			JSONObject returnValues) {
		
		Iterator<String> list = returnValues.keys();
		
		String key = null; //循环用的关键字
		
		Object prop = null;
		
		JSONObject json = new JSONObject();
		
		while(list.hasNext()){
			key = list.next();
			returnValues.get(key);
			
			prop = BeanUtils.getProperty(node, (String) returnValues.get(key));
			
			json.put(key, prop==null?"":prop); //如果没有这个属性返回空值
		}
		
		datejson.put("returnDates", json);
	}

	/**
	 * 增加参数
	 * @param parms
	 */
	public void appendParamet(Map<String, Object> parms){
		
	}
	
	/**
	 * 获得SelectModel
	 */
	protected ITreeSelectModel constructSelectModel() {
		throw new UnsupportedOperationException("未能实现该方法");
	}
	
	/**
	 * Injected.
	 */
	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();

	/** 名称 * */
	@Parameter
	public abstract String getTitle();
	
	@Parameter(defaultValue = "ognl:true")
	public abstract boolean isVerifyLeafNode();
	
	/**
	 * 设置返回值的名称和需要返回的内容
	 * json的形式如：{"htmlAttribute1":"entityGetName1","htmlAttribute2":"entityGetName2"}
	 */
	@Parameter(defaultValue = "literal:{}")
	public abstract String getReturnValues();
	
	/**
     * Injected response builder for doing specific XHR things.
     *
     * @return ResponseBuilder for this request. 
     */
    public abstract ResponseBuilder getResponse();
	
	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();
	
	@InjectObject("spring:treeService")
	public abstract TreeService getTreeService();
	
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();
}