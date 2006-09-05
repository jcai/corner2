/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

/**
 * 提供一个查询的下拉框组件.
 */
dojo.provide("corner.widget.QueryBox");
dojo.require("dojo.widget.DropdownContainer");

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
		var text=document.createTextNode("hello!");
		dpNode.style.backgroundColor="red";
		dpNode.appendChild(text);
		this.containerNode.appendChild(dpNode);
		this.containerNode.style.backgroundColor = "transparent";
	}
});
