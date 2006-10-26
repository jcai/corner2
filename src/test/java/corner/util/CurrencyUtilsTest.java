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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */

public class CurrencyUtilsTest {
	@Test
	public void testConvert() {
		Assert.assertEquals(123456.12, CurrencyUtils.round(
				(double) 123456.123456, 2));
		Assert.assertEquals(123456.1235, CurrencyUtils.round(
				(double) 123456.123456, 4));
		Assert.assertEquals(-123456.1235, CurrencyUtils.round(
				(double) -123456.123456, 4));
		Assert.assertEquals(-123456.11113, CurrencyUtils.round(
				(double) -123456.111126, 5));
		Assert.assertEquals(1034.75, CurrencyUtils.round((double) 1034.745, 2));
		Assert.assertEquals(1034.74, CurrencyUtils.round((double) 1034.735, 2));

	}
	@Test
	public void testPlus(){
		double v1=2.1;
		double v2=2.2;
		Assert.assertEquals(CurrencyUtils.plus(v1, v2),4.3);
	}
}
