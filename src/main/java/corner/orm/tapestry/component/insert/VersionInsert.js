/*  
 * 显示字体
 */
function showText(clientId,json,jsonOther,entityName,showProperty,type){
//	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	var date1 = json[entityName][showProperty];
	var date2 = jsonOther[entityName][showProperty];
	
	dojo.debug("type: " + type);
	
	if(type == "show"){
		if(date1 != null){
		$(clientId).update(date1);
	}
	}else{
		showFieldText(clientId,date1,date2);
	}
}

/*  
 * 显示更新显示文字
 * 未记入svn的用#bfb表示，改变的用#fd8表示
 */
function showFieldText(clientId,date1,date2){
	dojo.debug("date1: " + date1);
	dojo.debug("date2: " + date2);
	
	var ver = showVerNum("otherVer_hid");
	
	if(date1!=null && date2==null){	//删除
		$(clientId).setStyle({
		  backgroundColor: '#FF3E3E',
		  fontSize: '12px',
			'text-decoration': 'line-through',
			color:'white'
		});
		$(clientId).update(date1);
	}
	
	if(date1==null && date2!=null){	//增加
		$(clientId).update("<font color='#bfb'>"+ date2 + "</font>");
	}
	
	if(date1!=null && date2!=null && date1 != date2){	//修改
		$(clientId).update("<font color='#fd8'>"+ date1 + "</font>");
		new Tip($(clientId), date2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
	}
	
	if(date1!=null && date2!=null && date1 == date2){	//显示
		dojo.debug("show");
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
