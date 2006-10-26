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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.sun.org.apache.bcel.internal.generic.GOTO;



/**
 * 转换货币的统一方法.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class CurrencyUtils {
	/**
	 * 转换为货币。
	 * 
	 * @param currency 待转换的数字。
	 * @param p 小数点后保留的位数.
	 * @return 转换后的数字.
	 */
	public  static double round(double currency,int p){
		return Double.parseDouble(constructFormatter(p).format(currency));
	}
	/**
	 * 浮点类型的四舍五入.
	 * @param currency 待转换的数字。
	 * @param p 保留的位数。
	 * @return 转换后的结果.
	 */
	public  static float round(float currency,int p){
		return Float.parseFloat(constructFormatter(p).format(currency));
	}
	private static NumberFormat constructFormatter(int p){
		String zeroStr=productZero(p); //产生后缀的0字符串
		StringBuffer sb=new StringBuffer();
		sb.append("##0");
		sb.append(p>0?".":"");
		sb.append(zeroStr);
		sb.append(";");
		sb.append("-##0");
		sb.append(p>0?".":"");
		sb.append(zeroStr);
		NumberFormat format=new DecimalFormat(sb.toString());
		return format;
	}
	private static String productZero(int p){
		StringBuffer sb=new StringBuffer();
		while(p-->0)
			sb.append("0");
		return sb.toString();
	}
	
}
