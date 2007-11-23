/* Cache class, a simple add/remove cache system */
/*  supports Prototype's enumerable functions */
var Cache = Class.create();
Cache.prototype = {
	initialize: function(){
		this.hash = {};
		this.length = 0;
	},
	_each: function(iterator) {
		for (var key in this.hash){ iterator(this.hash[key]); }
  },
  clear: function() {
    this.hash = null;
    this.initialize();
    return this;
  },
	insert: function(value){
		var key = this.length++;
		this.hash[key] = value;
		return key;
	},
	remove: function(key){
		var value = this.hash[key];
		delete this.hash[key];
		return value;
	}
};
Object.extend(Cache.prototype, Enumerable);


/* Event rewrite */
var B = Prototype.Browser;
// applies the calling method to an array of elements
function applyToList(args) {
  if (args[0].constructor != Array) { return false; }
  var method = args.callee;
  args = $A(args);
  args.shift().each(function(el){
    method.apply(this, [el].concat(args));
  });
  return true; // the calling method can end
}

var Observer = Class.create();
Observer.guid = 0;

Observer.prototype = {
  initialize: function(element, type, observer, useCapture) {
    this.element = $(element);
    this.type = (type == 'keypress' && (B.IE || B.WebKit) ? 'keydown' : type);
    this.observer = observer;
    this.useCapture = useCapture || false;
  },
  add: function() {
    this._add();
    if (!this.element._observers) this.element._observers = {};
    var local = this.element._observers[this.type];
    if (!local) local = this.element._observers[this.type] = {};
    if (!this.observer.$$guid) this.observer.$$guid = Observer.guid++;
    local[this.observer.$$guid] = this;
  },
  remove: function() {
    this._remove();
    delete this.element._observers[this.type][this.observer.$$guid];
  }
};

if (document.addEventListener) {
  Object.extend(Observer.prototype, {
    _add: function() {
      // TODO: create wrapper for Safari?
      this.element.addEventListener(this.type, this.observer, this.useCapture);
    },
    _remove: function() {
      this.element.removeEventListener(this.type, this.observer, this.useCapture);
    }
  });
} else if (B.IE) {
  Object.extend(Observer.prototype, {
    _add: function() {
/*
      // create a wrapper for scope correction and event object normalization
      var ob = this.observer, el = this.element;
      this.wrapper = function(e) {
        return ob.call(el, Observer.extendEvent(e))
      };
      this.element.attachEvent('on' + this.type, this.wrapper);
*/
			this.element.attachEvent('on' + this.type, this.observer);
    },
    _remove: function() {
/*
      if (!this.wrapper) { try{
      	this.wrapper = this.element._observers[this.type][this.observer.$$guid].wrapper;
      }catch(e){ return; } }
      this.element.detachEvent('on' + this.type, this.wrapper);
*/
			this.element.detachEvent('on' + this.type, this.observer);
    }
  });

/*  Object.extend(Observer, {
    // adds standard DOM event model properties to IE's events
    extendEvent: function(event) {
      event || (event = window.event);
      return Object.extend(Object.extend(event, {
          target: event.srcElement,
          which:  event.button,
          pageX:  Event.pointerX(event),
          pageY:  Event.pointerY(event),
          relatedTarget:   Event.relatedTarget(event)
        }),
        this.eventMethods
      );
    },
    eventMethods: {
      stopPropagation: function() { this.cancelBubble = true },
      preventDefault:  function() { this.returnValue = false },
      inspect: function() { return '[object Event]' }
    }
  });
*/
}

var EventCache = Class.create();
EventCache.globalCache = new Cache();
EventCache.unload = function(){
  EventCache.globalCache.invoke('clear');
};

EventCache.prototype = {
  initialize: function(){
    this.cache = new Cache();
    this.globalIndex = EventCache.globalCache.insert(this);
  },

  clear: function(){
    this.cache.invoke('remove').clear();
  },
  
  dispose: function(){
  	EventCache.globalCache.remove(this.globalIndex).clear();
  },
  
  observe: function(element, type, observer, useCapture) {
    if (applyToList.call(this,arguments)){ return; }
    
    var ob = new Observer(element, type, observer, useCapture);
    ob.add();
    ob.cacheKey = this.cache.insert(ob);
    return ob;
  },
  
  remove: function(observer){
  	this.cache.remove(observer.cacheKey).remove();
  },
  
  stopObserving: function(element, type, observer, useCapture) {
    if (applyToList.call(this,arguments)){ return; }
    
    var ob = new Observer(element, type, observer, useCapture);
    if (ob.observer) {
	    try{
	    	this.remove(ob.element._observers[ob.type][ob.observer.$$guid]);
	    }catch(e){}
		}else if (ob.element && ob.element._observers){
      var collection = ob.element._observers;
      if (ob.type) {
        if (!(collection = collection[ob.type])){ return; }
        for (var i in collection) { this.remove(collection[i]); }
      }else{
        for (var i in collection) { for (var j in collection[i]) {
        	this.remove(collection[i][j]);
        }}
      }
    }
  }
};
if(B.IE){ window.attachEvent('onunload', EventCache.unload); }
if (!window.Event) {
  var Event = new EventCache();
}else{
  Object.extend(Event, EventCache.prototype);
  Event.initialize();
}


/* Hash.toQueryString rewrite, supports recursion and is *correct* */
/*
Hash.toQueryString = function(obj){
  var pairs = $H(obj).findAll(function(pair){ return pair.value !== undefined; });
  return pairs.collect(function(pair,index){
    return Hash._toQueryString(encodeURIComponent(pair.key), pair.value);
  }).findAll(function(arg){ return !!arg; }).join('&');
};
Hash._toQueryString = function(key,value){
  if(value === null) value = '';
  if(typeof value != 'object'){
    return key+'='+encodeURIComponent(value.toString());
  }
  if(value.constructor == Array){
    var items = value.findAll(function(arg){ return arg !== undefined; });
    if(!items.length) return Hash._toQueryString(key, '');
    var result = items.collect(function(val,index){
      return Hash._toQueryString(key+'['+index+']',val);
    });
  }else{
    var pairs = $H(value).findAll(function(pair){ return pair.value !== undefined; });
    if(!pairs.length) return Hash._toQueryString(key, '');
    var result = pairs.collect(function(pair,index){
      return Hash._toQueryString(key+'['+encodeURIComponent(pair.key)+']',pair.value);
    });
  }
  return result.join('&');
};
*/
Hash.toQueryString.addPair = function(key, value, prefix) {
  if (value === undefined) return;
  key = encodeURIComponent(key);
  this.push(key + '=' + (value == null ? '' : encodeURIComponent(value)));
};
String.prototype.toQueryParams = function(separator) {
    var match = this.strip().match(/([^?#]*)(#.*)?$/);
    if (!match) return {};

    return match[1].split(separator || '&').inject({}, function(hash, pair) {
      if ((pair = pair.split('='))[0]) {
        var name = decodeURIComponent(pair[0]);
        var value = pair[1] ? decodeURIComponent(pair[1]) : '';

        if (hash[name] !== undefined) {
          if (hash[name].constructor != Array)
            hash[name] = [hash[name]];
          if (value) hash[name].push(value);
        }
        else hash[name] = value;
      }
      return hash;
    });
  }

/* Easy access to current "get" variables */
var _GET = window.location.search.toQueryParams();

/* Form extensions */
Object.extend(Form,{
	getSelects: function(form, name){
		form = $(form);
		var inputs = form.getElementsByTagName('select');
		if(!name){ return inputs; }
		var matchingInputs = [];
		for(var i = 0; i < inputs.length; i++){
			var input = inputs[i];
			if(name && input.name != name){ continue; }
			matchingInputs.push(input);
		}
		return matchingInputs;
	}
});