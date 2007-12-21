DrawScaffold = CornerBuilder.create({
	extend: CornerBase.prototype,
	initialize: function(canvas, props){	
		this.canvas = canvas;	
		this.workflow = new Workflow(this.canvas);
		this.props = props;
		this.figures = new Map();
		this.options = this["options"];
		
		//建立节点并且画线
		this.upbuild();
	},
	upbuild:function(){
		for(var i=0;i<this.props.nodes.length;i++ ){
			node = eval('new '+this.props.nodes[i].type+'()');
			node.id = this.props.nodes[i].id
			node.setTitle(this.props.nodes[i].title);
			node.setContent(this.props.nodes[i].content);
			this.workflow.addFigure(node,this.props.nodes[i].x,this.props.nodes[i].y);
			
		}
		
		for(var i=0;i<this.props.lines.length;i++ ){
			config = this.props.lines[i];
			var line = new Connection();
			line.setSource((this.workflow.getFigure(config.sourcePort)).getPort("output"));
  			line.setTarget((this.workflow.getFigure(config.targetPort)).getPort("input"));
  			this.workflow.addFigure(line);
		}
	}
});