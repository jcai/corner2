function initAutocompleter(element, fromElementId,url, picUrl) {
	ac.createAjaxAutocompleter(element.parentNode.parentNode.rowIndex + "complete", element, url, "auto_complete", 0.2,{select:'selectme',paramName:fromElementId,afterUpdateElement:ac.evaluate}, picUrl,"");
}