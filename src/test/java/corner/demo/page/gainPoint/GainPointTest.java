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

package corner.demo.page.gainPoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class GainPointTest  extends Assert{
	
	@Test
	public void testJsonObject(){
		
		List l = new ArrayList();
		
		l.add("111");
		l.add("222");
		
		List ls = new ArrayList();
		
		ls.add("333");
		ls.add("444");
		
		JSONObject json = new JSONObject();
		
		JSONObject jsont = new JSONObject();
		
		json.put("inName", l);
		json.put("cnName", ls);
		
		System.out.println(json.toString());
	}
}
