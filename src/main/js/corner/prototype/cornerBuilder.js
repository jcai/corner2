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


function Map(){
	this.elements=new Array();
	this.size=function(){
		return this.elements.length;
	}
	this.put=function(_key,_value){
		this.elements.push({key:_key,value:_value});
	}
	
	this.remove=function(_key){
		var bln=false;
		try{
			for (i=0;i<this.elements.length;i++){
				if (this.elements[i].key==_key){
					this.elements.splice(i,1);
					return true;
				}
			}
		}catch(e){
			bln=false;
		}
		return bln;
	}
	
	this.containsKey=function(_key){
		var bln=false;
		try{
			for (i=0;i<this.elements.length;i++){
				if (this.elements[i].key==_key){
					bln=true;
				}
			}
		}catch(e){
			bln=false;
		}
		return bln;
	}
	
	this.get=function(_key){
		try{
			for (i=0;i<this.elements.length;i++){
				if (this.elements[i].key==_key){
					return this.elements[i];
				}
			}
		}catch(e){
			return null;
		}
	}
}