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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import corner.demo.model.one2many.A;
import corner.service.EntityService;
import corner.test.AbstractTestCase;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class HibernateObjectTest extends AbstractTestCase{
	private static final Log logger = LogFactory.getLog(HibernateObjectTest.class);
	@Test
	public void testA(){
		logger.debug("----------------------对A的操作--------------");
		//保存A
		this.startTransaction();
		Session session=this.getCurrentSession();
		final A a1=new A();
		a1.setName("哈哈");
		System.out.println(XStreamDelegate.toJSON(a1));
		session.save(a1);
		this.commitTransaction();
		
		
		//保存B,同时增加关系
		this.startTransaction();
		session=this.getCurrentSession();
		A a = (A) session.load(A.class, a1.getId());
		
		 System.out.println(XStreamDelegate.toJSON(a));
        
		this.commitTransaction();
	}
}
