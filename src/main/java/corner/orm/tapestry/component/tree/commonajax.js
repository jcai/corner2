function json_decode(txt){
	try{
		return eval('('+txt+')');
	}catch(ex){
		alert("An exception occurred in the script. Error name: " + e.name + ". Error message: " + e.message);
	}
}

/* Changes:
made hiding of external control an option
relocated useless hook for onEnterEditMode to after createForm, now uses options
*/
Ajax.InPlaceEditor.prototype.enterEditMode = function(evt) {
	if (this.saving || this.editing){ return; }
	this.editing = true;
	if(this.options.externalControl && this.options.hideExternalControl){
		Element.hide(this.options.externalControl);
	}
	Element.hide(this.element);
	this.createForm();
	this.element.parentNode.insertBefore(this.form, this.element);
	if(this.options.onEnterEditMode){ this.options.onEnterEditMode.call(this); }
	if(!this.options.loadTextURL){ Field.scrollFreeActivate(this.editField); }
	// stop the event to avoid a page refresh in Safari
	if(evt){ Event.stop(evt); }
	return false;
};

/* rewrite to use above "options.onEnterEditMode" modification and not use separate class? */
Ajax.InPlaceDateEditor = Class.create();
Object.extend(Ajax.InPlaceDateEditor.prototype, Ajax.InPlaceEditor.prototype);
Object.extend(Ajax.InPlaceDateEditor.prototype, {
	initialize: function(element, url, options, calOptions) {
		this.calOptions = Object.extend({
			ifFormat: "%a %m-%d-%y"
		}, calOptions || {});
		Object.extend(new Ajax.InPlaceEditor(element, url, Object.extend({
			formClassName:'date-ipe'
		},options||{})), this);
		if(Element.empty(element)){ element.innerHTML = '---'; }
	},
	createEditField: function() {
	  var text = this.getText();
	  if(text == '---'){ text = ''; }
	  var obj = this;
	    
		this.options.textarea = false;
		var textField = document.createElement("input");
		textField.obj = this;
		textField.type = "text";
		textField.name = "value";
		textField.value = text;
		textField.style.backgroundColor = this.options.highlightcolor;
		textField.className = 'editor_field';
	  var size = this.options.size || this.options.cols || 0;
	  if (size !== 0){ textField.size = size; }
		this.editField = textField;
		Calendar.setup(Object.extend(this.calOptions,{
			inputField : this.editField,
			eventName: "focus"
		}));
		this.form.appendChild(this.editField);
	}
});


//make into Table.RowInfo
var TableRowInfo = {
	toggle: function(element,options){
		if(Element.visible(element)){ this.hide(element,options); }
		else{ this.show(element,options); }
	},
	show: function(element,options){
		if(Element.visible(element)){ return; }
		if(options.reload){ this.load(element,options); }
		else{ this._show(element,options); }
	},
	_show: function(element,options){
		var cell = Element.findElement(element,'td');
		var row = cell.parentNode;
		var start = cell.cellIndex;
		var offset = options.offset || 0;
		if(start+1 < row.cells.length-offset){
			for(var i=start+1; i < row.cells.length-offset; i++){
				Element.hide(row.cells[i]);
				row.cells[start].colSpan++;
			}
		}
		new Effect.BlindDown(element,{
			queue: {scope:'TableRowInfo'}
		});
		if(options.scrollTo !== false){
			new Effect.ScrollTo(row,{
				queue: {scope:'TableRowInfo',position:'end'}
			});
		}
	},
	load: function(element,options){
		Element.hide(element);
		new Ajax.Updater(element,options.page,Object.extend({
			onComplete: function(xhr,obj){
				setTimeout(function(){TableRowInfo._show(element,options);},50);
			}
		}, options.ajaxOptions || {} ));
	},
	hide: function(element,options){
		new Effect.Fade(element,Object.extend({
			afterFinish: this.restore,
			to: 0.6
		},options.effectOptions || {}));
	},
	restore: function(effect){
		effect.element.hide().setOpacity(1.0);
		var cell = Element.findElement(effect.element,'td');
		var row = cell.parentNode;
		for(var i=cell.cellIndex+1; i < row.cells.length; i++){
			if(Element.visible(row.cells[i])){ return; }
			cell.colSpan--;
			Element.show(row.cells[i]);
		}
	}
};

var Infobox = {
	last_clicked: '',
	page: '',
	loadDataOrToggle: function(target,options){
		if(this.last_clicked == options.what){
			Element.toggle(target);
		}else{
			this.loadData(target,options);
			this.last_clicked = options.what;
			Element.show(target);
		}
	},
	loadData: function(target,options){
		var params = 'a=load_data&'+$H(options).toQueryString();
		new Ajax.Updater(target,this.page,{
			asynchronous:true,
			evalScripts:true,
			parameters:params
		});
	}
};

/*
Example:
insertion: myInsertion(target,responseText){}
onComplete: myOnComplete(xhr,object){} 
*/
function getData(target,form_name,options){
	target = $(target);
	var params = 'a=get_form&form_name='+form_name+'&id='+target.id;
	new Ajax.Updater(target,page,Object.extend({
		asynchronous:true,
		evalScripts:true,
		parameters:params
	},options ||{}));
}
function doDelete(me){
	var val = document.getElementsByClassName('data',me,'span');
	var data = '';
	for(var i=0; i < val.length; i++){
		data += val[i].innerHTML+(i==val.length-1?'':' ');
	}
	if(confirm('Are you sure you want to delete "'+data+'"?')){
		var table = me.id.substring(0,me.id.indexOf('_'));
		var id = me.id.substring(me.id.lastIndexOf('_')+1,me.id.length);
		new Ajax.Request(page,{
			asynchronous:true,
			parameters:'a=delete&table='+table+'&id='+id,
			onSuccess: function(xhr,obj){
				if(obj && obj.error){
					alert(obj.error);
				}else{
					me.parentNode.removeChild(me);
				}
			}
		});
	}
	return false;
}

Nubbin = Class.create();
Nubbin.prototype = {
	initialize: function(parent,hoverTag,className,options){
		this.options = Object.extend({
			tagName: 'div',
			observers: [],
			getElements: function(parent){
				var elements = [];
				if(this.parent.getAttribute('reordering') == 'yes'){ return elements; }
				return [parent.down(this.options.tagName+'.'+this.className)];
			}
		}, options || {});
		this.className = className;
		this.hoverTag = hoverTag;
		this.parent = $(parent);
		this.events = new EventCache();
		this.mouseOverListener = this.show.bindAsEventListener(this);
		this.mouseOutListener = this.hide.bindAsEventListener(this);
		$A(this.parent.getElementsByTagName(hoverTag)).each(this.observe.bind(this));
	},
	observe: function(element){
		this.events.observe(element,'mouseover',this.mouseOverListener);
		this.events.observe(element,'mouseout',this.mouseOutListener);
		this.options.observers.each(function(observer){
			var button = document.getElementsByClassName(observer.className,element,observer.tagName || 'div')[0];
			if(button){ this.events.observe(button,observer.name || 'click',observer.observer); }
		}.bind(this));
	},
	show: function(event){
		clearTimeout(Nubbin.timer);
		Nubbin.hideAll();
		var parent = $(Event.findElement(event,this.hoverTag));
		this.options.getElements.call(this,parent).each(function(el){
			Nubbin.nubbins.push(el);
			el.style.display = 'block';
		});
	},
	hide: function(){
		Nubbin.timer = setTimeout(Nubbin.hideAll,100);
	},
	stopObserving: function(element){
		this.events.stopObserving(element,'mouseover',this.mouseOverListener);
		this.events.stopObserving(element,'mouseout',this.mouseOutListener);
		this.options.observers.each(function(observer){
			var button = document.getElementsByClassName(observer.className,element,observer.tagName || 'div')[0];
			if(button){ this.events.stopObserving(button,observer.name || 'click',observer.observer); }
		}.bind(this));
	},
	dispose: function(){
		this.events.stopObserving();
	}
};
Nubbin.timer = null;
Nubbin.nubbins = [];
Nubbin.hideAll = function(){
	while(Nubbin.nubbins.length){ Nubbin.nubbins.shift().hide(); }
};

//make into a class that takes a function, key, page, requestOptions, timeout
//add clearKey
var optionCache = [];
var optionCacheRequests = [];
function fillSelectCached(receiver,key,page,options){
	var onComplete = false;
	if(options && options.onComplete){
		onComplete = options.onComplete;
		delete options.onComplete;
	}
	if(optionCache[key]){
		if(optionCache[key] == '_option_cache_loading'){
			var oldFunction = optionCacheRequests[key].options.onComplete;
			optionCacheRequests[key].options.onComplete = function(xhr,json){
				fillSelectFromObject(receiver,optionCache[key]);
				//if(oldFunction){ try{ oldFunction(xhr,json); }catch(ex){} }
				if(onComplete){ try{ onComplete(xhr,json); }catch(ex){} }
			};
		}else{
			fillSelectFromObject(receiver,optionCache[key]);
			if(onComplete){ try{ onComplete(optionCacheRequests[key].transport,optionCache[key]); }catch(ex){} }
		}
	}else{
		optionCache[key] = '_option_cache_loading';
		optionCacheRequests[key] = new Ajax.Request(page,Object.extend({
			parameters: 'action=getOptions&id='+key,
			onComplete: function(xhr,json){
				if(json && json.error){ alert(json.error); return false; }
				if(!json || !json.options){ json = json_decode(xhr.responseText); }
				if(!json){ alert('Error retreiving '+key); return false; }
				optionCache[key] = json;
				fillSelectFromObject(receiver,optionCache[key]);
				if(onComplete){ try{ onComplete(xhr,json); }catch(ex){} }
				return true;
			}
		},options || {}));
	}
}

/* Can be called on the "a" of a list element that was created using one of these functions,
	either by a click event on the remove link, or by calling removeListItem.bind(element)(); */
function removeListItem(event){
	var element = (this.parentNode ? this : Event.element(event));
	Event.stopObserving(element,'click',removeListItem);
	if(element.disposeMe){ element.disposeMe.dispose(); }
	var parent = Element.findElement(element,'li');
	parent.parentNode.removeChild(parent);
}
function addDoubleComboSelect(container, name, page, comboOptions){
	var masterSelect = Builder.node('select');
	var slaveSelect = Builder.node('select',{name:name+'[]'});
	var remLink = Builder.node('a',[Builder.node('img',{src: '../images/icons/delete.png', alt: ''})]);
	$(container).appendChild(Builder.node('li',[masterSelect, slaveSelect, remLink]));
	Event.observe(remLink,'click',removeListItem);
	remLink.disposeMe = new Ajax.DoubleCombo(masterSelect, slaveSelect, page, comboOptions);
	return masterSelect;
}

function addReadOnly(container, name, value, text){
	var remLink = Builder.node('a',[Builder.node('img',{src: '../images/icons/delete.png', alt: ''})]);
	var newItem = Builder.node('li',[
		Builder.node('input',{type: 'hidden', name: name+'[]', value: value}),
		Builder.node('input',{type: 'text', readonly: true, value: text}), remLink ]);
	$(container).appendChild(newItem);
	Event.observe(remLink,'click',removeListItem);
	return newItem;
}

function addDoubleReadOnly(container, name, value, text1, text2){
	var newItem = addReadOnly(container, name, value, text2);
	var tradeText = Builder.node('input',{ type: 'text', value: text1, readonly: true });
	newItem.insertBefore(tradeText,newItem.firstChild);
}

Ajax.InPlaceMultiSelectEditor = Class.create();
Object.extend(Ajax.InPlaceMultiSelectEditor.prototype, Ajax.InPlaceEditor.prototype);
Object.extend(Ajax.InPlaceMultiSelectEditor.prototype, {
	initialize: function(element, url, options) {
		Object.extend(new Ajax.InPlaceEditor(element, url, Object.extend({
			selectObject: false,
			selectName: false,
			minIndex: 0,
			splitString: ', ',
			emptyText: 'empty',
			addText: '(+)',
			callback: function(form,value){
				return Form.serialize(form);
			}
		}, options || {})), this);
	},
	createEditField: function() {
    this.oldInnerHTML = this.getText();
		this.options.textarea = false;
		if(!this.options.selectName){ this.options.selectName = this.element.id || 'multiSelect'; }
		this.selectDiv = Builder.node('div',{id:this.options.selectName+'-div'});
		var addLink = Builder.node('a',{href:'javascript:;'},[this.options.addText]);
		addLink.onclick = this.addSelectToDiv.bind(this);
		/*this.editField = */ var hidden = Builder.node('input',{type:'hidden',name:'selectName',value:this.options.selectName});
		
		//this.form.appendChild(this.editField);
		this.form.appendChild(hidden);
		this.form.appendChild(addLink);
		this.form.appendChild(this.selectDiv);

		var newSel;
		if(this.options.loadTextURL){
			this.selectDiv.innerHTML = this.options.loadingText;
			this.loadExternalText();
		}else{
			var text = this.getText();
			if(text != this.options.emptyText){
				text.split(this.options.splitString).each(function(str){
					newSel = this.addSelectToDiv();
					selectText(newSel,str);
				}.bind(this));
			}else{
				newSel = this.addSelectToDiv();
			}
			this.editField = newSel;
		}
	},
  loadExternalText: function() {
    Element.addClassName(this.form, this.options.loadingClassName);
    this.disableEditField();
    new Ajax.Request(
      this.options.loadTextURL,
      Object.extend({
        asynchronous: true,
        onComplete: this.onLoadedExternalText.bind(this)
      }, this.options.ajaxOptions)
    );
  },
  onLoadedExternalText: function(xhr, obj) {
    Element.removeClassName(this.form, this.options.loadingClassName);
		var newSel;
    obj.each(function(val){
    	newSel = this.addSelectToDiv();
    	selectValue(newSel,val);
    });
    this.editField = newSel;
    this.enableEditField();
  },
  disableEditField: function(){Form.disable(this.form);},
  enableEditField: function(){Form.enable(this.form);},
	addSelectToDiv: function(id){
		return addSelectToElement(this.selectDiv, this.options.selectName, this.options.minIndex, this.options.selectObject, id||null);
	}	
});

var Cursor = {
	cursors: {loading: '/images/ajax-loader.gif'},
	offsetX: 14,
	offsetY: 14,
	show: function(){
		Event.observe(document.body, 'mousemove', this.updateListener);
		this.cursorDiv.show();
	},
	hide: function(){
		this.cursorDiv.hide();
		Event.stopObserving(document.body, 'mousemove', this.updateListener);
	},
	set: function(value) {
		if(this.cursor == value){ return this; }
		this.cursor = value;
		this.create().firstChild.src = Cursor.cursors[this.cursor] || this.cursor;
		return this;
	},
	update: function(event){ /* keep this function as fast as possible, only set position if cursor is showing */
		if(event.target && event.target.tagName == 'OPTION'){ return; }
		this.cursorDiv.style.left = Event.pointerX(event) + this.offsetX + 'px';
		this.cursorDiv.style.top = Event.pointerY(event) + this.offsetY + 'px';
	},
	create: function() {
		if(this.cursorDiv){ return this.cursorDiv; }
		this.cursorDiv = Element.extend(Builder.node('div',[Builder.node('img',{alt:''})]));
		this.cursorDiv.setStyle({
			position: 'absolute',
			'z-index': 99999,
			display: 'none'
		});
		this.updateListener = this.update.bindAsEventListener(this);
		Event.observe(document.body, 'click', this.updateListener);
		document.body.appendChild(this.cursorDiv);
		return this.cursorDiv;
	},
	destroy: function() {
		Event.stopObserving(document.body, 'click', this.updateListener);
		if(Element.visible(this.cursorDiv)){ Event.stopObserving(document.body, 'mousemove', this.updateListener); }
		if(this.cursorDiv){ this.cursorDiv.remove(); }
		this.cursorDiv = this.cursor = false;
	}
};

function getDebugDiv(){
	var debug = $('debug_messages');
	if(!debug){
		var container = $('container') || document.body;
		debug = Builder.node('div',{id:'debug_messages',className:'debug'});
		new Insertion.Bottom(container,'<h1>Debug Console:<small>'+
			'<a href="javascript:;" onclick="$(\'debug_messages\').innerHTML=\'\';">(clear)</a></small></h1>');
		container.appendChild(debug);
	}
	return debug;
}

function getRequestDetails(request){
	var message = '';
	try{
		message += 'Method: '+request.method+'<br/>';
		message += 'URL: '+request.url+'<br/>';
		message += 'Parameters: '+$H(request.parameters).toQueryString()+'<br/>';
		message += 'Response: '+request.transport.status+' '+request.transport.statusText+'<br/>';
		var jsonHeader = request.transport.getResponseHeader('X-JSON');
		var jsonText = (jsonHeader ? jsonHeader.escapeHTML():false);
		message += 'X-JSON: '+(jsonText ? '<pre>'+"\n\n"+jsonText+"\n"+'</pre>':'')+'<br/>';
		var resText = request.transport.responseText.escapeHTML();
		message += 'Response Text: '+(resText ? '<pre>'+"\n\n"+resText+"\n"+'</pre>':'')+'<br/>';
		return message;
	}catch(ex){ return message+'<br/>Invalid XML Request: '+getExceptionDetails(ex); }
}
function getExceptionDetails(ex){
	var message = 
	'<b>'+ex.name+':</b><pre>'+ex.message+'</pre><br/>'+
	(ex.fileName && ex.lineNumber ? ex.fileName+' (line <b>'+ex.lineNumber+'</b>)<br/><br/>':'')+
	(ex.description ? 'Description:<pre>'+ex.description+'</pre><br/>':'')+
	(ex.stack ? 'Stack trace: <pre>'+ex.stack+'</pre><br/>':'')+
	(ex.number ? 'Number: '+ex.number+'<br/>':'')+
	(ex['opera#sourceloc'] ? 'Location: <b>'+ex['opera#sourceloc']+'</b><br/>':'');
	return message;
}

function printDebug(){
	var debug = getDebugDiv();
	new Insertion.Bottom(debug,'<hr/>');
	for(var i=0; i < arguments.length; i++){
		var msg = arguments[i] === undefined ? 'undefined' : arguments[i] === null ? 'null' : arguments[i].toString();
		new Insertion.Bottom(debug,msg); }
}
//if(!console){ console = new Object(); }
//if(!console.debug){ console.debug = printDebug; }

var _debug_enabled = false;
function enableAjaxDebug(){
	if(_debug_enabled){ return;	}
	_debug_enabled = true;
	var debug = getDebugDiv();
	$A(debug.getElementsByTagName('pre')).each(function(el){
		new ToggleButton(el);
	});
	Ajax.Responders.register({
		onCreate: function(request, transport) {
			request.startTime = time();
			request.debugDiv = 'debug-'+request.startTime;
			var debugDiv = getDebugDiv();
			var hr = Builder.node('hr');
			var params = $H(request.parameters).toQueryString();
			debugDiv.appendChild(hr);
			new Insertion.Bottom(debugDiv,
				'<div id="'+request.debugDiv+'">'+
				'Method: '+request.method+'<br/>'+
				'URL: '+request.url+'<br/>'+
				'Parameters: '+params+'<br/>'+
				'</div>'
			);
			var showHTML = '<strong>'+request.method.toUpperCase()+' '+request.url+' ? '+params+'</strong>';
			new ToggleButton($(request.debugDiv),{showHTML: showHTML, sibling: hr.nextSibling});
		},
		onComplete: function(request, transport, obj) {
			var diff = time() - request.startTime;
			var div = $(request.debugDiv) || getDebugDiv();
			var contentLength = request.getHeader('Content-Length') || transport.responseText.length;
			var resText = transport.responseText.escapeHTML();
			var jsonText = (request.getHeader('X-JSON') || '').escapeHTML();
			new Insertion.Bottom(div,
			'Elapsed time: <b>' + diff/1000 + ' seconds</b><br/>' +
			'HTTP status: ' + (transport.status == "200" ? '200':'<em style="color:red;">'+transport.status+'</em>')+' '+transport.statusText+'<br/>' +
			'Content-Length: '+contentLength+'<br/>' +
			'responseText: '+ (resText ? '<pre>'+"\n\n"+resText+"\n"+'</pre>':'')+'<br/>' +
			'X-JSON: '+ (jsonText ? "<pre>\n\n"+jsonText+"\n</pre>":'') + '<br/>' +
			(obj && obj.debugMsg ? 'debugMsg: '+obj.debugMsg :'') +
			'</div>');
			$A(div.getElementsByTagName('pre')).each(function(el){new ToggleButton(el);});
		}
	});
}

/* check for get values and enable/disable and set cookies */
if( _GET.debug == 'false' || _GET.profile == 'false' ){
	deleteCookie('debug');
}
if( _GET.debug == 'true' || _GET.profile == 'true' ){
	setCookie( 'debug', 'true' );
}
if(getCookie('debug') == 'true'){
	addLoadEvent(enableAjaxDebug);
}


addLoadEvent(function(){Cursor.set('loading');});
Ajax.Responders.register({
	onCreate: function(){
		Cursor.set('loading').show();
		Cursor.timeout = setTimeout(function(){ Ajax.activeRequestCount = 0; Cursor.hide(); },10000); //hide after 10 seconds in case an error occurs
	},
	onComplete: function(){
		if(Ajax.activeRequestCount === 0){ clearTimeout(Cursor.timeout); Cursor.hide(); }
	},
	onException: function(request,ex){
		Cursor.hide();
		if(_debug_enabled || confirm('An error occured processing your request. This is possibly a bug.\n\nClick OK to see the details.')){
			printDebug('<hr/><b>Exception occured during Ajax.Request:</b><br/>'+getRequestDetails(request)+'<br/><br/>'+getExceptionDetails(ex)+'<br/>');
			var debug = getDebugDiv();
			if(debug){ debug.scrollTo(); }
		}
	}
});