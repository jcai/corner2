/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.
*/
function addRows(size,id){
//summary:
// 传入循环次数和tableID，批量增加
	for(var i=0 ; i < size-1;i++){
		addRow(id);
	}
}

function getTrIndex(obj){
	return obj.parentNode.parentNode.rowIndex;
}

function addRow(id){
//summary:
// 传入ID，获得要增加行的table
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
//summary:
// 传入复制的样子和ID，增加新的一行
	var lastCell = dojo.byId(id).rows[0].cells[cell.cellIndex];
	cell.innerHTML = lastCell.innerHTML;
	
	dojo.debug("1111111111111111");
	
	dojo.debug(cell.innerHTML);
	
	if(cell.childNodes != null && cell.childNodes.length > 0){
		dojo.debug("cell.childNodes  " + cell.childNodes);
		
		for(childIndex = 0;childIndex < cell.childNodes.length; childIndex++){
			var child = cell.childNodes[childIndex];
			
			dojo.debug("child  " + child);
			
			dojo.debug("child.type  " + child.type);
			
			switch(child.type){
			case "hidden":
				 child.value = "";
				 break;
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
	
	dojo.debug(cell.innerHTML);
	
	cell.className = lastCell.className;
	cell.align = lastCell.align;
	cell.height = lastCell.height;
}

function dellRow(pid,id){
//summary:
// 删除指定table的指定行
	var obj = dojo.byId(pid);
	dojo.debug("del "  + id);
	obj.deleteRow(id);
}