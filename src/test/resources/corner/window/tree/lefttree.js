var LeftTree = Class.create();
LeftTree.prototype = {
	initialize: function(element) {
		var date = eval({"nodes":[{"id":"test1","type":"jobsite","data":"Ajax Element 1"},{"id":"test2","type":"jobsite","data":"Ajax Element 2"}]});
		
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					jobsite: {
						page: 'http://colin.mollenhour.com/ajaxtree/ajaxtreedata.php',
						prependParentId: '_jobsite-',
						// nothing to do if default behaviour is all that is needed
						
						callback : {
							call : function(node,parentId){
								dojo.debug("node " + typeof node);
								dojo.debug("node.id : "+ node.id);
								
								return "left=" + 2 + "&" + "right=" + 3;
							}
						}
					}
				},
				onTest: function(){
					new Ajax.Tree.Invoice(this.element,'1','jobsite',{data:'Element 2'});
				}
			});
			var test = new Ajax.Tree.Invoice(element,'root','jobsite',{data:'First Element!'});
			test.onTest();
	}
}