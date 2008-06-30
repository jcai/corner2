/*
 *	继承corner.js
 */
WindowDialogBase = CornerBuilder.create({
	extend: CornerBase.prototype,
	initialize: function(fieldElement, props,selectFunName,isShowCleanDiv,showCleanId,callShowCleanFunName){	
		this.fieldElement = fieldElement;	
		this.props = props;
		this.win =null;
		this.selectFunName=selectFunName;
		this.options = this["options"];
		/*
		  增加onclick事件并传递this到clickField，传递的this表示当前function
		  如果不传递this给clickField，clickField 的 this就是他本身，测试中是input element
		 */
		Event.observe(this.fieldElement,"click",this.clickField.bindAsEventListener(this));
		
		if(isShowCleanDiv){
			
			//设定下拉菜单的宽度
			contentWidth=dojo.html.getContentBox(fieldElement).width;
			dojo.html.setContentBox($(showCleanId),{width:contentWidth});
			dojo.debug("content Width:"+contentWidth);
			//得到菜单的位置
			with(dojo.html.getAbsolutePosition(fieldElement)){
				var _left=left;
				var _top=top;
			}
			//设定下拉菜单的位置
			with(dojo.html){
				_top+=dojo.html.getContentBox(fieldElement).height;
				dojo.debug("left :"+_left+" _top:"+_top);
				dojo.html.placeOnScreen($(showCleanId),_left,_top,[0,0],false);	
			}
			
			dojo.event.connect(fieldElement,"onmouseover",function(evt){Fade.elementOpen(showCleanId)});
			dojo.event.connect(fieldElement,"onmouseout",function(evt){Fade.elementClose(showCleanId)});
			dojo.event.connect($(showCleanId),"onmouseover",function(evt){Fade.keepState()});
			dojo.event.connect($(showCleanId),"onmouseout",function(evt){Fade.elementClose(showCleanId)});
			dojo.event.connect($(showCleanId),"onclick",function(evt){Fade.callFun(callShowCleanFunName)});
		}
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
		if(this.fieldElement.type == "checkbox"){
			if(this.fieldElement.checked){
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
	},
	getPos:function(obj){
		var x,y,objParent;
		with(obj){
			x=offsetLeft;
			y=offsetTop;
			objParent=offsetParent;
			while(objParent.tagName.toUpperCase()!= "BODY"){
				x+=objParent.offsetLeft;
				y+=objParent.offsetTop;
				objParent = objParent.offsetParent;
			}
		}
		return {"x":x,"y":y};
	}
});

/*
 *	WindowQueryDialog的js操作
 */
WindowQueryDialogAction = WindowDialogBase;