var CustTafelTree = Class.create();
CustTafelTree.prototype = {
	initialize: function(struct,struct1,treeConfig,treeId,rootId,expendElementId,collapseElementId,optTreeCmpId,optTreeLeafCmpId,openLink){
		
		var tree = null;
		var nodeStatus = true;
		var leafStatus = true;
		var treeStruct = struct;
		var treeStruct1 = struct1;
		var treeIds = treeId;
		var treeConfigs = treeConfig;
		var rootIds = rootId;
		
		myClick = function(branch){
	    	var popupObject = new PopupWindow();
	    	popupObject.setUrl(openLink + branch.getId());
			popupObject.autoHide();
			popupObject.setSize(500,300);
			popupObject.offsetX = 30;
			popupObject.showPopup(treeIds); 
		}
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
		
		optTreeLeafCmp = function(){
			dojo.byId(treeIds).innerHTML = "";
			if(leafStatus){
				tree = new TafelTree(treeIds, treeStruct1, treeConfigs);
				leafStatus = false;
			}else{
				tree = new TafelTree(treeIds, treeStruct, treeConfigs);
				leafStatus = true;
			}
			root = tree.getBranchById(rootIds);
			root.expend();
			branches = root.getBranches();
			tafelTreeExpend(branches);
		}
		 
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
		
		if(optTreeLeafCmpId != ''){
			dojo.event.connect(dojo.byId(optTreeLeafCmpId),'onclick','optTreeLeafCmp');
		}
	}
}


