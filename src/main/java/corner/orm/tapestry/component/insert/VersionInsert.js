/*  
 * 显示字体
 */
function showText(clientId,json,jsonOther,entityName,showProperty){
//	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	var ver = showVerNum("otherVer_hid");
	
	var date1 = json[entityName][showProperty];
	var date2 = jsonOther[entityName][showProperty];
	
	if(date1!=null && date2==null){	//删除
		$(clientId).update("<font color='#88f'> "+ date1 + "</font>");
	}else{	//改、增
		showFieldText(clientId,date1,date2);
		new Tip($(clientId), date2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
	} 
}

/*  
 * 显示更新显示文字
 * 未记入svn的用#bfb表示，改变的用#fd8表示
 */
function showFieldText(clientId,date1,date2){
	
	dojo.debug("date1: " + date1);
	dojo.debug("date2: " + date2);
	
	if(date1 == null && date2 != null){
		$(clientId).update("<font color='#bfb'> "+ $(clientId).innerHTML + "</font>");
	}else{
		$(clientId).update("<font color='#fd8'> "+ date1 + "</font>");
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