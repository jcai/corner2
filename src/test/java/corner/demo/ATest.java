package corner.demo;

import java.util.Vector;

import org.hibernate.Session;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.test.AbstractTestCase;

public class ATest extends AbstractTestCase {
	@Test
	public void testVectorType() {
		final A a=new A();
		Vector<String> v=new Vector<String>();
		v.add("test");
		v.add("test2");
		a.setColors(v);
		
		this.startTransaction();
		Session session=this.getCurrentSession();
		session.save(a);
		this.commitTransaction();
		
		
		
		this.startTransaction();
		session=this.getCurrentSession();
		A a1=(A) session.load(A.class,a.getId());
		assertNotNull(a1.getColors());
		assertEquals(a1.getColors().size(),2);
		this.commitTransaction();
	}
}
