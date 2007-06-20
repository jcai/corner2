function addRow(id){
	var tblObj = dojo.byId(id);
	if(tblObj.rows){
		dojo.debug(tblObj.rows.length);
	}else{
		dojo.debug('aaa');
	}
	//追加行
	var newRow = tblObj.insertRow(-1);
	newRow.style.display = "";
	var cellNum = tblObj.rows[0].cells.length;
	
	//追加列
	for(colIndex = 0; colIndex < cellNum; colIndex++){
		var newCell = newRow.insertCell(-1);
		initTblCell(newCell,id);
	}
}
function initTblCell(cell,id){
	var lastCell = dojo.byId(id).rows[0].cells[cell.cellIndex];
	cell.innerHTML = lastCell.innerHTML;
	
	dojo.debug("1111111111111111");
	
	dojo.debug(cell.innerHTML);
	if(cell.children != null && cell.children.length > 0){
		for(childIndex = 0;childIndex < cell.children.length; childIndex++){
			var child = cell.children[childIndex];
			
			switch(child.type){
			case "text":
				 child.value = "";
				 break;
			case "checkbox":
				 child.value = "";
				 child.checked = false;
				 break;
			}
		}
	}
	cell.className = lastCell.className;
	cell.align = lastCell.align;
	cell.height = lastCell.height;
}

function buttonFun(){
	var obj = document.getElementsByName("text4");
	for(i = 1;i<obj.length;i++){
		dojo.debug(obj[i].value);
	}
}
function dellRow(pid,id){
	var obj = dojo.byId(pid);
	dojo.debug("del "  + id);
	obj.deleteRow(id);
}