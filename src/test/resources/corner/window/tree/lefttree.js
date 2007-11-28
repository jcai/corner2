var LeftTree = Class.create();
LeftTree.prototype = {
	initialize: function(element,page,title,queryClassName,parentAction) {
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					leftTreeSite: {
						page: 'test.txt',
						queryClassName : queryClassName,
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
							this.element.setAttribute("depth",data.depth);
							this.element.setAttribute("treeName",data.name);
							this.element.setAttribute("entityId",data.id);
							
							if((data.right - data.left) == 1){
								this.clickExpense = function(evt){ 
									parent[parentAction](this.element);
									queryBox.close();
								}.bind(this);
								Event.observe(this.span,'click',this.clickExpense);
							}else{
								this.clickExpense = function(evt){ 
									this.onClick();
								}.bind(this);
								Event.observe(this.span,'click',this.clickExpense);
							}
						},
						callback:{
							call:function(node,id){
								left=node.element.getAttribute("left");
								right=node.element.getAttribute("right");
								depth=node.element.getAttribute("depth");
								
								return "left=" + left + "&" + "right=" + right + "&" + "depth=" + depth + "&" + "queryClassName=" + node.options.queryClassName;
							}	
						}
					}
				}
			});
			new Ajax.Tree.Invoice(element,'root','leftTreeSite',{data:{name:title,left:-1,right:-1,depth:0}},queryClassName);
	}
}