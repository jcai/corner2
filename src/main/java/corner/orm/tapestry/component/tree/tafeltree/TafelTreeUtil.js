//tafelTree关闭节点
function tafelTreeExpend(branches){
		for(var i=0;i<branches.length;i++){
			branches[i].collapse();
		}
};