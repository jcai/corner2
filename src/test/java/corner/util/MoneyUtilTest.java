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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 金额转换工具类测试
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class MoneyUtilTest extends Assert {
	
	/**
	 * 测试数字变成大写
	 */
	@Test
	public void testChangeToBig(){
		
		assertEquals("柒佰肆拾叁亿贰仟玖佰肆拾柒万贰仟叁佰肆拾元贰角叁分", MoneyUtil.changeToBig(74329472340.23));
		assertEquals("柒佰肆拾叁亿贰仟玖佰肆拾柒万贰仟叁佰肆拾元贰角叁分", MoneyUtil.changeToBig("74329472340.23"));
		assertEquals("柒万贰仟叁佰肆拾元贰角整", MoneyUtil.changeToBig("072340.20"));
		assertEquals("贰角叁分", MoneyUtil.changeToBig("0.23"));
		assertEquals("柒拾万零肆拾元零叁分", MoneyUtil.changeToBig("700040.03"));
		assertEquals("柒佰肆拾元整", MoneyUtil.changeToBig("740.00"));
		assertEquals("壹亿零壹仟元整", MoneyUtil.changeToBig("100001000.00"));
	}
	
	/**
	 * 测试数字变成英文大写
	 */
	@Test
	public void testChangeToEnglishBig(){
		
		assertEquals("ONE ONLY", MoneyUtil.parse("01"));
		assertEquals("ELEVEN ONLY", MoneyUtil.parse("11"));
		assertEquals("NINETY ONLY", MoneyUtil.parse("90"));
		assertEquals("NINETY NINE ONLY", MoneyUtil.parse("99"));
		assertEquals("ONE HUNDRED ONLY", MoneyUtil.parse("100"));
		assertEquals("ONE THOUSAND ONLY", MoneyUtil.parse("1000"));
		assertEquals("TEN THOUSAND ONLY", MoneyUtil.parse("10000"));
		assertEquals("ONE HUNDRED THOUSAND ONLY", MoneyUtil.parse("100000"));
		assertEquals("ONE MILLION ONLY", MoneyUtil.parse("1000000"));
		assertEquals("ONE HUNDRED AND TWENTY ONE ONLY", MoneyUtil.parse("121"));
		assertEquals("ONE THOUSAND AND ONE ONLY", MoneyUtil.parse("1001"));
		assertEquals("ONE THOUSAND AND TEN ONLY", MoneyUtil.parse("1010"));
		assertEquals("ONE HUNDRED AND TWENTY THREE AND CENTS FORTY FIVE ONLY", MoneyUtil.parse("123.456"));
	}
}
