var SelectBox = Class.create();
SelectBox.prototype = {
	initialize: function(fromField,toField) {
		this.fromField = fromField;
		this.toField = toField;
	    Event.observe($(this.fromField),"dblclick",this.dblclickCopy.bindAsEventListener(this));
	},
	copyFormToList:function(){
		this.copyToList(true);
	},
	copyToFormList:function(){
		this.copyToList(false);
	},
	copyAllFormTo:function(){
		this.copyAll(true);
	},
	copyAllToForm:function(){
		this.copyAll(false);
	},
	//from表示:包含可选择项目的select对象名字 to表示:列出可选择项目的select对象名字
	//你可以根据你的具体情况修改
	copyToList:function(isTurn){
		var fromList = isTurn?$(this.fromField):$(this.toField);	
		var toList = isTurn?$(this.toField):$(this.fromField);
		if (toList.options.length > 0 && toList.options[0].value == 'temp'){
			toList.options.length = 0;
		}
		var sel = false;
		for (i=0;i<fromList.options.length;i++)	{
			var current = fromList.options[i];
			if (current.selected){
				sel = true;
				if (current.value == 'temp'){
					alert ('你不能选择这个项目!');
					return;
				}
				txt = current.text;
				val = current.value;
				toList.options[toList.length] = new Option(txt,val);
				fromList.options[i] = null;
				i--;
			}
		}
	},
	copyAll:function(isTurn){
		var fromList = isTurn?$(this.fromField):$(this.toField);	
		var toList = isTurn?$(this.toField):$(this.fromField);
		if (toList.options.length > 0 && toList.options[0].value == 'temp'){
			toList.options.length = 0;
		}
		for (i=0;i<fromList.options.length;i++){
			var current = fromList.options[i];
			toList.options[toList.length] = new Option(current.text,current.value);
			fromList.options[i] = null;
			i--;
		}
	},
	fnAdd:function(){
		var oOption = document.createElement("option");
	    oOption.appendChild(document.createTextNode(sName));
	
	    if (arguments.length == 3) 
	    {
	        oOption.setAttribute("value", sValue);
	    }
	
	    oListbox.appendChild(oOption);
	},
	allSelect:function(){
		List = document.forms[0].chosen;
		if (List.length && List.options[0].value == 'temp') return;
		for (i=0;i<List.length;i++){
			 List.options[i].selected = true;
		}
	},
	/*
	 * 双击操作
	 */
	dblclickCopy: function(evt){
		dojo.debug("call dblclickCopy");
  
		obj = dojo.html.getEventTarget(evt);
		selectedValue = obj.value;
		selectedText = obj.text;
		//判断使用什么取值 obj.text 或obj.options[obj.selectedIndex].text
		if(typeof(selectedText) == "undefined"){
		   //ie下 需这样取值
		   selectedText = obj.options[obj.selectedIndex].text;   
		}
		  
		dojo.debug("selectedText " + selectedText);
		dojo.debug("selectedValue " + selectedValue);
	}
}
