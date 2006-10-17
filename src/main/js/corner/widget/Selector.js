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
		fillInTemplate: function(args, frag){
			corner.widget.Selector.superclass.fillInTemplate.call(this,args,frag);
			//---------------调整Selector的样式
			//取得domNode的样式类.
			var textInputClass =dojo.html.getClass(this.domNode);
			var width=dojo.style.getContentWidth(this.domNode);
	
	        //删除domNode的样式.
	        dojo.html.removeClass(this.domNode,textInputClass,false);
	
	        //替换TextInputNode的样式为源节点的样式
	        dojo.html.replaceClass(this.textInputNode,textInputClass,"dojoComboBoxInput");
	        dojo.debug(this.textInputNode.style.width);
	        //设定widget中table的样式
	        //dojo.html.replaceClass(this.cbTableNode,textInputClass,"dojoComboBox");
	        
	        this["optionsListWrapper"].style.textAlign ="left";
	        
	        this.downArrowNode.style.verticalAlign="middle";
	        this.textInputNode.style.verticalAlign="middle";
	        //dojo.style.setContentWidth(this.domNode,width);
	        //设定widget中table的边框
	        this.cbTableNode.style.border="none";
		},
		convertResultList:function(results){
			dojo.debug("call here");
			var r=[];
			for(var i=0;i<results.length;i++){
				var tr=results[i];
				if(tr)
					results[i]=[tr[1],tr[0]];
			}
		},
		postCreate: function(){
			corner.widget.Selector.superclass.postCreate.call(this);
			dojo.event.connect("before",this,"openResultList",this,"convertResultList");
			
		}
	
});



