
var VersionInserts = {
	versionInserts: [],
	zIndex: 1200,

	add: function(versionInsert) {
		this.versionInserts.push(versionInsert);
	},
	resetTips: function(){	//全部显示
		dojo.debug("run resetTips.");
		for(i=0;i<this.versionInserts.length;i++){
			this.versionInserts[i].tip.activate();
		}
	},
	closeTips: function(){	//全部隐藏
		dojo.debug("run closeTips.");
		for(i=0;i<this.versionInserts.length;i++){
			this.versionInserts[i].tip.deactivate();
		}
	}
}

/*
 * svn显示文字功能
 */
var VersionInsert = Class.create();
VersionInsert.prototype = {
	initialize: function(element,json,jsonOther,entityName,showProperty,type) {
		this.element = element;
		
		
		this.entityName = entityName;
		this.showProperty = showProperty;
		this.type = type;
		this.date1 = json[entityName][showProperty];
		this.date2 = jsonOther[entityName][showProperty];
		this.tip = null;
		
		this.setFieldText();	//调用设置方法
		
		VersionInserts.add(this);
	},
	setFieldText : function(){
		dojo.debug("type: " + this.type);
		
		if(this.type == "show"){
			if(this.date1 != null){
			this.element.update(this.date1);
		}
		}else{
			this.showFieldText();
		}
	},
	/*
	 * 显示更新显示文字
	 * 未记入svn的用#bfb表示，改变的用#fd8表示
	 */
	showFieldText: function(){
		dojo.debug("date1: " + this.date1);
		dojo.debug("date2: " + this.date2);
		
		var ver = this.showVerNum("otherVer_hid");
		
		if(this.date1!=null && this.date2==null){	//删除
			$(this.element).setStyle({
			backgroundColor: '#FF3E3E',
			fontSize: '12px',
				'text-decoration': 'line-through',
				color:'white'
			});
			$(this.element).update(this.date1);
		}
		
		if(this.date1==null && this.date2!=null){	//增加
			$(this.element).setStyle({
			backgroundColor: '#bfb',
			'line-height':'150%'
			});
			$(this.element).update("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			this.tip = new Tip($(this.element), this.date2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
		}
		
		if(this.date1!=null && this.date2!=null && this.date1 != this.date2){	//修改
			$(this.element).setStyle({
				backgroundColor: '#fd8'
				});
			$(this.element).update(this.date1);
			this.tip = new Tip($(this.element), this.date2,{footer:ver,fixed:true,hook:{target:'topLeft',tip:'bottomLeft'}});
		}
		
		if(this.date1!=null && this.date2!=null && this.date1 == this.date2){	//显示
			dojo.debug("show");
		}
	},
	/*
	 * 显示版本号
	 */
	showVerNum: function (verFieldName){
		var ver = $(verFieldName).value;
		if(ver){
			return " Revision:" + ver ;
		}else{
			return "MAXINFO";
		}
	}
}
