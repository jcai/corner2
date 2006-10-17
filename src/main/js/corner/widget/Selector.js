/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.Selector");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");
dojo.require("dojo.widget.ComboBoxDataProvider");
//定义一个Selector的widget.
dojo.widget.defineWidget("corner.widget.Selector");
dojo.widget.tags.addParseTreeHandler("dojo:Selector");

//定义基本的Selector
corner.widget.Selector = function(){
	 dojo.widget.html.Select.call(this);
}
//继承
dojo.inherits(corner.widget.Selector,  dojo.widget.html.Select);
//对Selector进行扩展.
/**
 * 在原始的Select中。
 * 服务返回的字符串为{"Sabc":"abc","Sasdf":"asdf"}
 * 经过dataprovider转化后为：[["abc","Sabc"],["asdf","Sasdf"]]
 * 经过OpenResultList后转为Td的attribute，resultName="abc" resultValue="Sabc"
 * 而dojo在调用setAllValues处理的结果为：
 * textInputNode.value=resultName (abc)
 * combox.value=resultValue  (Sabc)(此值传入到服务器)
 * combox_selected.value=resultName;
 * 
 * 但是我们设计的是：
 * 服务器返回字符串为
 * {"abc":"Sabc","asdf","Sasdf"}
 * 经过dataprovider转化后为：[["Sabc","abc"],["Sasdf","asdf"]]
 * 
 * 经过OpenResultList之前转换Results为：[["abc","Sabc"],["asdf","Sasdf"]]
 *   经过转为Td的attribute，resultName="abc" resultValue="Sabc"
 * 
 * textInputNode.value=resultName (abc)
 * combox.value=resultValue  (Sabc)(此值传入到服务器)
 * combox_selected.value=resultName;
 
 * 
 */
dojo.lang.extend(corner.widget.Selector,{
		widgetType: "Selector",
		autoComplete: false,
		forceValidOption: false,
		setAllValues: function(name, value){
			corner.widget.Selector.superclass.setAllValues.call(this,name,value);
			dojo.debug("name:["+name+"]");
			dojo.debug("value:["+value+"]");
			
			dojo.debug("combox.value:["+this.getValue()+"]");
			dojo.debug("combox_selection.value:["+this.comboBoxSelectionValue.value+"]");
		},
		coonvertResultList:function(results){
			dojo.debug("call here");
			var r=[];
			for(var i=0;i<results.length;i++){
				var tr=results[i];
				if(tr)
					results[i]=[tr[1],tr[0]];
			}
			/*_this.clearResultList();
			if(!results.length){
				_this.hideResultList();
			}
	
			if(	(_this.autoComplete)&&
				(results.length)&&
				(!_this._prev_key_backspace)&&
				(_this.textInputNode.value.length > 0)){
				var cpos = _this.getCaretPos(_this.textInputNode);
				// only try to extend if we added the last character at the end of the input
				if((cpos+1) > _this.textInputNode.value.length){
					// only add to input node as we would overwrite Capitalisation of chars
					_this.textInputNode.value += results[1][0].substr(cpos);
					// build a new range that has the distance from the earlier
					// caret position to the end of the first string selected
					_this.setSelectedRange(_this.textInputNode, cpos, _this.textInputNode.value.length);
				}
			}
	
			var even = true;
			while(results.length){
				var tr = results.shift();
				if(tr){
					var td = document.createElement("div");
					td.appendChild(document.createTextNode(tr[1]));
					td.setAttribute("resultName", tr[1]);
					td.setAttribute("resultValue", tr[0]);
					td.className = "dojoComboBoxItem "+((even) ? "dojoComboBoxItemEven" : "dojoComboBoxItemOdd");
					even = (!even);
					_this.optionsListNode.appendChild(td);
					dojo.event.connect(td, "onmouseover", _this, "itemMouseOver");
					dojo.event.connect(td, "onmouseout", _this, "itemMouseOut");
				}
			}
	
			// show our list (only if we have content, else nothing)
			_this.showResultList(); */
		},
		postCreate: function(){
			corner.widget.Selector.superclass.postCreate.call(this);
			dojo.event.connect("before",this,"openResultList",this,"coonvertResultList");
			
		}
	
});



