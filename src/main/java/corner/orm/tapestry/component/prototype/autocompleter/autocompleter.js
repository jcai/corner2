/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 *	file : $Id: autocompleter.js 5409 2007-04-30 04:40:54Z jcai $
 *	created at:2007-04-24
 */
var ac = new Object();

var indicator_id="AC_INDICATOR";
ac.createAjaxAutocompleter = function(updId, inpId, updUrl, elmntClass, freq, options,indicator_pic_url,dependFields) {
    if (typeof Ajax.Autocompleter == "undefined") {
        dojo.debug("Ajax.Autocompleter could not be resolved, script.aculo.us library required!");
        return;
    }

    ac._ensureElementExists(updId, elmntClass);
    //create indicator
    ac._ensureIndicatorExists(indicator_pic_url);
    
	if(dependFields && dependFields.length>0){
			this._DF_CB=function(element, entry){
				strs=dependFields.split(',');
				for(i=0;i<strs.length;i++){
					str=strs[i];
		        	 entry+='&'+encodeURIComponent(str)+'='+encodeURIComponent($(str).value);
				}
				 return entry;
			};
		Object.extend(options,{callback:this._DF_CB});	
	}
    var updParams = { method: "get", frequency : freq, paramName: inpId,indicator:indicator_id};
    Object.extend(updParams, options);
    return new Ajax.Autocompleter(inpId, updId, ac.constructDynamicUrl(updUrl), updParams);
}
ac._ensureElementExists = function(id, className) {
    if (!document.getElementById(id)) {
        var block = document.createElement("div");
        block.setAttribute("id", id);
        if (className)
            Element.addClassName(block, className);
        document.body.appendChild(block);
    }
}

ac.evaluate = function (text,li) {
	var nodes = document.getElementsByClassName("returnValue",li) || [];
    if(nodes.length>0){
		//得到Script的值
	   	 value=nodes[0].childNodes[0].text;
	   	 value=value.gsub(/^<!--\s+/,'').gsub(/\/{2}\s+-->/,'');
    	fields = eval('(' + value + ')');
		for(var name in fields){
			if($(name)){
				$(name).value=fields[name];
			}
		}	 
    }
}
ac._ensureIndicatorExists = function(indicator_pic_url){
    if (!document.getElementById(indicator_id)) {
        var block = document.createElement("div");
        block.setAttribute("id", indicator_id);
        Element.addClassName(block, "indicator"); 
        Element.hide(block);
        document.body.appendChild(block);

        
		var t=document.createElement("img");
		t.setAttribute("src",indicator_pic_url);
		block.appendChild(t);
		block.appendChild(document.createTextNode("Waiting....."));
    }
	
}
// 阻止IE缓存，见： http://dev.bjmaxinfo.com/projects/manufacturing-system/wiki/2006/10/26/09.11
ac.constructDynamicUrl=function(url){
			var d = new Date();
			var time = d.getTime();
			tmpUrl=url;
			if (tmpUrl.indexOf('?') > 0)
				tmpUrl = tmpUrl+'&prevent_cache='+time;
			else
				tmpUrl = tmpUrl+'?prevent_cache='+time;
			
			return tmpUrl;

}