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
import corner.util.BeanUtils;

/**
 * svn测试
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitServiceTest extends Assert{
	
//	@Test
	public void testgetPropertys(){
		SvnKitService service = constructSvnKitService();
		
		A entity = new A();
		
		List<String> s =  BeanUtils.getPropertyMethodNames(entity);
		
		for(String name : s){
			System.out.println(name);
		}
	}
	
	@Test
	public void testEntityRevision(){
		SvnKitService service = constructSvnKitService();
		
		A entity = new A();
		entity.setId("282828c115fa49d60115fa4ab96d0001");
		
		String s = service.getEntityRevision(entity, -1);
	}
	
	/**
	 * 增和改的测试
	 */
	@Test
	public void testSaveOrUpdateSvn(){
		SvnKitService service = constructSvnKitService();
		
		A entity = new A();
		entity.setId("test01");
		entity.setName("中国");
		
		service.saveOrUpdateSvn(entity,"增加的", BeanUtils.getPropertyMethodNames(entity));
	}
	
	private SvnKitService constructSvnKitService(){
		SvnKitService service=new SvnKitService();
		service.setUrl("http://dev.bjmaxinfo.com/svn/svn-test/");
		service.setUsername("xf");
		service.setPassword("123456");
		return service;
	}
}
