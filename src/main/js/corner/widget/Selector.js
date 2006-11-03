/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/

dojo.provide("corner.widget.Selector");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Select");

//定义一个Selector的widget.
dojo.widget.defineWidget(
	"corner.widget.Selector",
 	dojo.widget.Select,{},
 	"html"

);



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
		autoComplete: false,
		forceValidOption: false,
		updateFields:null,
		protoUrl:null,
		setAllValues: function(_label, value){
			dojo.debug("updateFields:"+this.updateFields);
			dojo.debug("value:"+value);
			
			this.setLabel(_label);
			if(this.updateFields && this.updateFields.indexOf(",")>0){
				vs=value.split(",");
				fs=this.updateFields.split(",");
				dojo.debug("values length:"+vs.length);
				dojo.debug("this.updateFields length:"+fs.length);
				
				
				if(vs.length!=fs.length){
					dojo.debug("values length != update Fields length \n"+"values:"+value+",\n"+"updateFields:"+fs);
					return;	
				}
				for(var i=0;i<fs.length;i++){
					if(fs[i] == 'this'){
						this.setValue(eval(vs[i]));
					}else{
						if(dojo.byId(fs[i])){
							dojo.byId(fs[i]).value=eval(vs[i]);
						}else{
							dojo.debug("cant find filed by id ["+fs[i]+"]");
						}

					}
				}
			}else{
				this.setValue(value);
			}
			dojo.debug("name:["+name+"]");
			dojo.debug("value:["+value+"]");
			
			dojo.debug("combox.value:["+this.getValue()+"]");
			dojo.debug("combox_selection.value:["+this.comboBoxSelectionValue.value+"]");
		},
		fillInTemplate: function(/*Object*/ args, /*Object*/ frag){
			// For inlining a table we need browser specific CSS
			dojo.html.applyBrowserClass(this.domNode);

			var source = this.getFragNodeRef(frag); 
			if (! this.name && source.name){ this.name = source.name; } 
			this.comboBoxValue.name = this.name; 
			this.comboBoxSelectionValue.name = this.name+"_selected";

			/* different nodes get different parts of the style */
			dojo.html.copyStyle(this.domNode, source);
			dojo.html.copyStyle(this.textInputNode, source);
			dojo.html.copyStyle(this.downArrowNode, source);
			
			dojo.html.removeClass(this.textInputNode,"dojoComboBox");
			this.domNode.style.textAlign="left";
			this.downArrowNode.style.verticalAlign="middle";
	        this.textInputNode.style.verticalAlign="middle";
			this.textInputNode.style.width=(source.offsetWidth-15)+"px";
			
			with (this.downArrowNode.style){ // calculate these later
				width = "0px";
				height = "0px";
			}

			var dpClass;
			if(this.mode == "remote"){
				dpClass = dojo.widget.incrementalComboBoxDataProvider;
			}else if(typeof this.dataProviderClass == "string"){
				dpClass = dojo.evalObjPath(this.dataProviderClass)
			}else{
				dpClass = this.dataProviderClass;
			}
			this.dataProvider = new dpClass();
			this.dataProvider.init(this, this.getFragNodeRef(frag));

			this.popupWidget = new dojo.widget.createWidget("PopupContainer", 
				{toggle: this.dropdownToggle, toggleDuration: this.toggleDuration});
			dojo.event.connect(this, 'destroy', this.popupWidget, 'destroy');
			this.optionsListNode = this.popupWidget.domNode;
			this.domNode.appendChild(this.optionsListNode);
			dojo.html.addClass(this.optionsListNode, 'dojoComboBoxOptions');
			dojo.event.connect(this.optionsListNode, 'onclick', this, 'selectOption');
			dojo.event.connect(this.optionsListNode, 'onmouseover', this, '_onMouseOver');
			dojo.event.connect(this.optionsListNode, 'onmouseout', this, '_onMouseOut');
			
			dojo.event.connect(this.optionsListNode, "onmouseover", this, "itemMouseOver");
			dojo.event.connect(this.optionsListNode, "onmouseout", this, "itemMouseOut");
		},
	/*		fillInTemplate: function(args, frag){
			//提高页面的展示效果
		
			corner.widget.Selector.superclass.fillInTemplate.call(this,args,frag);
			
			var source = this.getFragNodeRef(frag);
			dojo.html.removeClass(this.textInputNode,"dojoComboBox");
			this.domNode.style.textAlign="left";
			this.downArrowNode.style.verticalAlign="middle";
	        this.textInputNode.style.verticalAlign="middle";
			this.textInputNode.style.width=(source.offsetWidth-15)+"px";
			
			

		},
		* */
		convertResultList:function(results){
			dojo.debug("call here results"+results);
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
			//此处加入对禁用IE缓存的策略 通过动态构建URL
			//see http://dev.bjmaxinfo.com/projects/manufacturing-system/wiki/2006/10/26/09.11
			this.protoUrl=this.dataUrl;
			
			dojo.event.connect("before",this,"startSearch",this,"constructDynamicUrl");
			
			dojo.event.connect("before",this,"compositionEnd",this,"replaceKey");
			
		},
		replaceKey:function(evt){
			dojo.debug("replace key evt:"+evt);
			evt.keyCode = dojo.event.browser.keys.KEY_SPACE;
		},
		constructDynamicUrl:function(){
			var d = new Date();
			var time = d.getTime();
			tmpUrl=this.protoUrl;
			if (tmpUrl.indexOf('?') > 0)
				tmpUrl = tmpUrl+'&prevent_cache='+time;
			else
				tmpUrl = tmpUrl+'?prevent_cache='+time;
			
			this.dataUrl=tmpUrl;
			//dojo.debug("data url :["+tmpUrl+"]");
		}
});



