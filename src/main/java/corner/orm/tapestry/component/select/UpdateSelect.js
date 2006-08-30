function UpdateForm(_formName,_fields){
	var	_form=document.getElementById(_formName);
	for(var i=0;i<_fields.length;i=i+2){
		eval(_formName+"."+_fields[i]+".value='"+_fields[i+1]+"'");
	}
}