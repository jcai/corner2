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

import junit.framework.TestCase;

import org.testng.annotations.Test;
@Test

public class VectorUtilsTest extends TestCase {
	public void testSumVector(){
		Vector<String> v=new Vector<String>();
		v.add("1");
		v.add("2");
		assertEquals(3.0,VectorUtils.sum(v));
		
		v.add("1.2");
		assertEquals(4.2,VectorUtils.sum(v));
		
		v.add("1.258");
		assertEquals(5.458,VectorUtils.sum(v));
		
		
		
	}
	public void testSubmVectorList(){
		Vector<String> v1=new Vector<String>();
		v1.add("1");
		v1.add("2");
		
		Vector<String> v2=new Vector<String>();
		v2.add("1");
		v2.add("2");
		
		List<Vector<String>> list=new ArrayList<Vector<String>>();
		list.add(v1);
		list.add(v2);
		Vector<Double> r=VectorUtils.sumList(list);
		assertTrue(2==r.get(0));
	}
}
