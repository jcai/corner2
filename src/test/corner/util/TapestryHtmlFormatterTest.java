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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TapestryHtmlFormatterTest extends TestCase {

	/*
	 * Test method for 'corner.util.TapestryHtmlFormatter.format(InputStream)'
	 */
	public void testFormat() throws IOException {
		String src="<input name=\"test\"/>";
		String dest="<input name=\"test\" jwcid=\"testField\"/>";
		byte [] content=src.getBytes();
		ByteArrayInputStream in=new ByteArrayInputStream(content);
		StringBuffer sb=TapestryHtmlFormatter.format(in);
		assertEquals(dest,sb.toString());
		
	}
	/*
	 * Test method for 'corner.util.TapestryHtmlFormatter.format(InputStream)'
	 */
	public void testCorrectFormat() throws IOException {
		String src="<input name=\"test\" jwcid=\"asdfField\"/>";
		String dest="<input name=\"test\" jwcid=\"testField\"/>";
		byte [] content=src.getBytes();
		ByteArrayInputStream in=new ByteArrayInputStream(content);
		StringBuffer sb=TapestryHtmlFormatter.format(in);
		assertEquals(dest,sb.toString());
	}

}
