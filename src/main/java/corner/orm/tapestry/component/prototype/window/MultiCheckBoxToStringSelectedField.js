var checkboxEventConnect = function(id,win){
	dojo.event.connect(dojo.byId(id),"onclick",function(evt){
		var src=dojo.html.getEventTarget(evt);
		if(src.checked){
			win.maps[src.value]=src.value;
		}else{
			delete win.maps[src.value];
		}
		win.onSelect(win.maps.values());
	}); 
}
/*
 * 变成选择状态
 */
var loadCheckBoxSelected = function(win){
	var elements = [];
	var inputs = document.getElementsByTagName("input");
	dojo.lang.forEach(inputs, function(input){
		if(input.getAttribute("type")=="checkbox"){
			elements.push(input);
		}
	});
	
	for(var key in win.maps){
		for(var i=0;i<elements.length;i++){
			if(elements[i].value==key){
				elements[i].checked = "checked";
			}
		}
	}
}