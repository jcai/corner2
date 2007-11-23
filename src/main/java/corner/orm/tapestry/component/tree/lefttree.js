var LeftTree = Class.create();
LeftTree.prototype = {
	initialize: function(element,page,site,firstTitle,left,right) {
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					leftTreeSite: {
						page: page,
						prependParentId: '_jobsite-',
						// nothing to do if default behaviour is all that is needed
						
						callback : {
							call : function(node,parentId){
								dojo.debug("node " + typeof node);
								dojo.debug("node.id : "+ node.id);
								dojo.debug("node.left : "+ node.left);
								dojo.debug("node.right : "+ node.right);
								
								return "left=" + node.left + "&" + "right=" + node.right;
							}
						}
					}
				},
				onTest: function(){
					new Ajax.Tree.Invoice(this.element,'1',site,{data:'Element 2'},1,4);
				}
			});
			var test = new Ajax.Tree.Invoice(element,'root',site,{data:firstTitle},left,right);
	}
}