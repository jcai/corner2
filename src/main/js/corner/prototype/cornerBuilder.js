/*
 *	基础类
 */
CornerBase = {};
CornerBase.prototype = {
};

/*
 *	构造类
 */
CornerBuilder = {
	/* Function: create
	Returns a constructor for a new class that is specific to the structure passed.
	This new class is an extension of <WindowDialogBase>

	structure - The structure that defines node types and their options and hooks.
	*/
	create: function(structure){
		var newTreeClass = Class.create();
		Object.extend(newTreeClass.prototype,Object.extend(structure.extend,structure));
		newTreeClass.prototype.constructor = newTreeClass;
		return newTreeClass;
	},
	showError: function(message){
		alert(message);
	}
};