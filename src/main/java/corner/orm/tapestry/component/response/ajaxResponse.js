/*
 * 进行ajax请求到任意页面，返回json串
 */
AjaxResponse = CornerBuilder.create({
	extend: CornerBase.prototype,
	initialize: function(url,page) {
		this.url = url;
	},
	request:function(paras){
	    request = new Ajax.Request(this.url,{
			parameters: paras,
		    method: 'get',
		    onSuccess: function(transport){
	    		response = transport.responseText; 
	    	},
	    	asynchronous:false
		 });
	     return response;
	}
});