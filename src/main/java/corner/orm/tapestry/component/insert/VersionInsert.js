/*  
 * 显示字体
 */
function showText(clientId,json,jsonOther,entityName,showProperty,type){
//	dojo.debug("ClientId： " + clientId + " Json: " + json + " JsonOther: " + jsonOther + " EntityName: " + entityName + " ShowProperty: " + showProperty);
	
	var data1 = json[entityName][showProperty];
	var data2 = jsonOther[entityName][showProperty];
	
	dojo.debug("type: " + type);
	
	if(type == "show"){
		if(data1 != null){
		$(clientId).update(data1);
	}
	}else{
		showFieldText(clientId,data1,data2);
	}
}

/*  
 * 显示更新显示文字
 * 未记入svn的用#bfb表示，改变的用#fd8表示
 */
function showFieldText(clientId,data1,data2){
	dojo.debug("data1: " + data1);
	dojo.debug("data2: " + data2);
	
	var ver = showVerNum("otherVer_hid");
	
	if(data1!=null && data2==null){	//删除
		$(clientId).setStyle({
		  backgroundColor: '#FF3E3E',
		  fontSize: '12px',
			'text-decoration': 'line-through',
			color:'white'
		});
		$(clientId).update(data1);
	}
	
	if(data1==null && data2!=null){	//增加
		$(clientId).setStyle({
		  backgroundColor: '#bfb',
		  'line-height':'150%'
		});
		$(clientId).update("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		new Tip($(clientId), data2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
	}
	
	if(data1!=null && data2!=null && data1 != data2){	//修改
		$(clientId).setStyle({
			  backgroundColor: '#fd8'
			});
		$(clientId).update(data1);
		new Tip($(clientId), data2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
	}
	
	if(data1!=null && data2!=null && data1 == data2){	//显示
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
