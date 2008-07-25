//==============================================================================
// file :       $Id: StringUtilsTest.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.util.StringUtils;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3677 $
 * @since 2.5
 */
public class StringUtilsTest extends Assert {

	@Test
	public void isNumber(){
		Double d1 = new Double(2.034);
		Long l1 = new Long(1);
		Integer i1 = new Integer(21);
		Float f1 = new Float(23.43);
		
		Object[] objs = new Object[]{null,d1,l1,i1,f1};
		Object[] objs1 = new Object[]{d1,l1,i1,f1};
		assertFalse(StringUtils.isNumber(objs));
		assertTrue(StringUtils.isNumber(objs1));
	}
}
