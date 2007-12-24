/**
 * create the PushletFrame
 */
showPushletFrame = function(url){
	dojo.debug("url is:"+url);
	dojo.event.connect("after", window, "onload", function(){
		var PublishFrameDivNode = dojo.byId("_PublishFrameDiv");//get PublishFrameDiv node
		var pushletFrame = document.createElement("iframe");
		pushletFrame.name = "_PublishFrame";
		pushletFrame.id = "_PublishFrame";
		pushletFrame.visibility = "hidden";
		pushletFrame.style.width = "0px";
		pushletFrame.style.height = "0px";
		pushletFrame.style.border = "0px";
		pushletFrame.src=url;
		PublishFrameDivNode.appendChild(pushletFrame);
		dojo.debug("create iframe done");
	});
}

onMessageShow = function(message, url){
		if(message!=null && message.length >0){
		   win = new Window({"className":"alphacube","title":"消息提示窗",
	            "top ":"0",right:20, bottom:20, zIndex:1000,"width":"260","height":"120",
	            "maxWidth":"none","maxHeight":"none","minWidth ":"260","minHeight":"120",
	            "resizable":"true","closable":"true","minimizable":"true","maximizable":"true",
	            "draggable":"true",showEffect:Effect.BlindDown, hideEffect: Effect.SwitchOff,"showEffectOptions":"none","hideEffectOptions":"none","effectOptions":"none",
	            "opacity":1,"recenterAuto":"true","gridX":1,"gridY":1,"wiredDrag":"false",
	            "destroyOnClose":"false","all callbacks":"none"});
		            
			win.getContent().innerHTML="<a href=\"javascript:onMessageClick('"+url+"')\" id='_messageContent'>"+message+"</a>";
			win.show();
			window.setTimeout("closeMessageWindow(win)",7000);
		}
	}
	
onMessageClick = function(url){
	dojo.debug("MessageListPageName is:"+url);
	parent.location = url;
}

closeMessageWindow = function(win){
	win.close();
}