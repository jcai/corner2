var CustTafelTree = Class.create();
CustTafelTree.prototype = {
	initialize: function(struct,treeConfig,treeId,rootId,expendElementId,collapseElementId){

		var tree = null;
		// This structure represents one root node with two children
//		var struct = ${treeStruct};
//		var treeConfig = ${treeConfig};
		
		// Tree initialisation. This function is called automatically
		// when the page finish to load
		tree = new TafelTree(treeId, struct, treeConfig);
//		tree.generate(); 
		
		expend = function(){
			tree.expend();
		}
		collapseTree = function(){
			tree.collapse();
		}
		//tafelTree关闭节点
		tafelTreeExpend= function(branches){
			for(var i=0;i<branches.length;i++){
				branches[i].collapse();
			}
		};
		
		var root = tree.getBranchById(rootId);
		root.expend();
		var branches = root.getBranches();
		
		tafelTreeExpend(branches);

		if(expendElementId!=''){
			dojo.event.connect(dojo.byId(expendElementId),'onclick','expend');
		}
		
		if(collapseElementId!=''){
			dojo.event.connect(dojo.byId(collapseElementId),'onclick','collapseTree');
		}
	}
}


