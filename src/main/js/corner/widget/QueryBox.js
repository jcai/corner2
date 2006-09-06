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
	fillInTemplate: function(args, frag){
		corner.widget.QueryBox.superclass.fillInTemplate.call(this, args, frag);
		var source = this.getFragNodeRef(frag);
		if(args.date){ this.date = new Date(args.date); }
		var dpNode = document.createElement("div");
		var frame=document.createElement("IFrame");
		dpNode.style.backgroundColor="red";
		dpNode.appendChild(frame);
		
		var params={
			id:source.id
		};
		dojo.debug(dojo.json.serialize(params));
		var url="querybox_page.html?data="+dojo.json.serialize(params);
		obj=dojo.json.evalJson(dojo.json.serialize(params));
		dojo.debug(obj);
		dojo.debug("url::"+url);
		frame.src=url;//"querybox_page.html?data="+dojo.json.serialize(params); 
		frame.setAttribute("test","hello");
		dojo.event.connect(frame,"onclick",this,"frameOnClick");
		this.containerNode.appendChild(dpNode);
		this.containerNode.style.backgroundColor = "transparent";
	},
	frameOnClick:function(evt){
		dojo.debug("frame on click!");
	}
});
