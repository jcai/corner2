var PageTree = Class.create();
PageTree.prototype = {
	initialize: function(element,page,title,actionFrame,queryClassName,parentPage,baseUrl) {
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					pageTreeSite: {
						page: page,
						insertion: function(el,data){
							var node = Builder.node('dl',{className:'treeDl'},
							[
								Builder.node('dt',{className:'treeDt'},[data.name]),
							]);
							el.appendChild(node);

							this.element.setAttribute("left",data.left);
							this.element.setAttribute("right",data.right);
							this.element.setAttribute("depth",data.depth);
							this.element.setAttribute("treeName",data.name);
							this.element.setAttribute("this",data.thisEntity);
							
							this.element.setAttribute("target",actionFrame);
							
							if((data.right - data.left) == 1){
								this.clickExpense = function(evt){
									window.parent[actionFrame].location.replace(baseUrl+data.actionPage);
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
								var left=node.element.getAttribute("left");
								var right=node.element.getAttribute("right");
								var depth=node.element.getAttribute("depth");
								
								var dependFields;
								
//								var dependFields = queryBox.options.dependFields;
								
								var depend="";
								
								if(dependFields != null && dependFields != "" && dependFields.length != 0){
									for(var i=0;i<dependFields.length;i++){
										if(i!=0){
											depend += ",";
										}
										depend += parent.document.getElementById(dependFields[i]).value;
									}
								}else{
									depend="";
								}
															
								return "left=" + left + "&" + "right=" + right + "&" + "depth=" + depth + 
								"&" + "queryClassName=" + queryClassName + "&" + "dependFields=" + depend + 
								"&" + "parentPage=" + parentPage;
							}
						}
					}
				},
				createNodes: function(nodes){
					if(!nodes.length){ return; }
					this.showChildren();
					this.loaded = true;
					for(var i=0; i < nodes.length; i++){
						if((nodes[i].data.right - nodes[i].data.left) == 1){
							this.options.leafNode = true;
						}else{
							this.options.leafNode = false;
						}
						var newNode = new this.constructor(this.element,nodes[i].id,nodes[i].type,nodes[i]);
					}
					if(this.options.sortable){ this.createSortable(); }
				}
			});
			this.treeObj = new Ajax.Tree.Invoice(element,'root','pageTreeSite',{data:{name:title,left:-1,right:-1,depth:0}});
	}
}