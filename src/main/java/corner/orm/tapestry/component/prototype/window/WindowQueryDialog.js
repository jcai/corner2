/*
 *	基础类
 */
WindowDialogBase = {};
WindowDialogBase.prototype = {
	initialize: function(fieldId, props,selectFunName){	
		this.fieldId = fieldId;	
		this.props = props;
		this.win =null;
		this.selectFunName=selectFunName;
		this.options = this["options"];
		/*
		  增加onclick事件并传递this到clickField，传递的this表示当前function
		  如果不传递this给clickField，clickField 的 this就是他本身，测试中是input element
		 */
		Event.observe($(this.fieldId),"click",this.clickField.bindAsEventListener(this));
	},
	loadFrame:function(){
		frameW=dojo.html.iframeContentWindow(this.win.getContent());
		if(frameW){
			frameW.queryBox=this;
			dojo.debug("frameW.queryBox : " + frameW.queryBox);
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
		ops = {"onload":this.loadFrame.bindAsEventListener(this)};	//传递this到loadFrame，确保this是function本身
		Object.extend(this.props, ops);
		
		this.win = new Window(this.props);
		Event.observe($(this.win.element.id + "_content"), "load", this.win.options.onload);
	},
	onSelect:function(element){
		eval(this.selectFunName+"(element)");
	}
};

/*
 *	构造类
 */
WindowDialog = {
	/* Function: create
	Returns a constructor for a new class that is specific to the structure passed.
	This new class is an extension of <WindowDialogBase>

	structure - The structure that defines node types and their options and hooks.
	*/
	create: function(structure){
		var newTreeClass = Class.create();
		Object.extend(newTreeClass.prototype,Object.extend(WindowDialogBase.prototype,structure));
		newTreeClass.prototype.constructor = newTreeClass;
		return newTreeClass;
	},
	showError: function(message){
		alert(message);
	}
};

/*
 *	WindowQueryDialog的js操作，一同写了 VersionCommentBox的checkbox判断
 */
WindowQueryDialogAction = WindowDialog.create({});