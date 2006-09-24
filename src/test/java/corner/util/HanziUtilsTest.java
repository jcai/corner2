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

import junit.framework.TestCase;

import org.testng.annotations.Test;
@Test
public class HanziUtilsTest extends TestCase {
	public void testLoadHanzi() throws IOException{
		String s=HanziUtils.getPinyin("asdf");
		assertEquals("asdf",s);
		s=HanziUtils.getPinyin("中华人民共和国");
		assertEquals("zhrmghg",s);
		
		
	}
}
