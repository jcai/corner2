// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-01
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证身份证号码是否正确。
 * <p>
 * 代码参考：http://blogger.org.cn/blog/more.asp?name=lhwork&id=19148
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class CardIDChecker {
	private static final String verify18PatternStr = "(\\d{6})" + "(\\d{8})"
			+ "(\\d{3})" + "([\\d[xX]]{1})";

	private static final Pattern verify18Pattern = Pattern
			.compile(verify18PatternStr);

	private static final String verify15PatternStr = "(\\d{6})" + "(\\d{6})"
			+ "(\\d{3})";

	private static final Pattern verify15Pattern = Pattern
			.compile(verify15PatternStr);

	/**
	 * 从第18到第1位的权
	 */
	private static final int verify18Rights[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6,
			3, 7, 9, 10, 5, 8, 4, 2, 1 };

	/**
	 * 检验码校对表
	 */
	private static final String verify18CheckDigit[] = { "1", "0", "X", "9",
			"8", "7", "6", "5", "4", "3", "2" };

	/**
	 * 地区码
	 */
	private String areaNum;

	/**
	 * 出生时间
	 */
	private String birthdayNum;

	/**
	 * 顺序号
	 */
	private String seqNum;

	/**
	 * 校验码
	 */
	private String check_digit;

	private int length;

	

	public boolean isMan() {
		int num = Integer.parseInt(seqNum);
		if (num % 2 == 1) {
			return true;
		} else
			return false;
	}

	/**
	 * 得到生日的日期，格式：yyyy-mm-dd
	 * 
	 * @return String
	 */
	public String getBirthday() {
		String FullBirthdayNum = null;
		if (length == 15)// 在生日号码前加“19”
			FullBirthdayNum = "19" + birthdayNum;
		else
			FullBirthdayNum = birthdayNum;

		String year = FullBirthdayNum.substring(0, 4);
		String month = FullBirthdayNum.substring(4, 6);
		String date = FullBirthdayNum.substring(6, 8);
		return year + "-" + month + "-" + date;
	}

	private CardIDChecker(){
		
	}

	boolean validateCard(String cardNo){
		if (cardNo == null)
			return false;
		length = cardNo.length();
		switch (length) {
		case 15:
			return Verifier15(cardNo);

		case 18:
			return Verifier18(cardNo);
		default:
			return false;
		}
	}
	/**
	 * 验证身份证号码是否正确.
	 * @param string 
	 * @return 是否正确.
	 */
	public static boolean validate(String cardNo) {
		CardIDChecker checker=new CardIDChecker();
		return checker.validateCard(cardNo);
	}

	private boolean Verifier15(String IDCardNO)  {

		Matcher m = verify15Pattern.matcher(IDCardNO);
		if (!m.matches()) {
			return false;
		}
		areaNum = m.group(1);
		birthdayNum = m.group(2);
		seqNum = m.group(3);
		return true;
	}

	private boolean Verifier18(String IDCardNO) 
			 {

		Matcher m = verify18Pattern.matcher(IDCardNO);
		if (!m.matches()) {
			return false;
		}
		areaNum = m.group(1);
		birthdayNum = m.group(2);
		seqNum = m.group(3);
		check_digit = m.group(4);

		// 预期的校验位:
		String expect_check_digit = getCheck_digit18(IDCardNO);

		// 如果校验位无效
		if (expect_check_digit.equalsIgnoreCase(check_digit) == false)
			return false;
		return true;
	}

	/**
	 * 从18位/17位身份证号算出校验位
	 * 
	 * @param IDCardNO
	 *            String
	 * @return String
	 */
	public static String getCheck_digit18(String IDCardNO) {
		// 权总值
		int sum = 0;
		for (int i = 0; i <= 16; i++) {
			int num = Integer.parseInt(IDCardNO.substring(i, i + 1));
			int right = verify18Rights[i];
			sum = sum + num * right;
		}
		// 对权总值取模
		int y = sum % 11;
		return verify18CheckDigit[y];
	}

	public static String IDCardNO15To18(String IDCardNO15)
			{

		Matcher m = verify15Pattern.matcher(IDCardNO15);
		if (m.matches() == false) {
			throw new NumberFormatException("错误的身份证号码!");
		}

		String NO17 = IDCardNO15.substring(0, 6) + "19"
				+ IDCardNO15.substring(6, 15);
		return NO17 + getCheck_digit18(NO17);
	}

	public String getAreaNum() {
		return areaNum;
	}

	public String getBirthdayNum() {
		return birthdayNum;
	}

	/**
	 * 返回校验位，只有18位身份证可用，15位时返回null
	 * 
	 * @return String
	 */
	public String getCheck_digitNum18() {
		return check_digit;
	}

	public int getLength() {
		return length;
	}

	public String getSeqNum() {
		return seqNum;
	}

}
