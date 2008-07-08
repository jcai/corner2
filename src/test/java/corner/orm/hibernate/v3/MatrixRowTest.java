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

package corner.orm.hibernate.v3;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class MatrixRowTest extends Assert{

	@Test
	public void testSumValue(){
		MatrixRow<Double> m=new MatrixRow<Double>();
		assertEquals(m.getDoubleWithAnyway(2,2.1),2.1);
	}
	
	@Test
	public void testGetInt(){
		MatrixRow<String> m = new MatrixRow<String>();
		MatrixRow<String> m1 = new MatrixRow<String>();
		m.add("1");
		assertEquals(m.getInt(0),1);
		assertEquals(m1.getInt(0), 0);
	}
	
	@Test
	public void testGetString(){
		MatrixRow<String> m = new MatrixRow<String>();
		MatrixRow<String> m1 = new MatrixRow<String>();
		m.add("1");
		assertEquals(m.getString(0),"1");
		assertEquals(m1.getString(0), "0");
	}
}
