var LeftTree = Class.create();
LeftTree.prototype = {
	initialize: function(element) {
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					leftTreeSite: {
						page: 'test.txt',
						insertion: function(el,data){
							dojo.debug("insertion data:"+data.left);
							var node = Builder.node('dl',{},
							[
								Builder.node('dt',[data.name]),
								Builder.node('dd',['delete'])
							]);
							el.appendChild(node);
							
							this.element.setAttribute("left",data.left);
							this.element.setAttribute("right",data.right);
						},
						callback:{
							call:function(node,id){
								left=node.element.getAttribute("left");
								right=node.element.getAttribute("right");
								leftTreeQueryClassName = $("leftTreeQueryClassName").value;
								
								return "left=" + left + "&" + "right=" + right + "&" + "queryClassName=" + leftTreeQueryClassName;
							}	
						}
					}
				}
			});
			var test = new Ajax.Tree.Invoice(element,'root','leftTreeSite',{data:{name:'Root Element!',left:1,right:2}});
	}
}