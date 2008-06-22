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
	
	//删除最后一个增加的id
	var values = document.getElementsByName(getPagePersistentId());
	
	if(values.length - 1 <= 0){ //如果是0
		return;
	}
	
	var field = values[values.length - 1];
	
	field.value="";
}

function getPagePersistentId(){
//summary:
// 获得持久化ID
	return 'poid';
}

function initTblCell(cell,id){
//summary:
// 传入复制的样子和ID，增加新的一行
	var lastCell = dojo.byId(id).rows[0].cells[cell.cellIndex];
	cell.innerHTML = lastCell.innerHTML;
	
//	dojo.debug(cell.innerHTML);
	
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

function dellRowById(pid,id){
//summary:
// 删除指定table的指定行
	var obj = dojo.byId(pid);
	dojo.debug("del "  + id);
	if(id==0){
		var cellNum = obj.rows[0].cells.length; //获得有多少个td的个数
		
		for(var i=0; i<cellNum; i++){
			
			var cell = obj.rows[0].cells[i]; //获得一个td
			
			for(childIndex = 0;childIndex < cell.childNodes.length; childIndex++){	//遍历td里面的元素
				
				var child = cell.childNodes[childIndex];
				
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
	}else{
		obj.deleteRow(id);		
	}
}

function dellLastRow(pid){
//summary:
// 删除指定table的最后一个tr
	var obj = dojo.byId(pid);
	var id = obj.rows.length-1;
	dellRowById(pid,id);
}


function checkHidField(hidName,filed){
	var obj = document.getElementsByName(hidName+"_check");
	//获得选择的位置
	var index = 0;
	for(var t=0;t<obj.length;t++){
		if(obj[t]==filed){
			index = t;
		}
	}
	dojo.debug("click index " + index);
	
	//获得所有该名字元素
	var hidObje = document.getElementsByName(hidName);
	if(filed.checked){
		hidObje[index].value="on";
	}else{
		hidObje[index].value="off";
	}
	
	dojo.debug("hid value = " + hidObje[index].value);
}