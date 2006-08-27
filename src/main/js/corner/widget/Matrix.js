/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.Matrix");

dojo.require("dojo.event.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.collections.ArrayList");

//why define widget?
dojo.widget.defineWidget("corner.widget.Matrix");

dojo.widget.tags.addParseTreeHandler("dojo:Matrix");

corner.widget.Matrix=function(){
	dojo.widget.HtmlWidget.call(this);
}
dojo.inherits(corner.widget.Matrix, dojo.widget.HtmlWidget);

dojo.lang.extend(corner.widget.Matrix,{
	//初始化
	initializer: function(){
   		dojo.debug("initializer");
   		//采用AOP定义事件响应机制
   		dojo.event.connect("after",this,"removeCol",this,"sumValue");
   		dojo.event.connect("after",this,"removeRow",this,"sumValue");
   		dojo.event.connect("after",this,"addRow",this,"sumValue");
   		dojo.event.connect("after",this,"addColumn",this,"sumValue");
   	},
   	//widget类型
   	widgetType : "Matrix",
	postCreate: function(args, frag, parentComp) {
		
	},
   	//行和列定义.
	rows:function(){return this.matrixTable.rows.length;},
	cols:function(){if(this.rows() == 0) return 0;else return this.matrixTable.rows[0].cells.length;},   
	//模板文件和css样式文件.
    templatePath : dojo.uri.dojoUri("../corner/widget/templates/Matrix.html"),
    templateCSSPath : dojo.uri.dojoUri("../corner/widget/templates/Matrix.css"),
    
    //dom Node
    matrixContainer:null,
    matrixTable:null,
    sumTextarea:null,
    
   
    inputNodes:new dojo.collections.ArrayList(),
    
    //增加一行.
    addRow:function(evt){
    	//加入对行进行操作的按钮
    	if(this.rows()==0){
    		//增加行和列的操作按钮
	    	

	    	row=this.matrixTable.insertRow(0);
	    	row.insertCell(0);
	    	
	    	inputChildren=new dojo.collections.ArrayList();
	    	this.inputNodes.add(inputChildren);

    	}
    	currentRows=this.rows();
    	currentCols=this.cols();
    	//插入行
    	row = this.matrixTable.insertRow(currentRows);
    	
    	//加入对行进行操作的单元格
    	cell=row.insertCell(0);
    	input=this.createRowActionElement('rc'+currentRows+0);
    	cell.appendChild(input);
    	dojo.event.connect(input,"onclick",this,"removeRow");
    	
    	//此行所有input的集合
    	inputChildren=new dojo.collections.ArrayList();
    	
		//插入行对应的列
		for(var i=1;i<currentCols;i++){
			id='rc'+currentRows+i;
			
	        cell=row.insertCell(i);
	        input=this.createInputElement(id);
		    cell.appendChild(input);
		    
		    //加入数据
		    inputChildren.add(input);
		}
		this.inputNodes.add(inputChildren);

		
    },
    //增加一列.
    addColumn:function(evt){
    	if(this.rows()==0){
    		return;
    	}
		currentRows=this.rows();
    	currentCols=this.cols();
    	
    	//加入对列操作的单元格
		cell=this.matrixTable.rows[0].insertCell(currentCols);

    	input=this.createColActionElement('rc'+0+currentCols);
		cell.appendChild(input);
		dojo.event.connect(input,"onclick",this,"removeCol");
		
    	//逐行加入列
    	for(var i=1;i<currentRows;i++){
			id='rc'+i+currentCols;
	        row=this.matrixTable.rows[i];
	        cell=row.insertCell(currentCols);
	        input=this.createInputElement(id);
	        cell.appendChild(this.createInputElement(id));
       		this.inputNodes.item(i).add(input);
       		

	    }
       	
    },
    //创建一个Input
    createInputElement:function (id){
        input=document.createElement("input");
        input.value=0;
        input.type="text";

        dojo.event.connect(input,"onkeyup",this,"sumValue");
        
	    return input;
	},
	//创建对列进行操作的Input
	createColActionElement:function(id){
		input = document.createElement("input");
        input.value="删除本列";
        input.type="button";
        return input;
	},
	/**
	 * 创建行操作元素
	 * @TODO 将和createColActionElement合并.
	 */
	createRowActionElement:function(id){
		input = document.createElement("input");
		input.type="button";
        input.value="删除本行";
        return input;
	},
	/**
	 * 删除行。
	 */
	removeRow:function(evt){
		var src=dojo.html.getEventTarget(evt);
		index=src.parentNode.parentNode.rowIndex;
		this.matrixTable.deleteRow(index);
		this.inputNodes.removeAt(index);

		
	},
	/**
	 * 删除列.
	 */
	removeCol:function(evt){
		var src=dojo.html.getEventTarget(evt);
		colIndex=src.parentNode.cellIndex;
		rowCount=this.rows();
		
		for(var i=0;i<rowCount;i++){
			row = this.matrixTable.rows[i];
			
			row.deleteCell(colIndex);
			if(i>0){
				rowNodes=this.inputNodes.item(i);
				dojo.debug("colIndex ["+colIndex+"]");
				rowNodes.removeAt(colIndex-1);
			}
		}
		
	},
	/**
	 * 统计值
	 */
	sumValue:function(evt){
		value="";

		this.inputNodes.forEach(function(item,i){
			if(i==0){//工具行
				return;
			}
			rowValue="";
			item.forEach(function(input,j){
				rowValue+=input.value;
				rowValue+=",";
			});
			if(rowValue.length>0){
				rowValue=rowValue.substr(0,rowValue.length-1);
			}
			value+=rowValue;
			value+="\n";
		});
		this.sumTextarea.value=value;
	}
    
});
