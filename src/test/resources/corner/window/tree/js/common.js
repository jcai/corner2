Effect.DefaultOptions.duration = 0.5;

var Bnode = Builder.node;
Builder.node = function(){ return Element.extend(Bnode.apply(Builder,arguments)); };

/* input: jtask-936_qtask-20
 * output: Array( 'jtask' => '936', 'qtask' => '20' ) */
function parseid(id){
	var hash = {};
	var tokens = id.split('_');
	tokens.each(function(el){
		var pair = el.split('-');
		hash[pair[0]] = pair[1];
	});
	return hash;
}

_UID_count = 0;
function UID(prefix,suffix){
	return (prefix || '_uid_')+(_UID_count++)+(suffix || '_');
}

function makeLabel(element){
	if(!element.id){ element.id = UID(); }
	var args = $A(arguments);
	args[0] = 'label';
	var label = Builder.node.apply(Builder,args);
	label.htmlFor = element.id;
	return label;
}

/* replace with Element.up? */
Object.extend(Element,{
	findElement: function(me, tagName){
		var element = $(me);
		while(element.parentNode &&
			(!element.tagName || (element.tagName.toUpperCase() != tagName.toUpperCase()))
		){ element = element.parentNode; }
		return $(element);
	},
	clear: function(element){
		element.innerHTML = '';
	}
});

/* window 'load' attachment */
function addLoadEvent(func) {
	var oldonload = window.onload;
	if(typeof window.onload != 'function'){
		window.onload = func;
	}else{
		window.onload = function(){
			oldonload();
			func();
		}
	}
}

/* wtf mate? */
/*
Object.extend(Array, {
  each: function(arr,iterator) {
    for (var i = 0, length = arr.length; i < length; i++)
      iterator(arr[i]);
  }
});
*/


function selectedOption(select){
	return select.options[select.selectedIndex];
}
function selectValue(myid,sel){
	var box = $(myid);
	for( var i=0; i<box.options.length; i++ ){
		if( box.options[i].value == sel ){
			box.options.selectedIndex = i;
			return;
		}
	}
}
function selectText(myid,sel){
	var box = $(myid);
	for( var i=0; i<box.options.length; i++ ){
		if( box.options[i].text == sel ){
			box.options.selectedIndex = i;
			return;
		}
	}
}

function fillSelectFromObject(receiver,obj){
	var box = $(receiver);
	box.options.length = 0;
	var sel = obj.selected || false;
	var opts = obj.options;
	for( var i=0; i<opts.length; i++ ){
		box.options[i] = new Option(opts[i].text,opts[i].value,null,(sel&&sel==opts[i].value?true:false));
	}
	if( obj.selectedIndex ){ box.selectedIndex = obj.selectedIndex; }
}
function addSelectFromObject( selectName, minIndex, newOptions, sel ){
	return addSelectToElement( $(selectName), selectName, minIndex, newOptions, sel );
}
function addSelectToElement( container, selectName, minIndex, newOptions, sel ){
	if(typeof (container.index) == 'undefined' ){ container.index = 0; }
	container.index++;
	var newSelect = Builder.node('select',{name:selectName+'[]'});
	fillSelectFromObject(newSelect,newOptions);
	if(sel) selectValue(newSelect,sel);
	var newSpan = Builder.node('div',{style:'clear:both;'},[newSelect]);
	if(container.index > minIndex ){
		var remLink = Builder.node('a',[Builder.node('img',{alt:'',src:'../images/icons/delete.png',title:'Remove'})]);
		Event.observe(remLink,'click',function(){ container.removeChild(newSpan)});
		newSpan.appendChild(remLink);
	}
	container.appendChild(newSpan);
	newSelect.focus();
	return newSelect;
}

//requires that style has: 'height: ??px'
function growTextArea(element){
	var ht = element.style.height.match(/^(\d+)/)[1] * 1;
	element.style.height = (ht + 48) + 'px'; 
	element.focus();
}

/* override the Prototype function, this one adds a 'tag' argument */
document.getElementsByClassName = function(className, parentElement, tagName) {
	var elements = [];
	parentElement = $(parentElement) || document;
	if(Prototype.BrowserFeatures.XPath){
		var xpathResult = document.evaluate(".//"+(tagName||'*')+"[contains(concat(' ', @class, ' '), ' " + className + " ')]",
			parentElement, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
		for (var i = 0, length = xpathResult.snapshotLength; i < length; i++) {
			elements.push(Element.extend(xpathResult.snapshotItem(i)));
		}
	}else{
		var regex = new RegExp("(^|\\s)" + className + "(\\s|$)");
    var children = parentElement.getElementsByTagName(tagName || '*');
    for (var i = 0, length = children.length; i < length; i++) {
    	var elClass = children[i].className;
			if (elClass && (elClass == className || elClass.match(regex))){
				elements.push(Element.extend(children[i]));
			}
		}
	}
	return elements;
};

function getWindowSize() {
  return {
  	width: document.documentElement.clientWidth || document.body.clientWidth,
  	height: document.documentElement.clientHeight || document.body.clientHeight
  };
}
function getScrollOffset() {
  return {
  	left: window.scrollX || document.documentElement.scrollLeft,
  	top: window.scrollY || document.documentElement.scrollTop
  };
}

Element.getOverhang = function(element,padding){
	element = $(element);
	padding = Object.extend({x:0,y:0},padding || {});
 	var dim = Element.getDimensions(element);
 	var win = getWindowSize();
 	var scroll = getScrollOffset();
 	var top = element.offsetTop;
 	var y = top - scroll.top - padding.y;
 	if(y > 0) y = top + dim.height + padding.y - win.height - scroll.top;
 	if(y < 0) y = 0; //TODO
 	var left = element.offsetLeft;
 	var x = left - scroll.left - padding.x;
 	if(x > 0) x = left + dim.width + padding.x - win.width - scroll.left;
 	if(x < 0) x = 0; //TODO
 	return {x: x, y: y}
}

Effect.Reposition = function(element,options){
	element = $(element);
	options = Object.extend({
		beforeSetup: function(effect){
			var overhang = Element.getOverhang(effect.element,effect.options);
			Object.extend(effect.options,{
				position: 'relative',
				x: -overhang.x,
				y: -overhang.y
			});
		}
	},options || {});
 	return new Effect.Move(element,options);
}

var Selectors = {
	odd: function(el,i){ return i % 2 == 1; },
	even: function(el,i){ return i % 2 == 0; }
};

//$$('table.striped').each(function(el){ el.getElementsBySelector('tr').select(Selectors.odd).invoke('addClassName','odd'); });
//$$('table.striped').each(function(el){ el.getElementsBySelector('tr.red.odd').invoke('removeClassName','red').invoke('addClassName','red-odd'); });

/* get, set, and delete cookies */
function getCookie( name ) {
	var start = document.cookie.indexOf( name + "=" );
	var len = start + name.length + 1;
	if(((!start) && (name != document.cookie.substring(0,name.length))) || start == -1 ){ return null; }
	var end = document.cookie.indexOf(";",len);
	if(end == -1){ end = document.cookie.length; }
	return unescape(document.cookie.substring(len,end));
}

function setCookie( name, value, expires, path, domain, secure ) {
	var today = new Date();
	today.setTime( today.getTime() );
	if(expires){ expires = expires * 1000 * 60 * 60 * 24; }
	var expires_date = new Date(today.getTime()+(expires));
	document.cookie = name+"="+escape( value ) +
		( ( expires ) ? ";expires="+expires_date.toGMTString() : "" ) +
		( ( path ) ? ";path=" + path : "" ) +
		( ( domain ) ? ";domain=" + domain : "" ) +
		( ( secure ) ? ";secure" : "" );
}
	
function deleteCookie( name, path, domain ) {
	if(getCookie(name)){
		document.cookie = name +"="+((path)?";path="+path:"")+
			((domain)?";domain="+domain:"")+";expires=Thu, 01-Jan-1970 00:00:01 GMT";
	}
}


/* Toggle - allows an n-way toggle button
Arguments:
	element: the element to be toggled, or an array of [element,target]
	state: the beginning state, can be omitted to use defaultState option, or to discover state
Global options:
	loadingClassName: 'loading' - The className used while executing changeStateHandler functions
	changeStateHandler: function() - The function called when changing states. Call this.changeStateComplete()
		or return true inside this function to complete the state change or call this.changeStateFailed() or return
		false to cancel the state change. This function will be called only if a changeStateHandler isn't specified
		in the current state's options. This function is called with bind, so properties such as "this.currentState"
		are accessible.
	defaultState: Use this if you'd like a default state assigned to elements that don't have one of the proper classNames
	getTarget: specify a function that returns the element that should be observed for clicks
State options:
	className: '<state>' - The className associated with the state. Defaults to the state name.
	nextState: (required) - The next state to enter by default.
	changeStateHandler: function() - This will override and behave the same as the changeStateHandler defined in
		the global options (see above).
	onEnterState: function(arguments) - This function will be called when this state successfully becomes the current state,
		and will be called with the same arguments as passed to either changeStateComplete or forceState without the first argument.
Properties: (accessible via this.<property> inside the changeStateHandler and onEnterState functions
	element: the element which has the relevant className for the current state
	target: the element observed for clicks (default: element, otherwise returned by getTarget or 2nd element of first argument)
	classNames: array of the classNames of the states you specified (extended by $A())
	stateKeys: array of the names of the states you specified (extended by $A())
	states: hash of the states you specified (extended by $H())

Usage example:
MyToggle = Toggle.create({
	states: {
		unavailable: {
			nextState: 'available',
			onEnterState: function(){
				alert('Status reset to unavailable');
			}
		},
		available: { nextState: 'automatic' },
		automatic: { nextState: 'unavailable' }
	},
	changeStateHandler: function(){
		new Ajax.Request(CS.adminPage,{
			params: 'action=toggle&state='+this.states[this.currentState].nextState+'&id='+this.element.id,
			onComplete: function(xhr,json){
				if(json && json.error){
					this.changeStateFailed();
					alert(json.error);
				}else{
					this.changeStateComplete();
				}
			}.bind(this)
		});
	},
	defaultState: 'unavailable'
});
new MyToggle(element,'available');
*/
var Toggle = {
	create: function(states){
		var newToggleClass = Class.create();
		Object.extend(newToggleClass.prototype,Toggle.Base.prototype);
		$H(states.states).keys().each(function(state){
			if(!states.states[state].className){ states.states[state].className = state; }
			if(!states.defaultState){ states.defaultState = state; }
		});
		if(!states.loadingClassName){ states.loadingClassName = 'loading'; }
		Object.extend(newToggleClass.prototype, states);
		newToggleClass.prototype.states = $H(newToggleClass.prototype.states);
		newToggleClass.prototype.classNames = newToggleClass.prototype.states.values().pluck('className');
		newToggleClass.prototype.stateKeys = newToggleClass.prototype.states.keys();
		return newToggleClass;
	}
};

Toggle.Base = Class.create();
Toggle.Base.prototype = {
	initialize: function(element,state){
		if(element.constructor == Array){
			this.element = $(element[0]);
			this.target = $(element[1]);
		}else{
			this.element = $(element);
			this.target = this.getTarget(this.element);
		}
		if(state){
			this.currentState = state;
			Element.addClassName(this.element,this.states[state].className);
		}
		this.changeStateListener = this.changeState.bindAsEventListener(this);
		Event.observe(this.target,'click',this.changeStateListener);
	},
	getTarget: Prototype.K,
	changeState: function(){
		if(!this.currentState){ /* bypass this step on initialize to make page loads faster */
			var stateClass = this.element.classNames().find(function(className){
				return this.classNames.include(className);
			}.bind(this));
			if(stateClass){
				this.currentState = this.stateKeys.find(function(state){
					return this.states[state].className == stateClass;
				}.bind(this));
			}else{
				this.currentState = this.defaultState;
			}
		}
		Element.removeClassName(this.element,this.states[this.currentState].className);
		var handler = this.states[this.currentState].changeStateHandler || this.changeStateHandler || false;
		if(handler){
			if(this.loading){ return; }
			this.loading = true;
			Element.addClassName(this.element,this.loadingClassName);
			var result = handler.apply(this,arguments);
			if(result === true){ this.changeStateComplete(); }
			else if(result === false){ this.changeStateFailed(); }
		}else{
			this.changeStateComplete();
		}
	},
	changeStateComplete: function(){
		var newState = this.states[this.currentState].nextState;
		if(!newState){ this.changeStateFailed(); return; }
		if(this.states[this.currentState].changeStateHandler || this.changeStateHandler){
			Element.removeClassName(this.element,this.loadingClassName);
			this.loading = false;
		}
		Element.addClassName(this.element,this.states[newState].className);
		this.previousState = this.currentState;
		this.currentState = newState;
		if(this.states[this.currentState].onEnterState){ this.states[this.currentState].onEnterState.apply(this,arguments); }
	},
	changeStateFailed: function(){
		if(this.changeStateHandler){
			Element.removeClassName(this.element,this.loadingClassName);
			this.loading = false;
		}
		Element.addClassName(this.element,this.states[this.currentState].className);
	},
	forceState: function(newState){
		if(!this.states[newState] || newState == this.currentState){ this.changeStateFailed(); return; }
		if(this.loading){
			Element.removeClassName(this.element,this.loadingClassName);
			this.loading = false;
		}else{
			Element.removeClassName(this.element,this.states[this.currentState].className);
		}
		Element.addClassName(this.element,this.states[newState].className);
		this.previousState = this.currentState;
		this.currentState = newState;
		var args = $A(arguments); args.shift();
		if(this.states[this.currentState].onEnterState){ this.states[this.currentState].onEnterState.apply(this,args); }
	},
	rollback: function(){
		if(!this.previousState){ return; }
		if(this.loading){
			Element.removeClassName(this.element,this.loadingClassName);
			this.loading = false;
		}else{
			Element.removeClassName(this.element,this.states[this.currentState].className);
		}
		Element.addClassName(this.element,this.states[this.previousState].className);
		this.currentState = this.previousState;
		this.previousState = false;
	},
	dispose: function(){
		Event.stopObserving(this.target,'click',this.changeStateListener);
	}
};


/* Usage:
new ToggleButton(element,{
	showHTML/hideHTML: when using default, parent, or sibling, the button created will use this as innerHTML
	visible: start the element visible?
	toggle: the event handler called when toggle button clicked
	onShow: hook called after all default actions have taken place. args: event, this: ToggleButton instance
	onHide: hook called after all default actions have taken place. args: event, this: ToggleButton instance
	parent: button element will be appended to this element
	sibling: button element will be inserted before this element (default: sibling is element)
	handle: this element will be the button (showHTML/hideHTML not used)
	effect: 'blind','slide','appear' if desired
	effectOptions: options to be passed to Effect.toggle
});
*/
ToggleButton = Class.create();
ToggleButton.prototype = {
	initialize: function(element, options){
		this.element = $(element);
		this.options = Object.extend({
			showHTML: '<b>(show)</b>',
			hideHTML: '<b>(hide)</b>', 
			visible: false,
			toggle: this.toggle,
			onShow: null,
			onHide: null
		}, options || {});
		if(this.options.parent){ this.options.parent = $(this.options.parent); }
		if(this.options.handle){ this.options.handle = $(this.options.handle); }
		if(this.options.sibling){ this.options.sibling = $(this.options.sibling); }
		if(!this.options.parent && !this.options.handle && !this.options.sibling){ this.options.sibling = this.element; }
		this.createButton();
		if(this.options.visible || this.element.hasClassName('active')){
			Element.show(this.element);
			if(!this.options.handle){ this.span.innerHTML = this.options.hideHTML; }
		}else{
			Element.hide(this.element);
			if(!this.options.handle){ this.span.innerHTML = this.options.showHTML; }
		}
		this.onclickListener = this.options.toggle.bindAsEventListener(this);
		Event.observe(this.button, 'click', this.onclickListener);
	},
	createButton: function(){
		if(this.options.handle){
			this.button = this.options.handle;
			return;
		}
		this.span = Builder.node('span');
		this.button = Builder.node('a',[this.span]);
		if(this.options.parent){ this.options.parent.appendChild(this.button); }
		else if(this.options.sibling){ this.options.sibling.parentNode.insertBefore(this.button,this.options.sibling); }
	},
	toggle: function(event){
		if(Element.visible(this.element)){ this.hide(event); }
		else{ this.show(event); }
	},
	show: function(event){
		if(this.options.effect && !Element.visible(this.element)){
			new Effect.toggle(this.element,this.options.effect,this.options.effectOptions || {});
		}else{ Element.show(this.element); }
		if(!this.options.handle){ this.span.innerHTML = this.options.hideHTML; }
		if(this.options.onShow){ this.options.onShow(event).bind(this); }
	},
	hide: function(event){
		if(this.options.effect && Element.visible(this.element)){
			new Effect.toggle(this.element,this.options.effect,this.options.effectOptions || {});
		}else{ Element.hide(this.element); }
		if(!this.options.handle){ this.span.innerHTML = this.options.showHTML; }
		if(this.options.onHide){ this.options.onHide(event).bind(this); }
	},
	dispose: function() {
		Event.stopObserving(this.button, 'click', this.onclickListener);
		if(!this.options.handle) this.element.parentNode.removeChild(this.button);
	}
};

function time(){
	var now = new Date();
	return (((now.getHours()*3600) + (now.getMinutes()*60) + now.getSeconds())*1000) + now.getMilliseconds();
}

function getDoubleDatePicker(start,finish,format){
	var ifFormat = format || '%a, %b %d, %Y';
	var startInput = Builder.node('input',{type:'text',name:'task_start_date'});
	var startDiv = Builder.node('div');
	var finishInput = Builder.node('input',{type:'text',name:'task_finish_date'});
	var finishDiv = Builder.node('div');
	var datePicker = Builder.node('div',{className:'datePicker'},[Builder.node('span',[
			'Select a start date:', Builder.node('br'), startInput, startDiv
		]),Builder.node('span',[
			'Select a finish date:', Builder.node('br'), finishInput, finishDiv
		]), Builder.node('br')
	]);
	//var datePicker = Builder.node('div',{className:'datePicker'},[startInput, startDiv, finishInput, finishDiv]);
	var startCal = Calendar.setup({
		flat         : startDiv,
		flatCallback : function(cal){
			startInput.value = cal.date.print(ifFormat);
			if(cal.date.getTime() > cal.finishCal.date.getTime()){    
				cal.finishCal.setDate(new Date(cal.date));
				finishInput.value = cal.date.print(ifFormat);	
			}
		},
		ifFormat     : ifFormat
	});
	startCal.finishCal = Calendar.setup({
		flat         : finishDiv,
		flatCallback : function(cal){
			if(cal.date.getTime() < startCal.date.getTime()){
				alert('Please select a date after '+startCal.date.print(ifFormat));
				cal.setDate(new Date(startCal.date));
			}
			finishInput.value = cal.date.print(ifFormat);	
		},
		ifFormat     : ifFormat
	});
	if(start && start != '---'){
		startCal.setDate(Date.parseDate(start,ifFormat));
		startInput.value = start;
	}
	if(finish && finish != '---'){
		startCal.finishCal.setDate(Date.parseDate(finish,ifFormat));
		finishInput.value = finish;
	}
	return {
		element: datePicker,
		dispose: function(){
			//startCal.dispose();
			//startCal.finishCal.dispose();
		}
	};
}


/* DATE ADDITIONS */
Date._MD = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
Date.SECOND = 1000 /* milliseconds */;
Date.MINUTE = 60 * Date.SECOND;
Date.HOUR   = 60 * Date.MINUTE;
Date.DAY    = 24 * Date.HOUR;
Date.WEEK   =  7 * Date.DAY;

Date.parseDate = function(str, fmt) {
	var today = new Date();
	var y = 0, m = -1, d = 0, a = str.split(/\W+/);
	var b = fmt.match(/%./g), i = 0, j = 0, hr = 0, min = 0;
	for(i = 0; i < a.length; ++i){
	if (!a[i]) continue;
		switch (b[i]) {
			case "%d":
			case "%e":
				d = parseInt(a[i], 10);
			break;
			case "%m":
				m = parseInt(a[i], 10) - 1;
			break;
			case "%Y":
			case "%y":
				y = parseInt(a[i], 10);
				(y < 100) && (y += (y > 29) ? 1900 : 2000);
			break;
			case "%b":
			case "%B":
				for(j = 0; j < 12; ++j){
					if(Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()){ m = j; break; }
				}
			break;
			case "%H":
			case "%I":
			case "%k":
			case "%l":
				hr = parseInt(a[i], 10);
			break;
			case "%P":
			case "%p":
				if(/pm/i.test(a[i]) && hr < 12){ hr += 12; }
				else if(/am/i.test(a[i]) && hr >= 12){ hr -= 12; }
			break;
			case "%M":
				min = parseInt(a[i], 10);
			break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	if (isNaN(d)) d = today.getDate();
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();
	if (y != 0 && m != -1 && d != 0) return new Date(y, m, d, hr, min, 0);
	y = 0; m = -1; d = 0;
	for (i = 0; i < a.length; ++i) {
		if(a[i].search(/[a-zA-Z]+/) != -1){
			var t = -1;
			for(j = 0; j < 12; ++j){
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
			}
			if(t != -1){ if (m != -1){ d = m+1; } m = t; }
		}else if(parseInt(a[i], 10) <= 12 && m == -1){
			m = a[i]-1;
		}else if(parseInt(a[i], 10) > 31 && y == 0){
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
		}else if (d == 0){
			d = a[i];
		}
	}
	if(y == 0) y = today.getFullYear();
	if(m != -1 && d != 0) return new Date(y, m, d, hr, min, 0);
	return today;
};

Date.prototype = Object.extend(Date.prototype,{
	/** Returns the number of days in the current month */
	getMonthDays : function(month) {
		var year = this.getFullYear();
		if(typeof month == "undefined"){ month = this.getMonth(); }
		if(((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1){ return 29; }
		else{ return Date._MD[month]; }
	},
	/** Returns the number of day in the year. */
	getDayOfYear : function() {
		var now = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
		var then = new Date(this.getFullYear(), 0, 0, 0, 0, 0);
		var time = now - then;
		return Math.floor(time / Date.DAY);
	},
	/** Returns the number of the week in year, as defined in ISO 8601. */
	getWeekNumber : function() {
		var d = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
		var DoW = d.getDay();
		d.setDate(d.getDate() - (DoW + 6) % 7 + 3); // Nearest Thu
		var ms = d.valueOf(); // GMT
		d.setMonth(0);
		d.setDate(4); // Thu in Week 1
		return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
	},
	/** Checks date and time equality */
	equalsTo : function(date) {
		return ((this.getFullYear() == date.getFullYear()) &&
			(this.getMonth() == date.getMonth()) &&
			(this.getDate() == date.getDate()) &&
			(this.getHours() == date.getHours()) &&
			(this.getMinutes() == date.getMinutes()));
	},
	/** Set only the year, month, date parts (keep existing time) */
	setDateOnly : function(date) {
		var tmp = new Date(date);
		this.setDate(1);
		this.setFullYear(tmp.getFullYear());
		this.setMonth(tmp.getMonth());
		this.setDate(tmp.getDate());
	},
	/** Prints the date in a string according to the given format. */
	print : function (str) {
		var m = this.getMonth(), d = this.getDate(), y = this.getFullYear();
		var wn = this.getWeekNumber(), w = this.getDay(), s = {}, hr = this.getHours();
		var pm = (hr >= 12), ir = ((pm) ? (hr - 12) : hr), dy = this.getDayOfYear();
		if(ir == 0) ir = 12;
		var min = this.getMinutes(), sec = this.getSeconds();
		s["%a"] = Calendar._SDN[w]; // abbreviated weekday name [FIXME: I18N]
		s["%A"] = Calendar._DN[w]; // full weekday name
		s["%b"] = Calendar._SMN[m]; // abbreviated month name [FIXME: I18N]
		s["%B"] = Calendar._MN[m]; // full month name
		s["%c"] = 1+m; // FIXME: %c : preferred date and time representation for the current locale
		s["%C"] = 1 + Math.floor(y / 100); // the century number
		s["%d"] = (d < 10) ? ("0" + d) : d; // the day of the month (range 01 to 31)
		s["%e"] = d; // the day of the month (range 1 to 31)
		// FIXME: %D : american date style: %m/%d/%y
		// FIXME: %E, %F, %G, %g, %h (man strftime)
		s["%H"] = (hr < 10) ? ("0" + hr) : hr; // hour, range 00 to 23 (24h format)
		s["%I"] = (ir < 10) ? ("0" + ir) : ir; // hour, range 01 to 12 (12h format)
		s["%j"] = (dy < 100) ? ((dy < 10) ? ("00" + dy) : ("0" + dy)) : dy; // day of the year (range 001 to 366)
		s["%k"] = hr;		// hour, range 0 to 23 (24h format)
		s["%l"] = ir;		// hour, range 1 to 12 (12h format)
		s["%m"] = (m < 9) ? ("0" + (1+m)) : (1+m); // month, range 01 to 12
		s["%M"] = (min < 10) ? ("0" + min) : min; // minute, range 00 to 59
		s["%n"] = "\n";		// a newline character
		s["%p"] = pm ? "PM" : "AM";
		s["%P"] = pm ? "pm" : "am";
		// FIXME: %r : the time in am/pm notation %I:%M:%S %p
		// FIXME: %R : the time in 24-hour notation %H:%M
		s["%s"] = Math.floor(this.getTime() / 1000);
		s["%S"] = (sec < 10) ? ("0" + sec) : sec; // seconds, range 00 to 59
		s["%t"] = "\t";		// a tab character
		// FIXME: %T : the time in 24-hour notation (%H:%M:%S)
		s["%U"] = s["%W"] = s["%V"] = (wn < 10) ? ("0" + wn) : wn;
		s["%u"] = w + 1;	// the day of the week (range 1 to 7, 1 = MON)
		s["%w"] = w;		// the day of the week (range 0 to 6, 0 = SUN)
		// FIXME: %x : preferred date representation for the current locale without the time
		// FIXME: %X : preferred time representation for the current locale without the date
		s["%y"] = ('' + y).substr(2, 2); // year without the century (range 00 to 99)
		s["%Y"] = y;		// year with the century
		s["%%"] = "%";		// a literal '%' character
		var re = /%./g;
		if (!Calendar.is_ie5 && !Calendar.is_khtml)
			return str.replace(re, function (par) { return s[par] || par; });
		var a = str.match(re);
		for (var i = 0; i < a.length; i++) {
			var tmp = s[a[i]];
			if (tmp) {
				re = new RegExp(a[i], 'g');
				str = str.replace(re, tmp);
			}
		}
		return str;
	}
});
Date.prototype.__msh_oldSetFullYear = Date.prototype.setFullYear;
Date.prototype.setFullYear = function(y) {
	var d = new Date(this);
	d.__msh_oldSetFullYear(y);
	if (d.getMonth() != this.getMonth())
		this.setDate(28);
	this.__msh_oldSetFullYear(y);
};