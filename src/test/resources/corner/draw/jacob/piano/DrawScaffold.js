dojo.require("dojo.json");
DrawScaffold = CornerBuilder.create({
	extend: CornerBase.prototype,
	initialize: function(canvas, props){	
		this.canvas = "paintarea";	
		this.workflow = new Workflow(this.canvas);
		this.json = dojo.json.evalJson("{'nodes':[{'id':'q5asTHHGkP','type':'SaleOrderMain','x':'100','y':'100','title': '订单编号： ','content':'<h1>名称</h1>'},{'id':'PB12l3iliw','type': 'Matter','x':'500','y':'80','title':'物','content':'原料'},{'id': 'HBp1l3PSoB','type':'Matter','x':'500','y':'250','title':'物','content':' 货品'},{'id':'D1R5vg3D7O','type':'Matter','x':'800','y':'250','title':'物', 'content':'货品'}],'lines':[{'sourcePort':'q5asTHHGkP','targetPort': 'PB12l3iliw'},{'sourcePort':'q5asTHHGkP','targetPort':'HBp1l3PSoB'},{'sourcePort':'HBp1l3PSoB','targetPort':'D1R5vg3D7O'}]}");
		this.options = this["options"];
		
		//建立节点并且画线
		this.upbuild();
		/*
		  增加onclick事件并传递this到clickField，传递的this表示当前function
		  如果不传递this给clickField，clickField 的 this就是他本身，测试中是input element
		 */
//		Event.observe($(this.fieldId),"click",this.clickField.bindAsEventListener(this));
	},
	upbuild:function(){
		var node,config;
		for(var i=0;i<this.json.nodes.length;i++ ){
			config = this.json.nodes[i];
			node = eval("new " + config.type + "()");
			node.id = config.id;
			node.setTitle(config.title);
			node.setContent(config.content);
			this.workflow.addFigure(node, config.x,config.y);
		}
		
		for(var i=0;i<this.json.lines.length;i++ ){
			config = this.json.lines[i];
			var line = new Connection();
			line.setSource((this.workflow.getFigure(config.sourcePort)).getPort("output"));
  			line.setTarget((this.workflow.getFigure(config.targetPort)).getPort("input"));
  			this.workflow.addFigure(line);
		}
	}
});