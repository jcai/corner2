package corner.demo;

import java.sql.SQLException;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


import corner.demo.model.one.A;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.test.AbstractTestCase;
import org.testng.annotations.Test;
@Test
public class ATest extends AbstractTestCase {
	public void testVectorType() {
		final A a=new A();
		Vector<String> v=new Vector<String>();
		v.add("test");
		v.add("test2");
		a.setColors(v);
		this.getEntityService().saveEntity(a);
		((HibernateObjectRelativeUtils) this.getEntityService().getObjectRelativeUtils()).getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				A a1=(A) session.load(A.class,a.getId());
				assertNotNull(a1.getColors());
				assertEquals(a1.getColors().size(),2);
				return null;
			}});
		

	}
}
