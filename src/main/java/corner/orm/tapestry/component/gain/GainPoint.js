/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.
*/
var GainPoint = Class.create();
GainPoint.prototype = {
	initialize: function(tableId,pagePersistentId,size,flags,checkBoxs) {
		this.tableId = tableId;
		this.pagePersistentId = pagePersistentId;
		this.size = size;
		this.flags = flags;
		this.checkBoxs = checkBoxs;
		
		//初始化
		this.addRows();
		this.init();
	},
	init:function(){
		var temp;
		for(var key in this.flags){
			var elements = document.getElementsByName(key);
			dojo.debug("elements.length " + elements.length);
			
			for (var i in elements){
				temp = 	this.flags[key][i];
				dojo.debug("type " + elements[i].type);
				
				if(temp){
					elements[i].value = temp;
					if(isContain(this.checkBoxs,key)){
						if(temp=="on"){
							document.getElementsByName(key+"_check")[i].checked = "checked";
						}
					}
				}
			}
		}
	},
	addRows:function(){
	//summary:
	// 传入循环次数和tableID，批量增加
		for(var i=0 ; i < this.size-1;i++){
			this.addRow();
		}
	},
	addRow : function(){
	//summary:
	// 传入ID，获得要增加行的table
		var tblObj = dojo.byId(this.tableId);
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
			initTblCell(newCell,this.tableId);
		}
		
		//删除最后一个增加的id
		var values = document.getElementsByName(this.pagePersistentId);
		
		if(values.length - 1 <= 0){ //如果是0
			return;
		}
		
		var field = values[values.length - 1];
		
		field.value="";
	},
	delLastRow : function(){
	//summary:
	// 删除指定table的最后一个tr
		var obj = dojo.byId(this.tableId);
		var id = obj.rows.length-1;
		delRowById(this.tableId,id);
	},
	delRowById : function(obj){
		delRowById(this.tableId,obj.parentNode.parentNode.rowIndex)
	}
}

var isContain = function (checkBoxs,value){
//summary:
// 是否是checkbox
	for(var i = 0 ; i<checkBoxs.length;i++){
		if(value==checkBoxs[i]){
			return true;
		}
	}
	return false;
}

var initTblCell = function(cell,id){
//summary:
// 传入复制的样子和ID，增加新的一行
	var lastCell = dojo.byId(id).rows[0].cells[cell.cellIndex];
	cell.innerHTML = lastCell.innerHTML;
	
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

var delRowById = function(pid,id){
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

var checkHidField = function(hidName,filed){
//summary:
//对hidden进行处理
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