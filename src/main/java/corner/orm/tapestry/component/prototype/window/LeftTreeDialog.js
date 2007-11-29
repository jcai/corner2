var LeftTreeDialog  = Class.create();
LeftTreeDialog.prototype = {
	initialize: function(fieldId, props,selectFunName,title,queryClassName,dependField){	
		TreeDialog = WindowDialog.create({
			options : {
				title : title,
				queryClassName : queryClassName,
				dependField : dependField
			}
		});
		this.tree = new TreeDialog(fieldId,props,selectFunName);
	}
}