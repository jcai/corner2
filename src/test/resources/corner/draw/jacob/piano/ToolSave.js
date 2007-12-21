ToolSave=function(_3a6a){
ToolGeneric.call(this,_3a6a);
};
ToolSave.prototype=new Button;
ToolSave.prototype.type="ToolSave";
ToolSave.prototype.execute=function(x,y){
	var json = new JSONSerializer().toJSON(this.palette.workflow.getDocument());
	alert(json);
};
