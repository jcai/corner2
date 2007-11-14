/*
 *	WindowQueryDialog的js操作，一同写了 VersionCommentBox的checkbox判断
 */
var WindowQueryDialogAction = Class.create();
WindowQueryDialogAction.prototype = {
	initialize: function(fieldId, props){	
		this.fieldId = fieldId;	
		this.props = props;
		this.win =null;
		/*
		  增加onclick事件并传递this到clickField，传递的this表示当前function
		  如果不传递this给clickField，clickField 的 this就是他本身，测试中是input element
		 */
		Event.observe($(this.fieldId),"click",this.clickField.bindAsEventListener(this));
	},
	loadFrame:function(){
			frameW=dojo.html.iframeContentWindow(this.win.getContent());
			if(frameW){
				frameW.queryBox=this.win;
			}
	},
	clickField: function(evt){
		if(this.win==null){
			this.build();
		}
		
		if($(this.fieldId).type == "checkbox"){
			if(dojo.byId(this.fieldId).checked){
				this.win.showCenter();
			}else{
				this.win.close();
			}
		}else{
			this.win.showCenter();
		}
	},
	build: function() {
		options = {"onload":this.loadFrame.bindAsEventListener(this)};	//传递this到loadFrame，确保this是function本身
		Object.extend(this.props, options);
		
		this.win = new Window(this.props);
	}
};