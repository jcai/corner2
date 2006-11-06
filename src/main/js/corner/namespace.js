dojo.provide("corner.namespace");

dojo.require("dojo.ns");

(function(){
	
	var map = {
		html: {
			"Selector": "corner.widget.Selector",
			"TextAreaBox":"corner.widget.TextAreaBox"
			"QueryBox":"corner.widget.QueryBox"
		}
	};
	
	function resolveNamespace(name, domain){
		if(!domain){ domain="html"; }
		if(!map[domain]){ return null; }
		return map[domain][name];
	}
	dojo.registerNamespaceManifest("corner","../corner", "corner", "corner.widget");
	dojo.registerNamespaceResolver("corner", resolveNamespace);
})();
