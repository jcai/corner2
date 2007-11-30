var LeftTreeDialog  = Class.create();
LeftTreeDialog.prototype = {
	initialize: function(fieldId, props,selectFunName,title,queryClassName,dependField,page){	
		TreeDialog = WindowDialog.create({
			options : {
				title : title,
				queryClassName : queryClassName,
				dependField : dependField,
				page : page
			}
		});
		this.tree = new TreeDialog(fieldId,props,selectFunName);
	}
}