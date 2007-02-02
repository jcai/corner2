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
	selectFun:null,
	titleText:null,
	
	dialogClass:"alertDialog",
	contentClass:"alertContent",
	buttonClass:"alertButton",
	templatePath: dojo.uri.dojoUri("../corner/widget/templates/QueryDialog.html"),
  templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/QueryDialog.css"),

	fillInTemplate: function(args, frag){
		corner.widget.QueryDialog.superclass.fillInTemplate.apply(this, arguments);
		dojo.debug(this.titleText+this.titleNode);
		if(this.titleText!=null)
			this.titleNode.appendChild(document.createTextNode(this.titleText));
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
		var onloadstr='dojo.widget.byId("'+this.widgetId+'").onLoadFrame();';

		var r = dojo.render.html;
		var ifrstr = ((r.ie)&&(dojo.render.os.win)) ? "<iframe frameborder='0' scrolling='auto' onLoad='"+onloadstr+"'  >" : "iframe";
		this.frameNode = document.createElement(ifrstr);
		
		this.dlgDiv.appendChild(this.frameNode);		
		dojo.html.setClass(this.frameNode,"frameClass");
		if(this.url!=null){
			this.frameNode.src=this.url;
		}
		
		if(!r.ie){
			this.frameNode.onload = new Function(onloadstr);
		}
		if(this.selectFun!=null){
			dojo.debug("selectFun:"+this.selectFun)
			dojo.event.connect(this,"processSelectedObj",this.selectFun);
		}
		this.dlg = dojo.widget.createWidget("dojo:Dialog",{toggle:"fade",toggleDuration:250}, this.dlgDiv);
		dojo.html.addClass(this.dlg.containerNode, this.dialogClass);
		dojo.debug("get sreen width "+screen.width);
		this.dlg.containerNode.style.width= (screen.width*0.8);
		this.dlg.containerNode.style.height= (screen.height*0.6);
		
	},
	hideDlg:function(){
		if(this.dlg!=null){
			this.dlg.hide();
		}
	},
	onSelect:function(obj){
		this.processSelectedObj(obj);
		this.hideDlg();
	},
	processSelectedObj:function(obj){
		dojo.debug("process")
	},
	onLoadFrame:function(){
		dojo.debug("frame on load!");
		frameW=dojo.html.iframeContentWindow(this.frameNode);
		if(frameW){
			frameW.queryBox=this;
		}
		
	}

});


