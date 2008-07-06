function initWindowDialog(element, title, url,selectFunName) {
	new WindowQueryDialogAction(element,{"className":"alphacube","title":title,
								   "url":url,"top ":"0","right":"0","width":"500","height":"300",
								   "maxWidth":"none","maxHeight":"none","minWidth ":"500","minHeight":"300",
								   "resizable":"true","closable":"true","minimizable":"true","maximizable":"true",
								   "draggable":"true","showEffectOptions":"none","hideEffectOptions":"none","effectOptions":"none",
								   "opacity":1,"recenterAuto":"true","gridX":1,"gridY":1,"wiredDrag":"false",
								   "destroyOnClose":"false","all callbacks":"none"},selectFunName);
}

//弹出树
function initWindowDialog(element, title, url,selectFunName,treeName,queryClassName,dependFields,page) {
	new LeftTreeDialog(element,{"className":"alphacube","title":title,
									   "url":url,"top ":"top:0","right":"left:0","width":"500","height":"300",
									   "maxWidth":"none","maxHeight":"none","minWidth ":"500","minHeight":"300",
									   "resizable":"true","closable":"true","minimizable":"true","maximizable":"true",
									   "draggable":"true","showEffectOptions":"none","hideEffectOptions":"none","effectOptions":"none",
									   "opacity":1,"recenterAuto":"true","gridX":1,"gridY":1,"wiredDrag":"false",
									   "destroyOnClose":"false","all callbacks":"none"},selectFunName,
									   treeName,queryClassName,dependFields,page);
}