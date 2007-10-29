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

package corner.orm.tapestry.component.prototype.window;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.AbstractWidget;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.link.DirectLink;
import org.apache.tapestry.listener.ListenerInvoker;

import corner.util.StringUtils;

/**
 * 使用prototype的弹出查询框
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class WindowQueryDialog extends AbstractWidget implements IDirect{

	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		IActionListener listener = getListener();

		if (listener == null)
			throw Tapestry.createRequiredParameterException(this, "listener");

		getListenerInvoker().invokeListener(listener, this, cycle);
	}
	
	
	/**
	 * @see org.apache.tapestry.dojo.IWidget#renderWidget(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	public void renderWidget(IMarkupWriter writer, IRequestCycle cycle) {
		
		if (!cycle.isRewinding()) {
            writer.begin(getTemplateTagName()); // use element specified
            renderIdAttribute(writer, cycle); // render id="" client id
            renderInformalParameters(writer, cycle);
        }
        
		setWindowShowButton(writer);
		
        renderBody(writer, cycle);
        
        if (!cycle.isRewinding()) {
            writer.end();
        }
        
        if (!cycle.isRewinding()) {
            JSONObject json = initWindow();
            
            //去掉onload方法名的引号
            String props = StringUtils.replace(json.toString(), "\"" + getDialogScriptOnLoadName() +"\"", getDialogScriptOnLoadName());            
            
            Map<String,Object> parms = new HashMap<String,Object>();
            parms.put("component", this);
            parms.put("props", props);
            
            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
        }
	}

	/**
	 * 创建一个按钮
	 */
	private void setWindowShowButton(IMarkupWriter writer) {
		addDialogCss(writer);	//加入css
		
		writer.beginEmpty("img");
		writer.attribute("name",getDialogButtonName());
		writer.attribute("id",getDialogButtonName());
		writer.attribute("src", getIndicatorAsset().buildURL());
		writer.attribute("border","0");
	}
	
	/**
	 * @param writer
	 */
	private void addDialogCss(IMarkupWriter writer) {
		String [] csList = csss.split(",");
		
		String url = getAssetCss().buildURL();
		
		for(String css : csList){
			url = getAssetCss().buildURL().replace("alphacube", css);
			
			writer.beginEmpty("link");
			writer.attribute("rel", "stylesheet");
			writer.attribute("type", "text/css");
			writer.attribute("href", url);
		}
		
	}


	/**
	 * 获得调用的方法名称
	 */
	public String getDialogScriptFunName(){
		return getDialogName() + "Show";
	}
	
	/**
	 * 获得名称
	 */
	public String getDialogName(){
		return getPrefix() + this.getClientId();
	}
	
	/**
	 * 获得按钮名称
	 */
	public String getDialogButtonName(){
		return getDialogName() + "_button";
	}
	
	/**
	 * 获得onload函数名称
	 */
	public String getDialogScriptOnLoadName(){
		return getDialogName() + "Fun";
	}


	/**
	 * 初始化窗体属性
	 */
	private JSONObject initWindow() {
		JSONObject json =new JSONObject();
        json.put("id", getWinId());
        json.put("className", getClassName());
        json.put("title", getTitle());
        json.put("url", getUrl());
        json.put("top ", getTop ());
        json.put("right", getLeft());
        json.put("width", getWidth());
        json.put("height", getHeight());
        json.put("maxWidth", getMaxWidth());
        json.put("maxHeight", getMaxHeight());
        json.put("minWidth ", getMinWidth ());
        json.put("minHeight", getMinHeight());
        json.put("resizable", getResizable());
        json.put("closable", getClosable());
        json.put("minimizable", getMinimizable());
        json.put("maximizable", getMaximizable());
        json.put("draggable", getDraggable());
        json.put("showEffectOptions", getShowEffectOptions());
        json.put("hideEffectOptions", getHideEffectOptions());
        json.put("effectOptions", getEffectOptions());
        json.put("opacity", getOpacity());
        json.put("recenterAuto", getRecenterAuto());
        json.put("gridX", getGridX());
        json.put("gridY", getGridY());
        json.put("wiredDrag", getWiredDrag());
        json.put("destroyOnClose", getDestroyOnClose());
        json.put("all callbacks", getAllCallbacks());
        json.put("onload", getDialogScriptOnLoadName());
         
        //未知的功能，放开就报错
//      json.put("parent", getParent()); 
//      json.put("showEffect", getShowEffect());
//      json.put("hideEffect", getHideEffect());
		
		return json;
	}

	/**
	 * 前台生成的onLoad脚本
	 */
	public String getOnloadFun() {
		return 	"function(){ \n" +
				"dojo.debug('frame on load!');\n"+
				"frameW=dojo.html.iframeContentWindow("+getDialogName()+".getContent());\n" +
				"	if(frameW){\n"+
				"		frameW.queryBox="+getDialogName()+";\n" +
				"	}\n" +
				"}\n";
	}


	/**
	 * @return
	 */
	protected String getUrl() {
		
		Object[] parameters = new Object[]{getOnSelectFunName(),getParameters()};
		
		Object[] serviceParameters = DirectLink
				.constructServiceParameters(parameters);

		DirectServiceParameter dsp = new DirectServiceParameter(this,
				serviceParameters);

		String url = getDirectService().getLink(false, dsp).getAbsoluteURL();
		return url;
	}
	
	public static String csss="default,theme1,mac_os_x,alphacube,darkX,spread,alert,alert_lite";
	
	/**
	 * window 需要的参数
	 */
	@Parameter(defaultValue = "literal:generated")
	public abstract String getWinId();
	@Parameter(defaultValue = "literal:alphacube")
	public abstract String getClassName();
	@Parameter(defaultValue = "literal:none")
	public abstract String getTitle();
//	@Parameter(defaultValue = "literal:none")
//	public abstract String getUrl();
	@Parameter(defaultValue = "literal:body")
	public abstract String getParent();
	@Parameter(defaultValue = "literal:top:0")
	public abstract String getTop ();
	@Parameter(defaultValue = "literal:left:0")
	public abstract String getLeft();
	@Parameter(defaultValue = "literal:500")
	public abstract String getWidth();
	@Parameter(defaultValue = "literal:300")
	public abstract String getHeight();
	@Parameter(defaultValue = "literal:none")
	public abstract String getMaxWidth();
	@Parameter(defaultValue = "literal:none")
	public abstract String getMaxHeight();
	@Parameter(defaultValue = "literal:500")
	public abstract String getMinWidth ();
	@Parameter(defaultValue = "literal:300")
	public abstract String getMinHeight();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getResizable();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getClosable();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getMinimizable();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getMaximizable();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getDraggable();
	@Parameter(defaultValue = "literal:Effect.Appear")
	public abstract String getShowEffect();
	@Parameter(defaultValue = "literal:Effect.Fade")
	public abstract String getHideEffect();
	@Parameter(defaultValue = "literal:{duration:1.5}}")
	public abstract String getShowEffectOptions();
	@Parameter(defaultValue = "literal:none")
	public abstract String getHideEffectOptions();
	@Parameter(defaultValue = "literal:none")
	public abstract String getEffectOptions();
//	@Parameter(defaultValue = "literal:none")
//	public abstract String getOnload();
	@Parameter(defaultValue = "ognl:1")
	public abstract int getOpacity();
	@Parameter(defaultValue = "ognl:true")
	public abstract String getRecenterAuto();
	@Parameter(defaultValue = "ognl:1")
	public abstract int getGridX();
	@Parameter(defaultValue = "ognl:1")
	public abstract int getGridY();
	@Parameter(defaultValue = "ognl:false")
	public abstract String getWiredDrag();
	@Parameter(defaultValue = "ognl:false")
	public abstract String getDestroyOnClose();
	@Parameter(defaultValue = "literal:none")
	public abstract String getAllCallbacks();
	
	
	@Asset("classpath:/corner/prototype/window/themes/alphacube.css")
	public abstract IAsset getAssetCss();
	
	
    /**
	 * 当选中某一条记录的时候，响应的js函数
	 * @return
	 */
	@Parameter(defaultValue = "literal:onSelectFunName")
	public abstract String getOnSelectFunName();
	
	@Parameter
	public abstract Object getParameters();
	
	@Parameter(defaultValue = "literal:pwin_")
	public abstract String getPrefix();
	
	@Asset("classpath:/corner/widget/templates/dic.gif")
	public abstract IAsset getIndicatorAsset();
	
	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();
	
	/**
	 * @see org.apache.tapestry.AbstractComponent#isStateful()
	 */
	@Override
	@Parameter(defaultValue="true")
	public abstract boolean isStateful();
	
	/**
	 * 监听调用函数
	 */
	@Parameter(required = true)
	public abstract IActionListener getListener();
	
	/**
	 * 获得监听
	 */
	@InjectObject("infrastructure:listenerInvoker")
	public abstract ListenerInvoker getListenerInvoker();

	/** injected. */
	@InjectScript("WindowQueryDialog.script")
	public abstract IScript getScript();
	
}
