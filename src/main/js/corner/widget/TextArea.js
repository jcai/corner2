/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.TextArea");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");
dojo.require("dojo.widget.ComboBoxDataProvider");
//定义一个TextArea的widget.
dojo.widget.defineWidget("corner.widget.TextArea");
dojo.widget.tags.addParseTreeHandler("dojo:TextArea");

//定义基本的TextArea
corner.widget.TextArea = function(){
	 dojo.widget.html.Select.call(this);
}
//继承
dojo.inherits(corner.widget.TextArea,  dojo.widget.html.Select);
//对TextArea进行扩展.
dojo.lang.extend(corner.widget.TextArea,{
		widgetType: "TextArea",
		templatePath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.html"),
		templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.css"),
		autoComplete: false,
		forceValidOption: false,
		setAllValues: function(value1, value2){
			this.setValue(value1.substring(value1.lastIndexOf("$")+1,value1.length));
			this.setLabel(value1.substring(0,value1.lastIndexOf("$")));
		}
	
});



