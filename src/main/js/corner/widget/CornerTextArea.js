dojo.provide("corner.widget.CornerTextArea");

dojo.require("dojo.widget.*");

dojo.require("dojo.widget.Select");

dojo.widget.tags.addParseTreeHandler("dojo:CornerTextArea");
corner.widget.CornerTextArea = function(){
	 dojo.widget.html.Select.call(this);
}

dojo.inherits(corner.widget.CornerTextArea,  dojo.widget.html.Select);

dojo.lang.extend(corner.widget.CornerTextArea,{
		widgetType: "CornerTextArea",
		templatePath: dojo.uri.dojoUri("../corner/widget/templates/CornerTextArea.html"),
		startSearchFromInput: function(){
			dojo.debug(this.textInputNode.value);
			//this.startSearch(this.textInputNode.value);
		},
	
});
