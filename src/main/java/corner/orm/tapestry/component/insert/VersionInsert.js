
var VersionInserts = {
	versionInserts: [],
	zIndex: 1200,

	add: function(versionInsert) {
		this.versionInserts.push(versionInsert);
	},
	resetTips: function(){	//全部显示
		dojo.debug("run resetTips.");
		for(i=0;i<this.versionInserts.length;i++){
			$(this.versionInserts[i].element).setStyle({
				display: ''
			});
			$(this.versionInserts[i].diffElement).setStyle({
				display: 'none'
			});
		}
	},
	closeTips: function(){	//全部隐藏
		dojo.debug("run closeTips.");
		for(i=0;i<this.versionInserts.length;i++){
			$(this.versionInserts[i].element).setStyle({
				display: 'none'
			});
			$(this.versionInserts[i].diffElement).setStyle({
				display: ''
			});
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
		this.diffPostfix = "_diff";
		this.diffElement = element + this.diffPostfix;
		
		this.setFieldText();	//调用设置方法
		
		this.setDiffFieldText();
		
		$(this.diffElement).setStyle({
			display: 'none'
		});
		
		VersionInserts.add(this);
	},
	setDiffFieldText:function(){
		this.diffBy();
	},
	setFieldText : function(){
		dojo.debug("type: " + this.type);
		
		if(this.type == "show"){
			if(this.date1 != null){
			$(this.element).update(this.date1);
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
			if(this.date1 == 0){
				$(this.element).update(this.date1);
			}
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
	},
	/*
	 下面是XinDiff的调用
	 */
	createMap: function (fast) {
	    return document.all ? new ActiveXObject('Scripting.Dictionary') : new Dictionary(fast);
	},
	doDiff: function (left, right) {
	    var diff = new XinDiffEngine(this.createMap);
	    diff.assign(left.words, 1);
	    diff.assign(right.words, 0);
	
	    var output = new XinDiffOutput(this.createMap);
	    output.diff = diff;
	    output.symbols = [right.symbols, left.symbols];
	
	    diff.doDiff().serialize(diff, output);
	    dojo.debug("this.diffElement " + this.diffElement);
	    
	    $(this.diffElement).update(output.getHTML());
	},
	diffBy: function () {
		var v1 = this.date1;
		var v2 = this.date2;
		if(v1==null){v1 = "";}
		if(v2==null){v2 = "";}
		
	    this.doDiff(this.splitWords(v1), this.splitWords(v2));
	},
	splitCharacters: function (text) {
	    var chars = [];
	    var blanks = this.createMap(true);
	    var blank = '';
	    var length = text.length;
	    for (var i = 0; i < length; ++i) {
	        var ch = text.charAt(i);
	        switch (ch) {
	        case ' ':
	        case '\n':
	        case '\r':
	        case '\t':
	            blank += ch;
	            break;
	        default:
	            if (blank != '') {
	                blanks.Add(chars.length, blank);
	                blank = '';
	            }
	            chars.push(ch);
	            break;
	        }
	    }
	    if (blank != '') {
	        blanks.Add(chars.length, blank);
	    }
	    return {'words': chars, 'symbols': blanks};
	},
	splitLines: function (text) {
	    var lines = [];
	    var blanks = createMap(true);
	    var line = '';
	    var blank = '';
	    var length = text.length;
	    for (var i = 0; i < length; ++i) {
	        var ch = text.charAt(i);
	        switch (ch) {
	        case '\n':
	        case '\r':
	        //case ' ':
	        //case '\t':
	            if (line != '') {
	                lines.push(line);
	                line = '';
	            }
	            blank += ch;
	            break;
	        default:
	            if (blank != '') {
	                blanks.Add(lines.length, blank);
	                blank = '';
	            }
	            line += ch;
	            break;
	        }
	    }
	    if (line != '') {
	        lines.push(line);
	    }
	    else if (blank != '') {
	        blanks.Add(lines.length, blank);
	    }
	    return {'words': lines, 'symbols': blanks};
	},
	isLiteral: function (ch) {
	    return ((ch <= 'z') && (ch >= 'a')) || ((ch <= 'Z') && (ch >= 'A')) ||
	        ((ch <= '9') && (ch >= '0')) || (ch == '_') || (ch == '-') || (ch == '+');
	},
	splitWords: function (text) {
	    var words = [];
	    var symbols = this.createMap(true);
	    var length = text.length;
	    var word = '';
	    var symbol = '';
	    for (var i = 0; i < length; ++i) {
	        if (text.charCodeAt(i) >= 256) {
	            if (word != '') {
	                words.push(word);
	                word = '';
	            }
	            else if (symbol != '') {
	                symbols.Add(words.length, symbol);
	                symbol = '';
	            }
	            words.push(text.charAt(i));
	        }
	        else {
	            var ch = text.charAt(i);
	            if (this.isLiteral(ch)) {
	                if (symbol != '') {
	                    symbols.Add(words.length, symbol);
	                    symbol = '';
	                }
	                word += ch;
	            }
	            else {
	                if (word != '') {
	                    words.push(word);
	                    word = '';
	                }
	                symbol += ch;
	            }
	        }
	    }
	    if (word != '') {
	        words.push(word);
	    }
	    else if (symbol != '') {
	        symbols.Add(words.length, symbol);
	    }
	    return {'words': words, 'symbols': symbols};
	}
}

var Dictionary = Class.create();
Dictionary.prototype = {
	initialize: function(fast) {
	    this.map = {};
	
	    this.Add = fast ?
	        function(key, value) {
	            this.map[key] = value;
	        }:
	        function(key, value) {
	            this.map['_' + key] = value;
	        };
	
	    this.Exists = fast ?
	        function(key) {
	            return this.map.hasOwnProperty(key);
	        }:
	        function(key) {
	            return this.map.hasOwnProperty('_' + key);
	        };
	
	    this.Item = fast ?
	        function(key) {
	            return this.map[key];
	        }:
	        function(key) {
	            return this.map['_' + key];
	        };
	}
}
