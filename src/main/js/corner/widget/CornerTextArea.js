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
		dataProviderClass: "corner.widget.ComboBoxDataProvider"
	
});
corner.widget.ComboBoxDataProvider=function(){

	var sup=new dojo.widget.ComboBoxDataProvider();
	for(pro in sup){
		this[pro]=sup[pro];
	};

	/**
	 * 调用父类的构造方法，对父类数据成员进行初始化
	 */
	this.init = function(cbox, node){
		sup.init(cbox,node);
	}
	
	/**
	 * 复写父类dojo.widget.comboBox的_performSearch方法
	 */
	this._preformSearch = function(searchStr, type, ignoreLimit){
		//
		//	NOTE: this search is LINEAR, which means that it exhibits perhaps
		//	the worst possible speed characteristics of any search type. It's
		//	written this way to outline the responsibilities and interfaces for
		//	a search.
		//
		var st = type||sup.searchType;
		// FIXME: this is just an example search, which means that we implement
		// only a linear search without any of the attendant (useful!) optimizations
		var ret = [];
		if(!sup.caseSensitive){
			searchStr = searchStr.toLowerCase();
		}
		for(var x=0; x<sup.data.length; x++){
			if((!ignoreLimit)&&(ret.length >= sup.searchLimit)){
				break;
			}
			// FIXME: we should avoid copies if possible!
			var dataLabel = new String((!sup.caseSensitive) ? sup.data[x][0][0].toLowerCase() : sup.data[x][0][0]);
			if(dataLabel.length < searchStr.length){
				// this won't ever be a good search, will it? What if we start
				// to support regex search?
				continue;
			}
			if(st == "STARTSTRING"){
				// jum.debug(dataLabel.substr(0, searchStr.length))
				// jum.debug(searchStr);
				if(searchStr == dataLabel.substr(0, searchStr.length)){
					ret.push(sup.data[x]);
				}
			}else if(st == "SUBSTRING"){
				// this one is a gimmie
				if(dataLabel.indexOf(searchStr) >= 0){
					ret.push(sup.data[x]);
				}
			}else if(st == "STARTWORD"){
				// do a substring search and then attempt to determine if the
				// preceeding char was the beginning of the string or a
				// whitespace char.
				var idx = dataLabel.indexOf(searchStr);
				if(idx == 0){
					// implicit match
					ret.push(sup.data[x]);
				}
				if(idx <= 0){
					// if we didn't match or implicily matched, march onward
					continue;
				}
				// otherwise, we have to go figure out if the match was at the
				// start of a word...
				// this code is taken almost directy from nWidgets
				var matches = false;
				while(idx!=-1){
					// make sure the match either starts whole string, or
					// follows a space, or follows some punctuation
					if(" ,/(".indexOf(dataLabel.charAt(idx-1)) != -1){
						// FIXME: what about tab chars?
						matches = true; break;
					}
					idx = dataLabel.indexOf(searchStr, idx+1);
				}
				if(!matches){
					continue;
				}else{
					ret.push(sup.data[x]);
				}
			}
		}
					dojo.debug("ret.length:"+ret.length);
		sup.provideSearchResults(ret);
	};
};
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


