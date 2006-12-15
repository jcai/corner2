/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.TextAreaBox");

dojo.require("dojo.widget.ComboBox");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.html.stabile");



dojo.widget.defineWidget(
	"corner.widget.TextAreaBox",
	dojo.widget.ComboBox,
	{
		
		
		setValue: function(value) {
			this.comboBoxValue.value = value;
		},

		setLabel: function(value){
			this.comboBoxSelectionValue.value = value;
			if (this.textInputNode.value != value) {
				this.textInputNode.value = value;
			}
		},	  
		
		getLabel: function(){
			return this.comboBoxSelectionValue.value;
		},
		onKeyUp: function(evt){
			this.comboBoxValue.value = this.textInputNode.value;
			this.setValue(this.textInputNode.value);
			this.setLabel(this.textInputNode.value);
//			dojo.debug("in onKeyUp --- this.comboBoxValue.value:"+this.comboBoxValue.value);
		},
		compositionEnd: function( evt){
			this.comboBoxValue.value = this.textInputNode.value;
			this.setValue(this.textInputNode.value);
			this.setLabel(this.textInputNode.value);
//			dojo.debug("in compositionEnd --- this.comboBoxValue.value:"+this.comboBoxValue.value);
		},
		setAllValues: function(value1, value2){
			this.setValue(value1);
			this.setLabel(value1);
//			dojo.debug("in setAllValues --- this.comboBoxValue.value:"+this.comboBoxValue.value);
		},
		//overwirte the default Html templete URL,use our Html templete instead
		//指定自己的html模版文件
		templatePath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.html"),
		templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.css"),
		fillInTemplate: function( args,  frag){
			this.downArrowNode=document.createElement("span");
			corner.widget.TextAreaBox.superclass.fillInTemplate.call(this,args,frag);
			var source = this.getFragNodeRef(frag);
			dojo.html.removeClass(this.textInputNode,"dojoComboBox");
			this.domNode.style.textAlign="left";
			if(dojo.html.getAttribute(source,"rows")){
				this.textInputNode.setAttribute("rows",dojo.html.getAttribute(source,"rows"));
			}
			if(dojo.html.getAttribute(source,"cols")){
				this.textInputNode.setAttribute("cols",dojo.html.getAttribute(source,"cols"));
			}
		},
		startSearchFromInput: function(){
			
			var searchStr = "";
			var inputStr = this.textInputNode.value;
			dojo.debug("inputStr is:"+inputStr);
			if(inputStr != null && inputStr.length>0){
				if(inputStr.lastIndexOf(";")==-1){//无分号
					searchStr = inputStr;
				} else{//有分号
					inputStr = inputStr.substr(inputStr.lastIndexOf(";")+1);
					if(inputStr.length>0){
						searchStr = inputStr;						
					} else{
						searchStr = "+";//inputStr中出现分号的时候，说明已经不是第一次搜索，此时键入分号不应该列出选项
						                //因此设置一个特殊字符，让其不显示
					}
				}
			} 
			dojo.debug("searchStr is:"+searchStr.substr(searchStr.lastIndexOf("+")+1));
			if(searchStr.indexOf("+")){//出现加号说明使用者已经录入了';',此处把'+'去掉作为查询条件
				this.startSearch(searchStr.substr(searchStr.lastIndexOf("+")+1));			
			} else{
				this.startSearch(searchStr);
			}
//			var searchStr = this.textInputNode.value
//			if(searchStr != null && searchStr.length>0 && searchStr.search(';')>-1){
//				this.startSearch(searchStr.substr(searchStr.lastIndexOf(';')+1,searchStr.length));
//				dojo.debug("searchStr is:"+searchStr.substr(searchStr.lastIndexOf(';')+1,searchStr.length));
//			}
//			else{
//				this.startSearch(this.textInputNode.value);
//			}
		},

		//overwirte the selectOptionMethod,record all of the user input
		//在用户选择了下拉菜单之后，记录并现实先前输入的全部内容
		selectOption: function(evt){
			var tgt = null;
			if(!evt){
				evt = { target: this._highlighted_option };
			}
	
			if(!dojo.dom.isDescendantOf(evt.target, this.optionsListNode)){
				// handle autocompletion where the the user has hit ENTER or TAB
	
				// if the input is empty do nothing
				if(!this.textInputNode.value.length){
					return;
				}
				tgt = dojo.dom.firstElement(this.optionsListNode);
	
				// 判断用户输入是否在 option list 中
				/*
				if(!tgt || !this._isInputEqualToResult(tgt.getAttribute("resultName")),tgt.getAttribute("resultValue")){
					return;
				}*/
				// otherwise the user has accepted the autocompleted value
			}else{
				tgt = evt.target; 
			}
	
			while((tgt.nodeType!=1)||(!tgt.getAttribute("resultName"))){
				tgt = tgt.parentNode;
				if(tgt === document.body){
					return false;
				}
			}
	
			this.textInputNode.value = tgt.getAttribute("resultName");		
			this.selectedResult = [tgt.getAttribute("resultName"), tgt.getAttribute("resultValue")];		
			//自动填充':'
			//this.setAllValues(this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1)+tgt.getAttribute("resultName")+":", this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1));
			this.setAllValues(this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1)+tgt.getAttribute("resultName"), this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1));
			
			if(!evt.noHide){
				this.hideResultList();
				//设置是否选中刚TextAreaBox中的全部内容
				//this.setSelectedRange(this.textInputNode, 0, null);
			}
			this.tryFocus();
		}

	});
