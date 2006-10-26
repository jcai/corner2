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

import java.math.BigDecimal;



/**
 * 转换货币的统一方法.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class CurrencyUtils {
	/**默认为四舍五入方法**/
	private static int DEFAULT_ROUND_PATTERN=BigDecimal.ROUND_HALF_UP;
	/**
	 * 转换为货币。
	 * 采用了四舍五入的方法.
	 * @param currency 待转换的数字。
	 * @param p 小数点后保留的位数.
	 * @return 转换后的数字.
	 */
	public  static double round(double currency,int p){
		BigDecimal decimal=BigDecimal.valueOf(currency);
		return decimal.divide(new BigDecimal(1),p,DEFAULT_ROUND_PATTERN).doubleValue();
	}
	/**
	 * 浮点类型的四舍五入.
	 * @param currency 待转换的数字。
	 * @param p 保留的位数。
	 * @return 转换后的结果.
	 */
	public  static float round(float currency,int p){
		BigDecimal decimal=BigDecimal.valueOf(currency);
		return decimal.divide(new BigDecimal(1),p,DEFAULT_ROUND_PATTERN).floatValue();
	}
	}
