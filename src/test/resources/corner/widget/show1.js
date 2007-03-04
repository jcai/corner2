function aaa(){
//	alert('fdsafds');
	var a = dojo.byId('test');
	dojo.debug("a:" + a);
	a.insertCell();
}
function foo(){
	
	var oTr = dojo.byId('myTable').firstChild.firstChild.cloneNode(true);
	oTr.flag = "new";
	dojo.byId('myTable').firstChild.appendChild(oTr);
}
function moveOver(){
	var oTr = event.srcElement;
	while(oTr.tagName != "TR"){
		oTr = oTr.parentElement;
	}
	if(oTr.flag == "new")
		oTr.style.backgroundColor='blue';
}
function moveOut(){
	var oTr = event.srcElement;
	while(oTr.tagName != "TR"){
		oTr = oTr.parentElement;
	}
	if(oTr.flag == "new")
		oTr.style.backgroundColor='';
}