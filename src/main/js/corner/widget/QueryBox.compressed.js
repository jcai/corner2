dojo.provide("corner.widget.QueryBox");
dojo.require("dojo.widget.DropdownContainer");
dojo.require("dojo.event.*");
dojo.require("dojo.json");
dojo.require("dojo.io.IframeIO");
dojo.widget.defineWidget("corner.widget.QueryBox");
dojo.widget.tags.addParseTreeHandler("dojo:QueryBox");
corner.widget.QueryBox=function(){
dojo.widget.DropdownContainer.call(this);
};
dojo.inherits(corner.widget.QueryBox,dojo.widget.DropdownContainer);
dojo.lang.extend(corner.widget.QueryBox,{widgetType:"QueryBox",frame:null,fillInTemplate:function(_1,_2){
dojo.debug("fill in template");
corner.widget.QueryBox.superclass.fillInTemplate.call(this,_1,_2);
var _3=this.getFragNodeRef(_2);
if(_1.date){
this.date=new Date(_1.date);
}
var _4=document.createElement("div");
_4.style.width="100%";
this.containerNode.appendChild(_4);
var _5="dojo.widget.byId(\""+this.widgetId+"\").frameOnload();";
var _6="dojo.widget.byId(\""+this.widgetId+"\").frameOnunload();";
var r=dojo.render.html;
var _8=((r.ie)&&(dojo.render.os.win))?"<iframe  onLoad='"+_5+"'  >":"iframe";
this.frame=document.createElement(_8);
_4.style.backgroundColor="red";
_4.appendChild(this.frame);
var _9={id:_3.id};
dojo.debug("wdigetId:::"+this.widgetId);
var _a="querybox_page.html?widgetId="+this.widgetId;
this.frame.src=_a;
if(!r.ie){
this.frame.onload=new Function(_5);
}
this.containerNode.style.zIndex=this.zIndex;
this.containerNode.style.backgroundColor="transparent";
},postCreate:function(_b,_c,_d){
dojo.debug("post create");
},frameOnClick:function(_e){
dojo.debug("frame on click!");
},frameOnload:function(_f){
dojo.debug("onload");
this.getFrameWindow().queryBox=this;
this.getFrameWindow().initBox();
},frameOnunload:function(evt){
dojo.debug("onUnload");
},onclick:function(evt){
var src=dojo.html.getEventTarget(evt);
var obj=src.getAttribute("object");
obj=dojo.json.evalJson(obj);
this.inputNode.value=obj.value;
this.toggleContainerShow();
},getFrameWindow:function(){
return dojo.io.iframeContentWindow(this.frame);
},getFrameDocument:function(){
return dojo.io.iframeContentDocument(this.frame);
}});

