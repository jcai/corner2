// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-12
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

/**
 * 数字转换为英文书写，中文书写格式的类
 * 英文书写格式ＡＮＤ的语法规则：在几十几与百位间加上ＡＮＤ
 * @author lsq
 * @version $Revision$
 * @since 2.5
 */
public class MoneyUtil {

	/**
	 * 将数字转换为英文大写格式．
	 * @param x
	 * @return　英文大写格式
	 */
	public static String parse(String x) {
	    int z = x.indexOf("."); // 取小数点位置
	    String lstr = "", rstr = "";
	    if (z > -1) { // 看是否有小数，如果有，则分别取左边和右边
	     lstr = x.substring(0, z);
	     rstr = x.substring(z + 1);
	     } else // 否则就是全部
	     lstr = x;

	    String lstrrev = reverse(lstr); // 对左边的字串取反
	    String[] a = new String[5]; // 定义5个字串变量来存放解析出来的叁位一组的字串

	    switch (lstrrev.length() % 3) {
	     case 1 :
	      lstrrev += "00";
	      break;
	     case 2 :
	      lstrrev += "0";
	      break;
	     }
	    String lm = ""; // 用来存放转换后的整数部分
	    for (int i = 0; i < lstrrev.length() / 3; i++) {
	     a[i] = reverse(lstrrev.substring(3 * i, 3 * i + 3)); // 截取第一个叁位
	     if (!a[i].equals("000")) { // 用来避免这种情况：1000000 = one million thousand only
	      if (i != 0){  
	        
	    	  lm = transThree(a[i]) + " " + parseMore(String.valueOf(i)) + " " + lm; // 加: thousand、million、billion
	      }
	      else if(Integer.parseInt(a[i]) <  100 && lstrrev.length() > 3){
	              //判断个,十,百三位小于100 且要转换的数字大于1000时要加AND 
	    	     // 如:1001 应该为:ONE THOUSAND AND ONE ONLY 要加AND 而不是ONE THOUSAND ONE ONLY
	    	       lm = "AND " + transThree(a[i]); 
	           }else{
	        	   lm = transThree(a[i]); // 防止i=0时， 在多加两个空格.
	           }
	      } else
	      lm += transThree(a[i]);
	     }

	    String xs = ""; // 用来存放转换后小数部分
	    if (z > -1)
	     xs = "AND CENTS " + transTwo(rstr) + " "; // 小数部分存在时转换小数

	    return lm.trim() + " " + xs + "ONLY";
	    }

	private static String parseFirst(String s) {
	    String[] a =
	     new String[] { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE" };
	    return a[Integer.parseInt(s.substring(s.length() - 1))];
	    }

	private static String parseTeen(String s) {
	    String[] a =
	     new String[] {
	      "TEN",
	      "ELEVEN",
	      "TWELEVE",
	      "THIRTEEN",
	      "FOURTEEN",
	      "FIFTEEN",
	      "SIXTEEN",
	      "SEVENTEEN",
	      "EIGHTEEN",
	      "NINETEEN" };
	    return a[Integer.parseInt(s) - 10];
	    }

	private static String parseTen(String s) {
	    String[] a =
	     new String[] {
	      "TEN",
	      "TWENTY",
	      "THIRTY",
	      "FORTY",
	      "FIFTY",
	      "SIXTY",
	      "SEVENTY",
	      "EIGHTY",
	      "NINETY" };
	    return a[Integer.parseInt(s.substring(0, 1)) - 1];
	    }

//	 两位
	private static String transTwo(String s) {
	    String value = "";
	    // 判断位数
	    if (s.length() > 2)
	     s = s.substring(0, 2);
	    else if (s.length() < 2)
	     s = "0" + s;

	    if (s.startsWith("0")) // 07 - seven 是否小於10
	     value = parseFirst(s);
	    else if (s.startsWith("1")) // 17 seventeen 是否在10和20之间
	     value = parseTeen(s);
	    else if (s.endsWith("0")) // 是否在10与100之间的能被10整除的数
	     value = parseTen(s);
	    else
	     value = parseTen(s) + " " + parseFirst(s);
	    return value;
	    }

	private static String parseMore(String s) {
	    String[] a = new String[] { "", "THOUSAND", "MILLION", "BILLION" };
	    return a[Integer.parseInt(s)];
	    }

//	 制作叁位的数
//	 s.length = 3
	private static String transThree(String s) {
	    String value = "";
	    if (s.startsWith("0")) // 是否小於100
	     value = transTwo(s.substring(1));
	    else if (s.substring(1).equals("00")) // 是否被100整除
	     value = parseFirst(s.substring(0, 1)) + " HUNDRED";
	    else
	     value = parseFirst(s.substring(0, 1)) + " HUNDRED AND " + transTwo(s.substring(1));
	    return value;
	    }

	/**
	 * 将数字进行反转
	 * @param s
	 * @return 反转后的数字
	 */
	private static String reverse(String s) {
	    char[] aChr = s.toCharArray();
	    StringBuffer tmp = new StringBuffer();
	    for (int i = aChr.length - 1; i >= 0; i--) {
	     tmp.append(aChr[i]);
	     }
	    return tmp.toString();
	    }
	
	/**
	 * 将数字转换成大写金额 
	 * @param value 需要转换的字符串
	 */
	public static String changeToBig(String value) {
		if (null == value || "".equals(value.trim()))
			return "零";

		String strCheck, strFen, strDW, strNum, strBig, strNow;
		double d = 0;
		try {
			d = Double.parseDouble(value);
		} catch (Exception e) {
			return "数据" + value + "非法！";
		}

		strCheck = value + ".";
		int dot = strCheck.indexOf(".");
		if (dot > 12) {
			return "数据" + value + "过大，无法处理！";
		}

		try {
			int i = 0;
			strBig = "";
			strDW = "";
			strNum = "";
			long intFen = (long) (d * 100);
			strFen = String.valueOf(intFen);
			int lenIntFen = strFen.length();
			while (lenIntFen != 0) {
				i++;
				switch (i) {
				case 1:
					strDW = "分";
					break;
				case 2:
					strDW = "角";
					break;
				case 3:
					strDW = "元";
					break;
				case 4:
					strDW = "拾";
					break;
				case 5:
					strDW = "佰";
					break;
				case 6:
					strDW = "仟";
					break;
				case 7:
					strDW = "万";
					break;
				case 8:
					strDW = "拾";
					break;
				case 9:
					strDW = "佰";
					break;
				case 10:
					strDW = "仟";
					break;
				case 11:
					strDW = "亿";
					break;
				case 12:
					strDW = "拾";
					break;
				case 13:
					strDW = "佰";
					break;
				case 14:
					strDW = "仟";
					break;
				}
				switch (strFen.charAt(lenIntFen - 1)) //选择数字  
				{
				case '1':
					strNum = "壹";
					break;
				case '2':
					strNum = "贰";
					break;
				case '3':
					strNum = "叁";
					break;
				case '4':
					strNum = "肆";
					break;
				case '5':
					strNum = "伍";
					break;
				case '6':
					strNum = "陆";
					break;
				case '7':
					strNum = "柒";
					break;
				case '8':
					strNum = "捌";
					break;
				case '9':
					strNum = "玖";
					break;
				case '0':
					strNum = "零";
					break;
				}
				//处理特殊情况  
				strNow = strBig;
				//分为零时的情况  
				if ((i == 1) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "整";
				//角为零时的情况  
				else if ((i == 2) && (strFen.charAt(lenIntFen - 1) == '0')) { //角分同时为零时的情况  
					if (!strBig.equals("整"))
						strBig = "零" + strBig;
				}
				//元为零的情况  
				else if ((i == 3) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "元" + strBig;
				//拾－仟中一位为零且其前一位（元以上）不为零的情况时补零  
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '元'))
					strBig = "零" + strBig;
				//拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过  
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				//拾－仟中一位为零且其前一位是元且为零的情况时跨过  
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '元')) {
				}
				//当万为零时必须补上万字  
				else if ((i == 7) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "万" + strBig;
				//拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零  
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '万'))
					strBig = "零" + strBig;
				//拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过  
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')) {
				}
				//拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过  
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				//万位为零且存在仟位和十万以上时，在万仟间补零  
				else if ((i < 11) && (i > 8)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')
						&& (strNow.charAt(2) == '仟'))
					strBig = strNum + strDW + "万零"
							+ strBig.substring(1, strBig.length());
				//单独处理亿位  
				else if (i == 11) {
					//亿位为零且万全为零存在仟位时，去掉万补为零  
					if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = "亿" + "零"
								+ strBig.substring(1, strBig.length());
					//亿位为零且万全为零不存在仟位时，去掉万  
					else if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = "亿" + strBig.substring(1, strBig.length());
					//亿位不为零且万全为零存在仟位时，去掉万补为零  
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = strNum + strDW + "零"
								+ strBig.substring(1, strBig.length());
					//亿位不为零且万全为零不存在仟位时，去掉万  
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = strNum + strDW
								+ strBig.substring(1, strBig.length());
					//其他正常情况  
					else
						strBig = strNum + strDW + strBig;
				}
				//拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零  
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '亿'))
					strBig = "零" + strBig;
				//拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过  
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '亿')) {
				}
				//拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过  
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				//亿位为零且不存在仟万位和十亿以上时去掉上次写入的零  
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) != '仟'))
					strBig = strNum + strDW
							+ strBig.substring(1, strBig.length());
				//亿位为零且存在仟万位和十亿以上时，在亿仟万间补零  
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) == '仟'))
					strBig = strNum + strDW + "亿零"
							+ strBig.substring(2, strBig.length());
				else
					strBig = strNum + strDW + strBig;
				strFen = strFen.substring(0, lenIntFen - 1);
				lenIntFen--;
			}
			return strBig;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 将数字转换成大写金额
	 * @param value 数值型的
	 * @return
	 */
	public static String changeToBig(double value) {
		return changeToBig(String.valueOf(value));
	}
}
