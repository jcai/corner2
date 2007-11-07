/*  
 * 显示字体
 */
function showText(clientId,json,jsonOther,entityName,showProperty){
	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	var ver = showVerNum("otherVer_hid");
	
	if(jsonOther[entityName][showProperty] == null){
		if(json[entityName][showProperty] != null)
			$(clientId).update(json[entityName][showProperty]);
	}else{
		if(json[entityName][showProperty] != jsonOther[entityName][showProperty]){
			$(clientId).update("<font color='#FF0000'> "+ json[entityName][showProperty] + "</font>");
			new Tip($(clientId), jsonOther[entityName][showProperty],{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
		}else{
			if(json[entityName][showProperty] != null)
				$(clientId).update(json[entityName][showProperty]);
		}
	}
}

/*  
 * 显示版本号
 */
function showVerNum(verFieldName){
	var ver = $(verFieldName).value;
	
	if(ver){
		return " Revision:" + ver ;
	}else{
		return "MAXINFO";
	}
}