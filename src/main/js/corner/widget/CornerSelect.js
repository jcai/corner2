/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.CornerSelect");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");
dojo.require("dojo.widget.ComboBoxDataProvider");
//定义一个CornerSelect的widget.
dojo.widget.defineWidget("corner.widget.CornerSelect");
dojo.widget.tags.addParseTreeHandler("dojo:CornerSelect");

//定义基本的CornerSelect
corner.widget.CornerSelect = function(){
	 dojo.widget.html.Select.call(this);
}
//继承
dojo.inherits(corner.widget.CornerSelect,  dojo.widget.html.Select);
//对CornerSelect进行扩展.
dojo.lang.extend(corner.widget.CornerSelect,{
		widgetType: "CornerSelect",
		autoComplete: false,
		forceValidOption: false,
		setAllValues: function(name, value){
			this.setValue(name);
			this.setLabel(value);
			//this.setValue(value1.substring(value1.lastIndexOf("$")+1,value1.length));
			//this.setLabel(value1.substring(0,value1.lastIndexOf("$")));
		}
	
});



