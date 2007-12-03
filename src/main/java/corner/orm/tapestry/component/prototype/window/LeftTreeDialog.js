/*
 *	左邻树Dialog
 */
var LeftTreeDialog  = Class.create();
LeftTreeDialog.prototype = {
	initialize: function(fieldId, props,selectFunName,title,queryClassName,dependFields,page){	
		TreeDialog = CornerBuilder.create({
			extend: WindowDialogBase.prototype,
			options : {
				title : title,
				queryClassName : queryClassName,
				dependFields : dependFields,
				page : page
			}
		});
		this.tree = new TreeDialog(fieldId,props,selectFunName);
	}
}