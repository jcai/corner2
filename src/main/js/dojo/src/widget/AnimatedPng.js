

dojo.provide("dojo.widget.AnimatedPng");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");


















dojo.widget.defineWidget(
"dojo.widget.AnimatedPng",
dojo.widget.HtmlWidget,
{
isContainer: false,

// Integer
//	width (of each frame) in pixels
width: 0,

// Integer
//	height (of each frame) in pixels
height: 0,

// String
//	pathname to png file containing frames to be animated (ie, displayed sequentially)
aniSrc: '',

// Integer
//	time to display each frame
interval: 100,

_blankSrc: dojo.uri.dojoUri("src/widget/templates/images/blank.gif"),

templateString: '<img class="dojoAnimatedPng" />',

postCreate: function(){
this.cellWidth = this.width;
this.cellHeight = this.height;

var img = new Image();
var self = this;

img.onload = function(){ self._initAni(img.width, img.height); };
img.src = this.aniSrc;
},

_initAni: function(w, h){
this.domNode.src = this._blankSrc;
this.domNode.width = this.cellWidth;
this.domNode.height = this.cellHeight;
this.domNode.style.backgroundImage = 'url('+this.aniSrc+')';
this.domNode.style.backgroundRepeat = 'no-repeat';

this.aniCols = Math.floor(w/this.cellWidth);
this.aniRows = Math.floor(h/this.cellHeight);
this.aniCells = this.aniCols * this.aniRows;
this.aniFrame = 0;

window.setInterval(dojo.lang.hitch(this, '_tick'), this.interval);
},

_tick: function(){
this.aniFrame++;
if (this.aniFrame == this.aniCells) this.aniFrame = 0;

var col = this.aniFrame % this.aniCols;
var row = Math.floor(this.aniFrame / this.aniCols);

var bx = -1 * col * this.cellWidth;
var by = -1 * row * this.cellHeight;

this.domNode.style.backgroundPosition = bx+'px '+by+'px';
}
}
);
