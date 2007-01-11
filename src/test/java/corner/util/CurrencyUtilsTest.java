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

		Assert.assertEquals(1034.7, CurrencyUtils.round((double) 1034.735, 1));

	}
	@Test
	public void testPlus(){
		double v1=2.1;
		double v2=2.2;
		Assert.assertEquals(CurrencyUtils.plus(v1, v2),4.3);
	}
	@Test
	public void testMinus(){
		double v1=2.0;
		double v2=2.4;
		Assert.assertEquals(CurrencyUtils.minus(v1, v2),-0.4);
	}
	@Test
	public void testDiv(){
		double v1=2.4;
		double v2=2.0;
		Assert.assertEquals(CurrencyUtils.div(v1, v2),1.2);
		Assert.assertEquals(CurrencyUtils.div(v1, v2,0),(double)1);
		
		double qutnum= 12000.0;
		double totalsum= 83454.55;
		Assert.assertEquals(CurrencyUtils.div(totalsum, qutnum,6),6.954546);
	}
	@Test
	public void testMult(){
		double v1 = 10.0;
		double v2 = 1.0835;
		Assert.assertEquals(CurrencyUtils.mult(v1, v2),10.835);
	}
}
