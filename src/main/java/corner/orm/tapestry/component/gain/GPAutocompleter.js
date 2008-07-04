function initAutocompleter(element, url, picUrl) {
	var d = new Date();
	var time = d.getTime();
	ac.createAjaxAutocompleter(time + "complete", element, url, "auto_complete", 0.2,{select:'selectme',afterUpdateElement:ac.evaluate}, picUrl,"");
}