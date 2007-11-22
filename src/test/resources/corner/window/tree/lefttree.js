var LeftTree = Class.create();
LeftTree.prototype = {
	initialize: function(element) {
		var date = eval({"nodes":[{"id":"test1","type":"jobsite","data":"Ajax Element 1"},{"id":"test2","type":"jobsite","data":"Ajax Element 2","nodes":[{"id":"test2blah1","type":"task","data":"Sub Element 1","nodes":[{"id":"test2blah1yay1","type":"expense","data":"Leaf Element 1"},{"id":"test2blah1yay2","type":"expense","data":"Leaf Element 2"}]},{"id":"test2blah2","type":"task","data":"Sub Element 2"},{"id":"test2blah2yay3","type":"expense","data":"Leaf Element 3"}]},{"id":"test3","type":"jobsite","data":"Ajax Element 3"}]});
		
		Ajax.Tree.Invoice = Ajax.Tree.create({
				types: {
					jobsite: {
						page: 'ajaxtreedata.php',
						prependParentId: '_jobsite-',
						left:2,
						right:3
						// nothing to do if default behaviour is all that is needed
						},
					task: {
						page: 'ajaxtreedata.php',
						prependParentId: '_task-',
						left:1,
						right:100
						// nothing to do if default behaviour is all that is needed
					},
					expense: {
						leafNode: true,
						insertion: function(el,data){
							var node = Builder.node('dl',{},
							[
								Builder.node('dt',[data]),
								Builder.node('dd',['delete'])
							]);
							el.appendChild(node);
							this.mark.innerHTML = '&nbsp;';
							//new Draggable(this.element);
							this.clickExpense = function(){ alert('you clicked '+this.element.id); }.bind(this);
							Event.observe(this.span,'click',this.clickExpense);
						},
						onDispose: function(){
							Event.stopObserving(this.span,'click',this.clickExpense);
						}
					}
				},
				onTest: function(){
					new Ajax.Tree.Invoice(this.element,'1','task',{data:'Element 2'});
				},
				getContents: function(){
					dojo.debug("add element");
					
					dojo.debug("this.date " + date);
					
					this.loadContents('root',date);
				}
			});
			var test = new Ajax.Tree.Invoice(element,'root','jobsite',{data:'First Element!'});
			test.onTest();
	}
}