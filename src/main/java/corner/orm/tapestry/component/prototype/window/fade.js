var Fade = {
	intTimeStep:20,
	isIe:(window.ActiveXObject)?true:false,
	intAlphaStep:(this.isIe)?5:0.05,
	curSObj:null,
	curOpacity:null,
	timer:null,
	
	elementOpen:function(objId){
		this.startObjVisible(objId,"show");
	},
	elementClose:function(objId){
		this.startObjVisible(objId,"hid");
	},
	startObjVisible:function(objId,action){
		this.curSObj=document.getElementById(objId);
		if(action == "show"){
			if(this.curSObj.style.display=="none"){
				this.setObjState(); 
			}
		}else{
			if(this.curSObj.style.display==""){
				this.setObjState(); 
			}
		}
	},
	/*
	 * 设置状态
	 */
	setObjState: function(evt){
		if (this.curSObj.style.display==""){
			this.curOpacity=1;
			this.setObjClose();
		}else{
			if(this.isIe){
				this.curSObj.style.cssText='DISPLAY: none;Z-INDEX: 1; FILTER: alpha(opacity=0); POSITION: absolute;';
				this.curSObj.filters.alpha.opacity=0;
			}else{
				this.curSObj.style.opacity=0 
			}
			this.curSObj.style.display='';
			this.curOpacity=0;
			this.setObjOpen();
		}
	},
	/*
	 * 打开层
	 */
	setObjOpen: function() {
		if(this.isIe){
			this.curSObj.filters.alpha.opacity+=this.intAlphaStep;
			if (this.curSObj.filters.alpha.opacity<100) {
				this.timer=setTimeout('Fade.setObjOpen()',this.intTimeStep)
			};
		}else{
			this.curOpacity+=this.intAlphaStep;
			this.curSObj.style.opacity = this.curOpacity;
			if (this.curOpacity<1) {
				this.timer=setTimeout('Fade.setObjOpen()',this.intTimeStep)
			};
		}
	},
	/*
	 * 关闭层
	 */ 
	setObjClose:function(){
		if(this.isIe){
			this.curSObj.filters.alpha.opacity-=this.intAlphaStep;
			if (this.curSObj.filters.alpha.opacity>0) {
				this.timer=setTimeout('Fade.setObjClose()',this.intTimeStep);
			}else {this.curSObj.style.display="none";}
		}else{
			this.curOpacity-=this.intAlphaStep;
			if (this.curOpacity>0) {
				this.curSObj.style.opacity =this.curOpacity;
				this.timer=setTimeout('Fade.setObjClose()',this.intTimeStep);
			}
			else {this.curSObj.style.display='none';} 
		}
	},
	/*
	 * 保持层状态
	 */
	keepState:function(){
		clearTimeout(this.timer);
	},
	/*
	 * 调用页面方法
	 */
	callFun:function(func){
		eval(func+"()");
	}
}