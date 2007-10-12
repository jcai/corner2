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
}
