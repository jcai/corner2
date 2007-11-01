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

package corner.service.svn;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * svn测试
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitServiceTest extends Assert{
	@Test
	public void testSaveOrUpdateSvn(){
		SvnKitService service = constructSvnKitService();
		
		A entity = new A();
		
		String propertys = "id,cnName";
		
		List ls = Arrays.asList(propertys.split(","));
		
		service.saveOrUpdateSvn(entity, ls);
	}
	
	private SvnKitService constructSvnKitService(){
		SvnKitService service=new SvnKitService();
		service.setUrl("http://dev.bjmaxinfo.com/svn/svn-test/");
		service.setUsername("xf");
		service.setPassword("123456");
		return service;
	}
}
