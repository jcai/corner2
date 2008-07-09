var ajaxResponse = function(url,paras){  
    var response = "";
    request = new Ajax.Request(url,{
		parameters: paras,
	    method: 'get',
	    onSuccess: function(transport){
    		response = transport.responseText; 
    	},
    	asynchronous:false
	 });
     return response;
}