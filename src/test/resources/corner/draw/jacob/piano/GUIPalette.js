GUIPalette=function(){
ToolPalette.call(this,"Tools");

this.tool4=new ToolSave(this);

this.tool4.setPosition(10,180);

this.addChild(this.tool4);
};
GUIPalette.prototype=new ToolPalette;
GUIPalette.prototype.dispose=function(){
ToolPalette.prototype.dispose.call(this);

this.tool4.dispose();
};
GUIPalette.prototype.addChildren=function(item){
};
