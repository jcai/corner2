//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class JSONArrayTest extends Assert{
	@Test
	public void testJSONArray(){
		JSONArray jsonarr = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("label", "广州");
		json.put("value", "111111");
		jsonarr.put(json);
		
		json = new JSONObject();
		json.put("label", "上海");
		json.put("value", "222222");
		jsonarr.put(json);
		
		json = new JSONObject();
		json.put("label", "北京");
		json.put("value", "333333");
		jsonarr.put(json);
		
		json = new JSONObject();
		json.put("label", "武汉");
		json.put("value", "444444");
		jsonarr.put(json);
		
		System.out.println(jsonarr.toString());
	}
	
	@Test
	public void testJSONObject(){
		JSONObject json = new JSONObject();
		json.put("label", "广州");
		System.out.println(json.toString());
	}
}
