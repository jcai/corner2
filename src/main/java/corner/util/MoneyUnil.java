//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

/**
 * 数字转换为英文书写的类
 * @author lsq
 * @version $Revision$
 * @since 2.5
 */
public class MoneyUnil {

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
	    	  if(a[i].substring(1, 2).equals("0")){
	    		  lm = transThree(a[i]) + " AND " + parseMore(String.valueOf(i)) + " " + lm; // 加: thousand、million、billion
	    	  }else{
	       lm = transThree(a[i]) + " " + parseMore(String.valueOf(i)) + " " + lm; // 加: thousand、million、billion
	    	  }
	      }
	      else
	       lm = transThree(a[i]); // 防止i=0时， 在多加两个空格.
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
	
}
