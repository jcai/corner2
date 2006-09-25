package corner.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.testng.annotations.Test;

import corner.demo.model.one2one.A;
import corner.demo.model.one2one.B;
import corner.test.AbstractTestCase;

public class One2OneTest extends AbstractTestCase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(One2OneTest.class);
	@Test
	public void testA() {
		this.startTransaction();
		Session session=this.getCurrentSession();
		final A a1 = new A();
		session.save(a1);
		this.commitTransaction();
		
		this.startTransaction();
		session=this.getCurrentSession();
		final B b1 = new B();
		b1.setA(a1);
		session.save(b1);
		this.commitTransaction();
		

		
		this.startTransaction();
		session=this.getCurrentSession();
		session.delete(a1);
		this.commitTransaction();
		
		//确信B已经被级联删除
		this.startTransaction();
		session=this.getCurrentSession();
		B tmp=(B) session.get(B.class,b1.getId());
		assertNull(tmp);
		this.commitTransaction();
		
		

		//在删除主的那一端时候必须要删除和其主建相同的one。
		//没有找到级联删除的方法。
		
		//hibernate 采用OnDelete方式对其进行删除(产生数据库级别的级联删除)

	}
	

	@Test
	public void testB() {
		this.startTransaction();
		Session session=this.getCurrentSession();
		final A a1 = new A();
		session.save(a1);
		session.flush();
		this.commitTransaction();
		

		
		this.startTransaction();
		session=this.getCurrentSession();
		final B b1 = new B();
		b1.setA(a1);
		session.save(b1);
		this.commitTransaction();
		

		
		this.startTransaction();
		session=this.getCurrentSession();
		session.delete(b1);
		this.commitTransaction();
		
		//确信A没有被删除
		this.startTransaction();
		session=this.getCurrentSession();
		A tmp=(A) session.load(A.class,a1.getId());
		assertNotNull(tmp);
		this.commitTransaction();
		

	}
}
