var fade_intTimeStep=20; 
var fade_isIe=(window.ActiveXObject)?true:false; 
var fade_intAlphaStep=(fade_isIe)?5:0.05; 
var fade_curSObj=null; 
var fade_curOpacity=null;
var fade_timer=null;
function startObjVisible(objId,action) {
	fade_curSObj=document.getElementById(objId);
	if(action == "show"){
		if(fade_curSObj.style.display=="none"){
			setObjState(); 
		}
	}else{
		if(fade_curSObj.style.display!="none"){
			setObjState(); 
		}
	}
} 
/*
 * 设置状态
 */
function setObjState(evTarget) {
	if (fade_curSObj.style.display==""){
		fade_curOpacity=1;
		setObjClose();
	}else{
		if(fade_isIe){
			fade_curSObj.style.cssText='DISPLAY: none;Z-INDEX: 1; FILTER: alpha(opacity=0); POSITION: absolute;';
			fade_curSObj.filters.alpha.opacity=0;
		}else{
			fade_curSObj.style.opacity=0 
		}
		fade_curSObj.style.display='';
		fade_curOpacity=0;
		setObjOpen();
	}
}
/*
 * 打开层
 */
function setObjOpen(){
	if(fade_isIe){
		fade_curSObj.filters.alpha.opacity+=fade_intAlphaStep;
		if (fade_curSObj.filters.alpha.opacity<100) {
			fade_timer=setTimeout('setObjOpen()',fade_intTimeStep)
		};
	}else{
		fade_curOpacity+=fade_intAlphaStep;
		fade_curSObj.style.opacity = fade_curOpacity;
		if (fade_curOpacity<1) {
			fade_timer=setTimeout('setObjOpen()',fade_intTimeStep)
		};
	}
}
/*
 * 关闭层
 */ 
function setObjClose(){
	if(fade_isIe){
		fade_curSObj.filters.alpha.opacity-=fade_intAlphaStep;
		if (fade_curSObj.filters.alpha.opacity>0) {
			fade_timer=setTimeout('setObjClose()',fade_intTimeStep);
		}else {fade_curSObj.style.display="none";}
	}else{
		fade_curOpacity-=fade_intAlphaStep;
		if (fade_curOpacity>0) {
			fade_curSObj.style.opacity =fade_curOpacity;
			fade_timer=setTimeout('setObjClose()',fade_intTimeStep);
		}
		else {fade_curSObj.style.display='none';} 
	}
}
/*
 * 保持层状态
 */
function keepState(){
	clearTimeout(fade_timer);
}
/*
 * 调用页面方法
 */
function callFun(func){
	eval(func+"()");
}