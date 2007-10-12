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
public class MoneyUnilTest extends Assert {
	
	/**
	 * 测试数字变成大写
	 */
	@Test
	public void testChangeToBig(){
		
		assertEquals("柒佰肆拾叁亿贰仟玖佰肆拾柒万贰仟叁佰肆拾元贰角叁分", MoneyUnil.changeToBig(74329472340.23));
		assertEquals("柒佰肆拾叁亿贰仟玖佰肆拾柒万贰仟叁佰肆拾元贰角叁分", MoneyUnil.changeToBig("74329472340.23"));
		assertEquals("柒万贰仟叁佰肆拾元贰角整", MoneyUnil.changeToBig("072340.20"));
		assertEquals("贰角叁分", MoneyUnil.changeToBig("0.23"));
		assertEquals("柒拾万零肆拾元零叁分", MoneyUnil.changeToBig("700040.03"));
		assertEquals("柒佰肆拾元整", MoneyUnil.changeToBig("740.00"));
		assertEquals("壹亿零壹仟元整", MoneyUnil.changeToBig("100001000.00"));
	}
	
	/**
	 * 测试数字变成英文大写
	 */
	@Test
	public void testChangeToEnglishBig(){
		
		assertEquals("ONE ONLY", MoneyUnil.parse("01"));
		assertEquals("ELEVEN ONLY", MoneyUnil.parse("11"));
		assertEquals("NINETY ONLY", MoneyUnil.parse("90"));
		assertEquals("NINETY NINE ONLY", MoneyUnil.parse("99"));
		assertEquals("ONE HUNDRED ONLY", MoneyUnil.parse("100"));
		assertEquals("ONE THOUSAND ONLY", MoneyUnil.parse("1000"));
		assertEquals("TEN THOUSAND ONLY", MoneyUnil.parse("10000"));
		assertEquals("ONE HUNDRED THOUSAND ONLY", MoneyUnil.parse("100000"));
		assertEquals("ONE MILLION ONLY", MoneyUnil.parse("1000000"));
		assertEquals("ONE HUNDRED AND TWENTY ONE ONLY", MoneyUnil.parse("121"));
		assertEquals("ONE THOUSAND ONE ONLY", MoneyUnil.parse("1001"));
		assertEquals("ONE HUNDRED AND TWENTY THREE AND CENTS FORTY FIVE ONLY", MoneyUnil.parse("123.456"));
	}
}
