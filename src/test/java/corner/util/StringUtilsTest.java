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
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class StringUtilsTest extends Assert{
	/**
	 * 将字符串line中的子串oldString全部替换为newString
	 */
	@Test
	public void testChangeToBig(){
		String testStr = "{\"id\":\"generated\",\"className\":\"alphacube\",\"title\":\"google\",\"url\":\"http://168.168.168.65/widget/QueryDialogTest,winDialog.direct\",\"top \":\"top:0\",\"right\":\"left:0\",\"width\":\"500\",\"height\":\"300\",\"maxWidth\":\"none\",\"maxHeight\":\"none\",\"minWidth \":\"500\",\"minHeight\":\"300\",\"resizable\":\"true\",\"closable\":\"true\",\"minimizable\":\"true\",\"maximizable\":\"true\",\"draggable\":\"true\",\"showEffectOptions\":\"{duration:1.5}}\",\"hideEffectOptions\":\"none\",\"effectOptions\":\"none\",\"opacity\":1,\"recenterAuto\":\"true\",\"gridX\":1,\"gridY\":1,\"wiredDrag\":\"false\",\"destroyOnClose\":\"false\",\"all callbacks\":\"none\",\"onload\":\"pwin_winDialogFun\"}";
		
		String newSring = StringUtils.replace(testStr, "\"pwin_winDialogFun\"", "pwin_winDialogFun");
		
		assertEquals("{\"id\":\"generated\",\"className\":\"alphacube\",\"title\":\"google\",\"url\":\"http://168.168.168.65/widget/QueryDialogTest,winDialog.direct\",\"top \":\"top:0\",\"right\":\"left:0\",\"width\":\"500\",\"height\":\"300\",\"maxWidth\":\"none\",\"maxHeight\":\"none\",\"minWidth \":\"500\",\"minHeight\":\"300\",\"resizable\":\"true\",\"closable\":\"true\",\"minimizable\":\"true\",\"maximizable\":\"true\",\"draggable\":\"true\",\"showEffectOptions\":\"{duration:1.5}}\",\"hideEffectOptions\":\"none\",\"effectOptions\":\"none\",\"opacity\":1,\"recenterAuto\":\"true\",\"gridX\":1,\"gridY\":1,\"wiredDrag\":\"false\",\"destroyOnClose\":\"false\",\"all callbacks\":\"none\",\"onload\":pwin_winDialogFun}", newSring);
	}
	
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
