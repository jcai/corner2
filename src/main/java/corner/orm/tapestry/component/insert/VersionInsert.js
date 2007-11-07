function showText(clientId,json,jsonOther,entityName,showProperty){
	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	if(jsonOther[entityName][showProperty] == null){
		if(json[entityName][showProperty] != null)
			$(clientId).update(json[entityName][showProperty]);
	}else{
		if(json[entityName][showProperty] != jsonOther[entityName][showProperty]){
			$(clientId).update("<font color='#FF0000'> "+ json[entityName][showProperty] + "</font>");
			new Tip($(clientId), jsonOther[entityName][showProperty]);
		}else{
			if(json[entityName][showProperty] != null)
				$(clientId).update(json[entityName][showProperty]);
		}
	}
}