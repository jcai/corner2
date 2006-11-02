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

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponentTestCase;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class MatrixHeadTest extends BaseComponentTestCase{
	@Test
	public void testRender(){
		
	}
	
	/**
	 * 测试字符
	 *
	 */
	@Test
	public void testMatrixRowwithString(){
		MatrixRow<String> matrix = new MatrixRow<String>("a","b","c","d","e");
		assertEquals(matrix.getRowSum(),0.0);
		matrix = new MatrixRow<String>("1","2","3","4","e");
		assertEquals(matrix.getRowSum(),0.0);
	}
	
	@Test
	public void testMatrixRowwithNumber(){
		MatrixRow<Double> matrix = new MatrixRow<Double>(1.0,2.0,3.0,4.0,5.0);
		assertEquals(matrix.getRowSum(),15.0);
		MatrixRow<Integer> matrix1 = new MatrixRow<Integer>(1,2,3,4,5);
		assertEquals(matrix1.getRowSum(),15.0);		
		MatrixRow<Long> matrix2 = new MatrixRow<Long>((long)1,(long)2,(long)3,(long)4,(long)5);
		assertEquals(matrix2.getRowSum(),15.0);		
	}

}
