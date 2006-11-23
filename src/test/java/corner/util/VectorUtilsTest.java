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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.testng.annotations.Test;

@Test
public class VectorUtilsTest extends TestCase {

	private static final String[] COMMAEND_ARR = { ",a,b,", ",", ",,,", "a,b," };

	private static final String[] TEST_STR = { ",按时地方,b,c,d,", ",", "a,", ",,," };

	private static final String[] COMMAS_STR = { ",", ",,,,," };

	public void testNullVector() {
			assertEquals((double)0,VectorUtils.sum(null));
		
	}

	public void testSumVector() {
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		assertEquals(3.0, VectorUtils.sum(v));

		v.add("1.2");
		assertEquals(4.2, VectorUtils.sum(v));

		v.add("1.258");
		assertEquals(5.458, VectorUtils.sum(v));

	}

	public void testSumErrorVector() {
		Vector<String> v = new Vector<String>();
		v.add("1s");
		v.add("2");

		try {
			VectorUtils.sum(v);
			fail("can't reacheable");
		} catch (RuntimeException e) {
			// go here
		}

	}

	public void testSubmVectorList() {
		Vector<String> v1 = new Vector<String>();
		v1.add("1");
		v1.add("2");

		Vector<String> v2 = new Vector<String>();
		v2.add("1");
		v2.add("2");

		List<Vector<String>> list = new ArrayList<Vector<String>>();
		list.add(v1);
		list.add(v2);
		Vector<Double> r = VectorUtils.sumList(list);
		assertTrue(2 == r.get(0));
	}

	public void testREGEX() {
		Pattern pattern = Pattern.compile(VectorUtils.COMMAEND_STR);
		for (String str : TEST_STR) {
			Matcher match = pattern.matcher(str);
			assertEquals(true, match.find());
		}
	}

	public void testREGEXI() {
		Pattern pattern = Pattern.compile(VectorUtils.COMMAS_STR);
		for (String str : COMMAS_STR) {
			Matcher match = pattern.matcher(str);
			assertEquals(true, match.find());
		}
	}

	public void testVectorSplit() {
		List<Object> strList = VectorUtils.VectorSplit(COMMAEND_ARR[0], ",");
		assertEquals(4, strList.size());
		assertEquals(strList.get(0).toString(), "");
		assertEquals(strList.get(1).toString(), "a");
		assertEquals(strList.get(2).toString(), "b");
		assertEquals(strList.get(3).toString(), "");

		List<Object> strList1 = VectorUtils.VectorSplit(COMMAEND_ARR[1], ",");
		assertEquals(2, strList1.size());
		assertEquals(strList1.get(0).toString(), "");
		assertEquals(strList1.get(1).toString(), "");

		List<Object> strList2 = VectorUtils.VectorSplit(COMMAEND_ARR[2], ",");
		assertEquals(4, strList2.size());
		assertEquals(strList2.get(0).toString(), "");
		assertEquals(strList2.get(1).toString(), "");

		List<Object> strList3 = VectorUtils.VectorSplit(COMMAEND_ARR[3], ",");
		assertEquals(3, strList3.size());
		assertEquals(strList3.get(0).toString(), "a");
		assertEquals(strList3.get(1).toString(), "b");
		assertEquals(strList3.get(2).toString(), "");

	}

}
