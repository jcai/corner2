/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

/**
 * 提供一个查询的下拉框组件.
 */
dojo.provide("corner.widget.QueryBox");
dojo.require("dojo.widget.DropdownContainer");
dojo.require("dojo.event.*");
dojo.require("dojo.json");
dojo.require("dojo.io.IframeIO");

//定义一个querybox的widget.
dojo.widget.defineWidget("corner.widget.QueryBox");


//定义基本的QueryBox
/* corner.widget.QueryBox=function(){
	dojo.widget.DropdownContainer.call(this);
}*/
//继承
dojo.inherits(corner.widget.QueryBox,dojo.widget.DropdownContainer);
//对QueryBox进行扩展.
dojo.lang.extend(corner.widget.QueryBox,{
	widgetType : "QueryBox",
	frame:null,
	url:null,
	fillInTemplate: function(args, frag){
		dojo.debug("fill in template");
				
		corner.widget.QueryBox.superclass.fillInTemplate.call(this, args, frag);
		var source = this.getFragNodeRef(frag);
		if(args.date){ this.date = new Date(args.date); }
		var dpNode = document.createElement("div");

		this.containerNode.appendChild(dpNode);
		dojo.debug("containerNode width "+this.containerNode.style.width);
		
		//this.frame=document.createElement("IFrame");
		var onloadstr='dojo.widget.byId("'+this.widgetId+'").frameOnload();';
		var onunloadstr='dojo.widget.byId("'+this.widgetId+'").frameOnunload();';
		var r = dojo.render.html;
		var ifrstr = ((r.ie)&&(dojo.render.os.win)) ? "<iframe  onLoad='"+onloadstr+"'  >" : "iframe";
		this.frame = document.createElement(ifrstr);
		this.frame.style.width="100%";
		this.frame.style.height="100%";


		dpNode.style.backgroundColor="red";
		
		dpNode.appendChild(this.frame);
		

		
		
		
    
		
		var params={
			id:source.id
		};
		dojo.debug("wdigetId:::"+this.widgetId);
		this.frame.src=this.url;//"querybox_page.html?data="+dojo.json.serialize(params); `
		if(!r.ie){
			this.frame.onload = new Function(onloadstr);
		}
		
		
		this.containerNode.style.zIndex = this.zIndex;
		this.containerNode.style.backgroundColor = "transparent";
		
		
		
	},
	postCreate: function(args, frag, parentComp) {
		dojo.debug("post create");
		//this.frame.onload=this.frameOnload;
				

	},
	frameOnClick:function(evt){
		dojo.debug("frame on click!");
	},
	frameOnload:function(evt){
		dojo.debug("onload");
		if(this.getFrameWindow())
			this.getFrameWindow().queryBox=this;

		//dojo.debug("dojo.html.getContentBox(this.getFrameDocument()):"+dojo.html.getBorderBox(this.getFrameDocument().body).width);
		
		
		//dojo.html.setContentBox(this.frame.parentNode,dojo.html.getBorderBox(this.getFrameDocument().body));
		//dojo.event.connect(this.getFrameWindow(),"onunload",this,"frameOnunload");	
	},
	frameOnunload:function(evt){
		dojo.debug("onUnload");
	},
	onclick:function(evt){
		var src=dojo.html.getEventTarget(evt);
		var obj=src.getAttribute("object");
		obj=dojo.json.evalJson(obj);	
		this.inputNode.value=obj.value;
		this.hideContainer();		
	},
	onSelect:function(obj){
		this.processSelectedObj(obj);
		this.hideContainer();
	},
	processSelectedObj:function(obj){
		//TODO add self code 
		dojo.debug("select object"+obj);
	},
	getFrameWindow:function(){
	  return dojo.html.iframeContentWindow(this.frame);
	},
	getFrameDocument:function(){
  	  return dojo.html.iframeContentDocument(this.frame);
	}
});
