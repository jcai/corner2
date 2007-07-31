/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: StringUtils.java 6599 2007-06-14 08:09:34Z ghostbb $
 * created at:2007-04-19
 */
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
}
