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
			},
			loadFrame:function(){
				frameW=dojo.html.iframeContentWindow(this.win.getContent());
				if(frameW){
					frameW.queryBox=this;
					if(frameW.tree)
						frameW.tree.treeObj.onClick();
					dojo.debug("frameW.queryBox : " + frameW.queryBox);
				}
			}
		});
		this.tree = new TreeDialog(fieldId,props,selectFunName);
	}
}