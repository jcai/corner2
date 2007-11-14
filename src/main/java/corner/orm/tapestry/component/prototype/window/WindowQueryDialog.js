function myWindow(fieldId,props){
	var win;
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

	win = new Window(props);
	
	ddd = function(evt){
		win.showCenter();
	}
	
	dojo.event.connect(dojo.byId(fieldId),"onclick",this,"ddd");
}