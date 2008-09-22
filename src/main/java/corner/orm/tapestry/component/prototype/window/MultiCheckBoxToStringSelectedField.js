var checkboxEventConnect = function(id,box){
	dojo.event.connect(dojo.byId(id),"onclick",function(evt){
		var src=dojo.html.getEventTarget(evt);
		var queryBox = eval(box);
		if(src.checked){
			queryBox.maps[src.value]=src.value;
		}else{
			delete queryBox.maps[src.value];
		}
		queryBox.onSelect(queryBox.maps.values());
	}); 
}
/*
 * 变成选择状态
 */
var loadCheckBoxSelected = function(box){
	dojo.event.connect('after',window, 'onload',function(evt){
		var elements = [];
		var inputs = document.getElementsByTagName("input");
		dojo.lang.forEach(inputs, function(input){
			if(input.getAttribute("type")=="checkbox"){
				elements.push(input);
			}
		});
		var queryBox = eval(box);
		dojo.debug(queryBox);
		if(queryBox == undefined || queryBox == null){
			window.setInterval("loadCheckBoxSelected()",1000);
			return;
		}
		for(var key in queryBox.maps){
			for(var i=0;i<elements.length;i++){
				if(elements[i].value==key){
					elements[i].checked = "checked";
				}
			}
		}
	});
}