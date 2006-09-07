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
		corner.widget.QueryBox.superclass.fillInTemplate.call(this, args, frag);
		var source = this.getFragNodeRef(frag);
		if(args.date){ this.date = new Date(args.date); }
		var dpNode = document.createElement("div");
		dpNode.style.width="100%";
		this.containerNode.appendChild(dpNode);
		this.frame=document.createElement("IFrame");
		dpNode.style.backgroundColor="red";
		dpNode.appendChild(this.frame);
		
		
		
		
		
    
		
		var params={
			id:source.id
		};
		dojo.debug("wdigetId:::"+this.widgetId);
		var url="querybox_page.html?widgetId="+this.widgetId;

		this.frame.src=url;//"querybox_page.html?data="+dojo.json.serialize(params); `
		
		dojo.event.connect("after",this.frame,"onload",this,"onload");

		
		this.containerNode.style.zIndex = this.zIndex;
		this.containerNode.style.backgroundColor = "transparent";
	},
	frameOnClick:function(evt){
		dojo.debug("frame on click!");
	},
	onload:function(evt){
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
  	  var oDoc = (this.frame.contentWindow || this.frame.contentDocument);
	  
	  return oDoc;
	}
});
