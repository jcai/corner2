// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-06
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

package corner.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 保存corner中常用的String工具类
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class StringUtils {

	/**
	 * 把UserDepartmentLink实体中的SecurityCode解析成为一个按照集团，分公司，部门，组，人员的SecurityCodes数组
	 * 
	 * @param s
	 * @return 如果没有找到则返回null
	 */
	public static String[] parseSecurityCode(String s) {
		String[] securityCodes = new String[4];
		securityCodes[0] = s.substring(0, 5);
		securityCodes[1] = s.substring(0, 8);
		securityCodes[2] = s.substring(0, 11);
		securityCodes[3] = s;
		return securityCodes;
	}

	/**
	 * 根据给定的许可字符串和授权类型，判断这个授权是否被许可 permissionStr:VisitorControl表中对应记录被授权的支值
	 * permissionType:等待判断的权限值
	 */
	public static boolean isPermission(int permissionValue, int permissionType) {
		if ((permissionValue & permissionType) > 0
				&& (permissionValue >= permissionType)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据给定的privilegeLsit,计算出最终的权限值
	 * 
	 * @param lsit
	 * @return
	 */
	public static int caculatePrivilege(List<String> privilegeList) {
		int privilege = 0;
		if (privilegeList != null && privilegeList.size() > 0) {
			privilege = Integer.parseInt(privilegeList.get(0));
			for (String i : privilegeList) {
				privilege = privilege | Integer.parseInt(i);
			}
		}
		return privilege;
	}
	
	/**
	 * 根据Unicode判断用户输入的字符
	 * @param inputString
	 * @param searchType
	 * @return
	 */
	public static boolean comparedCharacter(String inputString,String searchType){	
		if(inputString.length()>0){//fix to avoid single character.
			Pattern p = Pattern.compile(searchType);
			Matcher m = p.matcher(inputString.substring(0,1));
			boolean x = m.find();
			return x;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 判断传入的Object[]是否都是Number类型
	 * true:都是Number类型 false:至少有一个,不是Number类型或者是null的元素存在于数组中
	 * @param objs
	 * @return
	 */
	public static boolean isNumber(Object... objs){
		boolean isNumvalue = true;
		if(objs != null && objs.length>0){
			for(Object obj:objs){
				if(!(obj instanceof Number)){
					isNumvalue = false;
					break;
				}
			}
		}
		return isNumvalue;
	}
	
	/**
	 * 将字符串line中的子串oldString全部替换为newString 
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray(); //字符串放入数组
			char[] newString2 = newString.toCharArray(); //要替换的字符串
			int oLength = oldString.length(); //被替换的字符串的长度
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}
}
