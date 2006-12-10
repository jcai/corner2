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

package corner.orm.tapestry.service.captcha;

import org.testng.annotations.Test;

/**
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public class RandomUtilTest {
	@Test
	public void test_createEncodeStr(){
		RandomUtil.encodeStr("hello");
	}
}
