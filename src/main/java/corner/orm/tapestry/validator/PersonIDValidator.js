// Copyright 2004, 2005 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

Tapestry.personid_field = function(event,fieldId){
	var field = this.find(fieldId);
	var value = field.value;
	
	if (value == "") return;
	
	//身份证号码校验,并从中拆分出出生年月日和性别
	var yyyy;
	var mm;
	var dd;
	var birthday;
	var sex;

	//校验身份证号码
	function CheckValue(value){
		var id=value;
		var id_length=id.length;

		if (id_length==0){
			event.invalid_field(field,"请输入身份证号码!");
			return false;
		}

		if (id_length!=15 && id_length!=18){
			event.invalid_field(field,"身份证号长度应为15位或18位！");
			return false;
		}

		if (id_length==15){
			yyyy="19"+id.substring(6,8);
			mm=id.substring(8,10);
			dd=id.substring(10,12);

			if (mm>12 || mm<=0){
				event.invalid_field(field,"输入身份证号,月份非法！");
				return false;
			}

			if (dd>31 || dd<=0){
				event.invalid_field(field,"输入身份证号,日期非法！");
				return false;
			}

			birthday=yyyy+ "-" +mm+ "-" +dd;

			if ("13579".indexOf(id.substring(14,15))!=-1){
				sex="1";
			}else{
				sex="2";
			}
		}else if (id_length==18){
			if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17){
				event.invalid_field(field,"身份证中\"X\"输入位置不正确！");
				return false;
			}

			yyyy=id.substring(6,10);
			if (yyyy>2200 || yyyy<1900){
				event.invalid_field(field,"输入身份证号,年度非法！");
				return false;
			}

			mm=id.substring(10,12);
			if (mm>12 || mm<=0){
				event.invalid_field(field,"输入身份证号,月份非法！");
				return false;
			}

			dd=id.substring(12,14);
			if (dd>31 || dd<=0){
				event.invalid_field(field,"输入身份证号,日期非法！");
				return false;
			}

			if (id.charAt(17)=="x" || id.charAt(17)=="X")
			{
				if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id)){
					event.invalid_field(field,"身份证校验错误，请检查最后一位！");
					return false;
				}

			}else{
				if (id.charAt(17)!=GetVerifyBit(id)){
					event.invalid_field(field,"身份证校验错误，请检查最后一位！");
					return false;
				}
			}

			birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
			if ("13579".indexOf(id.substring(16,17)) > -1){
				sex="1";
			}else{
				sex="2";
			}
		}

		return true;
	}
	//15位转18位中,计算校验位即最后一位
	function GetVerifyBit(id){
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
}

Tapestry.validate_max_number = function(event, fieldId, max, message)
{
	var field = this.find(fieldId);
	var value = field.value;
	
	if (value == "") return;
	
    if (Number(value) > max)
      event.invalid_field(field, message)
}
