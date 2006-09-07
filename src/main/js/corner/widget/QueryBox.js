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

dojo.widget.tags.addParseTreeHandler("dojo:QueryBox");
//定义基本的QueryBox
corner.widget.QueryBox=function(){
	dojo.widget.DropdownContainer.call(this);
}
//继承
dojo.inherits(corner.widget.QueryBox,dojo.widget.DropdownContainer);
//对QueryBox进行扩展.
dojo.lang.extend(corner.widget.QueryBox,{
	widgetType : "QueryBox",
	frame:null,
	fillInTemplate: function(args, frag){
		dojo.debug("fill in template");
				
		corner.widget.QueryBox.superclass.fillInTemplate.call(this, args, frag);
		var source = this.getFragNodeRef(frag);
		if(args.date){ this.date = new Date(args.date); }
		var dpNode = document.createElement("div");
		dpNode.style.width="100%";
		this.containerNode.appendChild(dpNode);
		
		//this.frame=document.createElement("IFrame");
		var onloadstr='alert("okay")';
		var r = dojo.render.html;
		var ifrstr = ((r.ie)&&(dojo.render.os.win)) ? "<iframe  onload='"+onloadstr+"'>" : "iframe";
		this.frame = document.createElement(ifrstr);

		dpNode.style.backgroundColor="red";
		dpNode.appendChild(this.frame);
		
		
		
		
    
		
		var params={
			id:source.id
		};
		dojo.debug("wdigetId:::"+this.widgetId);
		var url="querybox_page.html?widgetId="+this.widgetId;
		

		this.frame.src=url;//"querybox_page.html?data="+dojo.json.serialize(params); `
		if(!r.ie){
			this.frame.onload = new Function(onloadstr);
		}
		
		
		this.containerNode.style.zIndex = this.zIndex;
		this.containerNode.style.backgroundColor = "transparent";
		
		
		
	},
	postCreate: function(args, frag, parentComp) {
		dojo.debug("post create");
		//this.frame.onload=this.frameOnload;
//		dojo.event.connect(this.frame,"onload",this,"frameOnload");			

	},
	frameOnClick:function(evt){
		dojo.debug("frame on click!");
	},
	frameOnload:function(evt){
		dojo.debug("onload");
		this.getFrameWindow().queryBox=this;
		this.getFrameWindow().initBox();
	},
	onclick:function(evt){
		var src=dojo.html.getEventTarget(evt);
		var obj=src.getAttribute("object");
		obj=dojo.json.evalJson(obj);	
		this.inputNode.value=obj.value;
		this.toggleContainerShow();		
	},
	getFrameWindow:function(){
	  return dojo.io.iframeContentWindow(this.frame);
	},
	getFrameDocument:function(){
  	  return dojo.io.iframeContentDocument(this.frame);
	}
});
