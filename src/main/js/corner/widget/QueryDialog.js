/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

/**
 * 提供一个查询的下拉框组件.
 */
dojo.provide("corner.widget.QueryDialog");

dojo.require("dojo.widget.Dialog");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.event.*");

dojo.widget.defineWidget(
	"corner.widget.QueryDialog",
	dojo.widget.HtmlWidget,{
	frame:null,
	dlg:null,
	url:null,
	width:600,
	height:400,
	dialogClass:"alertDialog",
	contentClass:"alertContent",
	buttonClass:"alertButton",
	templatePath: dojo.uri.dojoUri("../corner/widget/templates/QueryDialog.html"),
  templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/QueryDialog.css"),

	fillInTemplate: function(args, frag){
		corner.widget.QueryDialog.superclass.fillInTemplate.apply(this, arguments);
	},
	buttonClick:function(){
		dojo.debug("onClick the button!");
		//创建IFRAME
		if(this.dlg==null){
			this._createIframe();
		}
		this.dlg.show();
	},
	_createIframe:function(){

		if(this.url!=null){
			this.frameNode.src=this.url;
		}


		this.dlg = dojo.widget.createWidget("dojo:Dialog",{toggle:"fade",toggleDuration:250}, this.dlgDiv);
		dojo.html.addClass(this.dlg.containerNode, this.dialogClass);
		dojo.debug("screent"+screen.height)
		this.dlg.containerNode.style.width= (screen.width*0.8);
		this.dlg.containerNode.style.height= (screen.height*0.6);
		
	},
	hideDlg:function(){
		if(this.dlg!=null){
			this.dlg.hide();
		}
	}

});


