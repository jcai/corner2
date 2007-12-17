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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.testng.annotations.Test;

import corner.test.AbstractTestCase;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class HibernateObjectTest extends AbstractTestCase{
	private static final Log logger = LogFactory.getLog(HibernateObjectTest.class);
	
	@Test
	public void testBase64(){
		this.startTransaction();
		Session session=this.getCurrentSession();
		
		final corner.demo.model.one.A a1=new corner.demo.model.one.A();
		a1.setName("Base 64 测试");
		a1.setBlobData("test".getBytes());
		session.saveOrUpdate(a1);
		this.commitTransaction();
		System.out.println(XStreamDelegate.toJSON(a1));
	}
	
//	@Test
	public void testA(){
		logger.debug("----------------------对A的操作--------------");
		//保存A
		this.startTransaction();
		Session session=this.getCurrentSession();
		final corner.demo.model.one2many.A a1=new corner.demo.model.one2many.A();
		a1.setName("哈哈");
		System.out.println(XStreamDelegate.toJSON(a1));
		session.save(a1);
		this.commitTransaction();
		
		
		//保存B,同时增加关系
		this.startTransaction();
		session=this.getCurrentSession();
		corner.demo.model.one2many.A a = (corner.demo.model.one2many.A) session.load(corner.demo.model.one2many.A.class, a1.getId());
		
		 System.out.println(XStreamDelegate.toJSON(a));
        
		this.commitTransaction();
	}
}
