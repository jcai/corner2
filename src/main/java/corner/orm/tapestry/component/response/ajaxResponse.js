/*
 * 进行ajax请求到任意页面，返回json串
 */
AjaxResponse = CornerBuilder.create({
	extend: CornerBase.prototype,
	initialize: function(url,page) {
		this.url = url;
		this.page = page;
	},
	request:function(paras){
		dojo.debug(paras);
		if(paras == null) paras = {};
		var response = "";
		var ops = {"page":this.page};
		Object.extend(paras, ops);
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