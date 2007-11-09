/*  
 * 显示字体
 */
function showText(clientId,json,jsonOther,entityName,showProperty){
//	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	var ver = showVerNum("otherVer_hid");
	
	if(jsonOther[entityName][showProperty] == null){
		if(json[entityName][showProperty] != null)
			$(clientId).update(json[entityName][showProperty]);
	}else{
		if(json[entityName][showProperty] != jsonOther[entityName][showProperty]){
			showFieldText(clientId,json[entityName][showProperty]);
			new Tip($(clientId), jsonOther[entityName][showProperty],{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
		}else{
			if(json[entityName][showProperty] != null)
				$(clientId).update(json[entityName][showProperty]);
		}
	}
}

/*  
 * 显示更新显示文字
 * 未记入svn的用蓝色表示，改变的用红色表示
 */
function showFieldText(clientId,date){
	if(date == null){
		$(clientId).update("<font color='#0000FF'> "+ $(clientId).innerHTML + "</font>");
	}else{
		$(clientId).update("<font color='#FF0000'> "+ date + "</font>");
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