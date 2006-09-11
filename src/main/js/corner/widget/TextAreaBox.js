/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.TextAreaBox");

dojo.require("dojo.widget.html.ComboBox");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.html.stabile");

dojo.widget.tags.addParseTreeHandler("dojo:TextAreaBox");

dojo.widget.defineWidget(
	"corner.widget.TextAreaBox",
	dojo.widget.html.ComboBox,
	{
		widgetType: "TextAreaBox",
		autoComplete: false,
		forceValidOption: false,
		comboBoxValue: null,
		
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
/*
		getState: function() {
			return {
				value: this.getValue(),
				label: this.getLabel()
			};
		},
*/
		onKeyUp: function(evt){
			this.comboBoxValue.value = this.textInputNode.value;
			this.setValue(this.textInputNode.value);
			this.setLabel(this.textInputNode.value);
		},

/*
		setState: function(state) {
			this.setValue(state.value);
			this.setLabel(state.label);
		},
*/
		setAllValues: function(value1, value2){
			this.setValue(value1);
			this.setLabel(value1);
		},
		//overwirte the default Html templete URL,use our Html templete instead
		//指定自己的html模版文件
		templatePath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.html"),
		templateCssPath: dojo.uri.dojoUri("../corner/widget/templates/TextAreaBox.css"),
		startSearchFromInput: function(){
			var searchStr = this.textInputNode.value
			if(searchStr != null && searchStr.length>0 && searchStr.search(';')>-1){
				this.startSearch(searchStr.substr(searchStr.lastIndexOf(';')+1,searchStr.length));
			}
			else{
				this.startSearch(this.textInputNode.value);
			}
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
			this.setAllValues(this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1)+tgt.getAttribute("resultName")+":", this.comboBoxSelectionValue.value.substr(0,this.comboBoxSelectionValue.value.lastIndexOf(';')+1));
			
			if(!evt.noHide){
				this.hideResultList();
				//设置是否选中刚TextAreaBox中的全部内容
				//this.setSelectedRange(this.textInputNode, 0, null);
			}
			this.tryFocus();
		}

	});
