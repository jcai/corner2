/*
 *	WindowQueryDialog的js操作，一同写了 VersionCommentBox的checkbox判断
 */
function windowQueryDialogAction(fieldId,props){
	this.win;
	dojo.debug(props);
	
	this.loadframe = function(){
			dojo.debug("frame on load!");
			frameW=dojo.html.iframeContentWindow(win.getContent());
			if(frameW){
				frameW.queryBox=win;
		}
	}
	
	var options = {"onload":this.loadframe};
	Object.extend(props, options);

	this.win = new Window(props);
	
	action = function(evt){
		if(dojo.byId(fieldId).type == "checkbox"){
			if(dojo.byId(fieldId).checked){
				this.win.showCenter();
			}else{
				this.win.close();
			}
		}else{
			this.win.showCenter();
		}
	}
	
	dojo.event.connect(dojo.byId(fieldId),"onclick",this,action);
}
