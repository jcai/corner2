/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.CornerTextArea");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");
dojo.require("dojo.widget.ComboBoxDataProvider");
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
		templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.css"),
		startSearch: function(searchString){}
	
});
corner.widget.ComboBoxDataProvider=function(){
	var sup=new dojo.widget.ComboBoxDataProvider();
	for(xxxx in sup){
		this[xxxx]=sup[xxxx];
	};
	this._preformSearch =function(searchStr, type, ignoreLimit){
		dojo.debug("call from child!!");
		dojo.debug("call before");
		
		sup._preformSearch(searchStr, type, ignoreLimit);
		
	};
	
};
corner.widget.ComboBoxDataProvider.prototype.asdf=function(){
	
}
//corner.widget.ComboBoxDataProvider.prototype = dojo.widget.ComboBoxDataProvider;
/*
dojo.inherits(corner.widget.ComboBoxDataProvider,  dojo.widget.ComboBoxDataProvider);

dojo.lang.extend(corner.widget.ComboBoxDataProvider,{
	_preformSearch : function(searchStr, type, ignoreLimit){
		dojo.debug("hello world!");
		this.inherited("_preformSearch", [searchStr, type, ignoreLimit]);
		
	}
});
*/
/*dojo.declare("corner.widget.ComboBoxDataProvider", dojo.widget.ComboBoxDataProvider, {

});*/


