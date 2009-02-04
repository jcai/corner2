var checkboxEventConnect = function(id,box){
	dojo.event.connect(dojo.byId(id),"onclick",function(evt){
		var src=dojo.html.getEventTarget(evt);
		var queryBox = eval(box);
		dojo.debug("B queryBox.maps.values is "+queryBox.maps.values());
		if(src.checked){
			queryBox.maps[src.value]=src.value;
		}else{
			delete queryBox.maps[src.value];
		}
		dojo.debug("queryBox.maps.values is "+queryBox.maps.values());
		queryBox.onSelect(queryBox.maps.values());
	}); 
}

 var intervalId;//TODO 全局变量可能造成问题,期待解决.
/*
 * 变成选择状态
 */
var loadCheckBoxSelected = function(box){
        var queryBox;
        
		try{
		    queryBox = eval(box);
		    queryBox.maps;
		}catch(e){
			if(intervalId){
				dojo.debug("clearInterval "+intervalId);
				window.clearInterval(intervalId);
			}
		    intervalId = window.setInterval("loadCheckBoxSelected()",1000);
			dojo.debug("interval "+intervalId);
			return;
		}
		
		dojo.debug("evaluate ...");
		if(intervalId){
			window.clearInterval(intervalId);
		}
		
		var elements = [];
		var inputs = document.getElementsByTagName("input");
		dojo.lang.forEach(inputs, function(input){
			if(input.getAttribute("type")=="checkbox"){
				elements.push(input);
			}
		});
		
		for(var key in queryBox.maps){
			for(var i=0;i<elements.length;i++){
				if(elements[i].value==key){
					elements[i].checked = "checked";
				}
			}
		}
}
