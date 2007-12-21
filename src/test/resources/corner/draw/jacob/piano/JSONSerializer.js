dojo.require("dojo.json");
JSONSerializer=function(){
};
JSONSerializer.prototype.type="JSONSerializer";
JSONSerializer.prototype.toJSON=function(document){
	var figures=document.getFigures();
	var json="{";
	/*
	 * 处理SaleOrderMain
	 */
	json=json+"\"nodes\":[";
	for(var i=0;i<figures.length;i++){
		if(i!=0){
			json=json+",";
		}
		var node=figures[i];
		var title,content;
		if(node.type == "SaleOrderMain" || node.type == "Matter"){
			title = node.getTitle();
			content = node.getContent();
		}else{
			content = title = "";
		}
		
		json=json+"{\"id\":\""+node.getId()+"\",";
		json=json+"\"type\":\""+node.type+"\",";
		json=json+"\"x\":"+node.getX()+",";
		json=json+"\"y\":"+node.getY()+",";
		json=json+"\"title\":\""+title+"\",";
		json=json+"\"content\":\""+content+"\"}";
	}
	json=json+"]";
	
	json=json+",\"lines\":[";
	var lines=document.getLines();
	for(var i=0;i<lines.length;i++){
		if(i!=0){
			json=json+",";
		}
		var line=lines[i];
		json=json+"{\"sourcePort\":\""+line.sourcePort.getParent().getId()+"\",";
		json=json+"\"targetPort\":\""+line.targetPort.getParent().getId()+"\"}";
	}
	json=json+"]}";
	dojo.debug(json);
	return json;
//	return dojo.json.evalJson(json);
};
