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