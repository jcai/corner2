dojo.provide("dojo.widget.ShowAction");
dojo.require("dojo.widget.*");
dojo.widget.defineWidget("dojo.widget.ShowAction",dojo.widget.HtmlWidget,{on:"",action:"fade",duration:350,from:"",to:"",auto:"false",postMixInProperties:function(){
if(dojo.render.html.opera){
this.action=this.action.split("/").pop();
}
}});
