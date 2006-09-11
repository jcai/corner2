/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.CornerTextArea");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");

//定义一个CornerTextArea的widget.
dojo.widget.defineWidget("corner.widget.CornerTextArea");
dojo.widget.tags.addParseTreeHandler("dojo:CornerTextArea");

//定义基本的CornerTextArea
corner.widget.CornerTextArea = function(){
	 dojo.widget.html.Select.call(this);
}
//继承
dojo.inherits(corner.widget.CornerTextArea,  dojo.widget.html.Select);
//对CornerTextArea进行扩展.
dojo.lang.extend(corner.widget.CornerTextArea,{
		widgetType: "CornerTextArea",
		templatePath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.html"),
		templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.css")
	
});
