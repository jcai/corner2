//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.io.IOException;



import org.testng.Assert;
import org.testng.annotations.Test;
@Test
public class HanziUtilsTest extends Assert {
	public void testLoadHanzi() throws IOException{
		String s=HanziUtils.getPinyin("asdf");
		assertEquals("asdf",s);
		s=HanziUtils.getPinyin("中华人民共和国");
		assertEquals("zhrmghg",s);
	}
	public void test_hanzi(){
		assertTrue(HanziUtils.isHanzi('我'));
		assertFalse(HanziUtils.isHanzi('a'));
		
	}
	public void test_changsha(){
		assertEquals("z",HanziUtils.getPinyin("长"));
	}
	public void test_whitespace(){
		assertEquals("za asdf",HanziUtils.getPinyin("长a asdf"));
		assertEquals("za nh",HanziUtils.getPinyin("长a 你好"));
		assertEquals("za 　nh",HanziUtils.getPinyin("长a 　你好"));
	}
}
