var CustTafelTree = Class.create();
CustTafelTree.prototype = {
	initialize: function(struct,treeConfig,treeId,rootId,expendElementId,collapseElementId,optTreeCmpId){

		var tree = null;
		var nodeStatus = true;
		// This structure represents one root node with two children
		// Tree initialisation. This function is called automatically
		// when the page finish to load
		tree = new TafelTree(treeId, struct, treeConfig);
//		tree.generate(); 
		
		expend = function(){
			tree.expend();
			nodeStatus = false;
		}
		collapseTree = function(){
			tree.collapse();
			nodeStatus = true;
		}
		
		optTreeCmp = function(){
			if(nodeStatus){
				expend();
			}else{
				collapseTree();
			}
		}
		
		tafelTreeExpend= function(branches){
			for(var i=0;i<branches.length;i++){
				branches[i].collapse();
			}
		};
		
		var root = tree.getBranchById(rootId);
		root.expend();
		var branches = root.getBranches();
		
		tafelTreeExpend(branches);

		if(expendElementId != ''){
			dojo.event.connect(dojo.byId(expendElementId),'onclick','expend');
		}
		
		if(collapseElementId != ''){
			dojo.event.connect(dojo.byId(collapseElementId),'onclick','collapseTree');
		}
		
		if(optTreeCmpId != ''){
			dojo.event.connect(dojo.byId(optTreeCmpId),'onclick','optTreeCmp');
		}
		
	}
}


