XMLSerializer_01=function(){
};
XMLSerializer_01.prototype.type="XMLSerializer_01";
XMLSerializer_01.prototype.toXML=function(document){
	var xml="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
	xml=xml+"<form>\n";
	var figures=document.getFigures();
	for(var i=0;i<figures.length;i++){
		var node=figures[i];
		var title,content;
		if(node instanceof SaleOrderMain){
			title = node.getTitle();
			content = node.getContent();
		}
		xml=xml+"<"+node.type+" x=\""+node.getX()+"\" y=\""+node.getY()+"\" title=\""+title+"\" content=\""+content+"\"  id=\""+node.getId()+"\">\n";
		xml=xml+this.getPropertyXML(node,"   ");
		if(node instanceof CompartmentFigure){
			xml=xml+this.getChildXML(node,"   ");
		}
		xml=xml+"</"+node.type+">\n";
	}
	
	var lines=document.getLines();
	for(var i=0;i<lines.length;i++){
		var line=lines[i];
		xml=xml+"<"+"Line" +" sourcePort=\""+line.sourcePort.getParent().getId()+"\" targetPort=\""+line.targetPort.getParent().getId()+"\">\n";
		xml=xml+"</"+"Line"+">\n";
	}
	xml=xml+"</form>\n";
	return xml;
};
XMLSerializer_01.prototype.getChildXML=function(_2e14,_2e15){
	var xml="";
	var _2e17=_2e14.getChildren();
	for(var i=0;i<_2e17.length;i++){
		var _2e19=_2e17[i];
		xml=xml+_2e15+"<"+_2e19.type+" x=\""+_2e19.getX()+"\" y=\""+_2e19.getY()+"\" id=\""+_2e19.getId()+"\">\n";
		xml=xml+this.getPropertyXML(_2e19,"   "+_2e15);
		if(_2e19 instanceof CompartmentFigure){
		xml=xml+this.getChildXML(_2e19,"   "+_2e15);
		}
		xml=xml+_2e15+"</"+_2e19.type+">\n";
	}
	return xml;
};
XMLSerializer_01.prototype.getPropertyXML=function(_2e1a,_2e1b){
var xml="";
var _2e1d=_2e1a.getProperties();
for(key in _2e1d){
var value=_2e1d[key];
if(value!=null){
xml=xml+_2e1b+"<property name=\""+key+"\" value=\""+value+"\">\n";
}
}
return xml;
};
