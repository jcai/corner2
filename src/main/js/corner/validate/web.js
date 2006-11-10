/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/
dojo.provide("corner.validate.web");

dojo.require("dojo.validate.common");

corner.validate.isInRange = function(/*String*/value, /*Object?*/flags){
// summary:
//  validate number
// value a string
// flags an object
 rFlags={};
	if(flags.maxField!=null&&dojo.byId(flags.maxField)){
		maxValue=dojo.byId(flags.maxField).value;
		if(maxValue!=null){
			if(dojo.validate.isRealNumber(maxValue)){
				rFlags.max=eval(maxValue);		
			}
		}
		
		
	}
	if(flags.minField!=null&&dojo.byId(flags.minField)){
		minValue=dojo.byId(flags.minField).value;
		if(minValue!=null){
			if(dojo.validate.isRealNumber(minValue)){
				rFlags.min=eval(minValue);		
			}
		}
	}
	rFlags.decimal=flags.decimal;
	return dojo.validate.isInRange(value,rFlags);
}
/**
 * 验证身份证号码是否正确
 */
corner.validate.isPersonID = function(/*String*/value, /*Object?*/flags){
// summary:
//	验证公民身份证号码是否正确。
//
// value  A string.
// flags  An object. 	

	if (value == "") return;
	
	//身份证号码校验,并从中拆分出出生年月日和性别
	var yyyy;
	var mm;
	var dd;
	var birthday;
	var sex;
	//15位转18位中,计算校验位即最后一位
	var GetVerifyBit=function GetVerifyBit(id){
		var result;
		var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
		nNum=nNum%11;
		switch (nNum) {
		   case 0 :
			  result="1";
			  break;
		   case 1 :
			  result="0";
			  break;
		   case 2 :
			  result="X";
			  break;
		   case 3 :
			  result="9";
			  break;
		   case 4 :
			  result="8";
			  break;
		   case 5 :
			  result="7";
			  break;
		   case 6 :
			  result="6";
			  break;
		   case 7 :
			  result="5";
			  break;
		   case 8 :
			  result="4";
			  break;
		   case 9 :
			  result="3";
			  break;
		   case 10 :
			  result="2";
			  break;
		}
		//document.write(result);
		return result;
	}

	//校验身份证号码
	
	var id=value;
	var id_length=id.length;

	if (id_length==0){
		//event.invalid_field(field,"请输入身份证号码!");
		return false;// Boolean
	}

	if (id_length!=15 && id_length!=18){
		//event.invalid_field(field,"身份证号长度应为15位或18位！");
		return false;// Boolean
	}

	if (id_length==15){
		yyyy="19"+id.substring(6,8);
		mm=id.substring(8,10);
		dd=id.substring(10,12);

		if (mm>12 || mm<=0){
			//event.invalid_field(field,"输入身份证号,月份非法！");
			return false;// Boolean
		}

		if (dd>31 || dd<=0){
			//event.invalid_field(field,"输入身份证号,日期非法！");
			return false;// Boolean
		}

		birthday=yyyy+ "-" +mm+ "-" +dd;

		if ("13579".indexOf(id.substring(14,15))!=-1){
			sex="1";
		}else{
			sex="2";
		}
	}else if (id_length==18){
		if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17){
			//event.invalid_field(field,"身份证中\"X\"输入位置不正确！");
			return false;// Boolean
		}

		yyyy=id.substring(6,10);
		if (yyyy>2200 || yyyy<1900){
			//event.invalid_field(field,"输入身份证号,年度非法！");
			return false;// Boolean
		}

		mm=id.substring(10,12);
		if (mm>12 || mm<=0){
			//event.invalid_field(field,"输入身份证号,月份非法！");
			return false;// Boolean
		}

		dd=id.substring(12,14);
		if (dd>31 || dd<=0){
			//event.invalid_field(field,"输入身份证号,日期非法！");
			return false;// Boolean
		}

		if (id.charAt(17)=="x" || id.charAt(17)=="X")
		{
			if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id)){
				//event.invalid_field(field,"身份证校验错误，请检查最后一位！");
				return false;// Boolean
			}

		}else{
			if (id.charAt(17)!=GetVerifyBit(id)){
				//event.invalid_field(field,"身份证校验错误，请检查最后一位！");
				return false;// Boolean
			}
		}

		birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
		if ("13579".indexOf(id.substring(16,17)) > -1){
			sex="1";
		}else{
			sex="2";
		}
	}

	return true;// Boolean
	
}

