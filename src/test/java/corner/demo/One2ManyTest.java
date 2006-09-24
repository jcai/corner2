package corner.demo;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;
import corner.demo.model.one2many.B;
import corner.test.AbstractTestCase;
@Test
public class One2ManyTest extends AbstractTestCase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(One2ManyTest.class);

	public void testA(){
		logger.debug("----------------------对A的操作--------------");

		final A a1=new A();
		this.getEntityService().saveEntity(a1);
		final B b1=new B();
		this.getEntityService().saveEntity(b1);
		logger.debug("通过A来增加A和B之间的关系");
		((HibernateDaoSupport) this.getEntityService().getObjectRelativeUtils())
				.getHibernateTemplate().execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						A a = (A) session.load(A.class, a1.getId());
						a.getBs().add(b1);
						session.saveOrUpdate(a);
						return null;
					}
				});
		logger.debug("删除A来删除关系!");
		this.getEntityService().deleteEntities(a1);
	}
	public void testB(){
		logger.debug("----------------------对B的操作--------------");
		final A a1=new A();
		this.getEntityService().saveEntity(a1);
		final B b1=new B();
		this.getEntityService().saveEntity(b1);
		logger.debug("通过B来增加A和B之间的关系"); //此为推荐的方法。
		((HibernateDaoSupport) this.getEntityService().getObjectRelativeUtils())
				.getHibernateTemplate().execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						B b = (B) session.load(B.class, b1.getId());
						b.setA(a1);
						session.saveOrUpdate(b);
						return null;
					}
				});
		logger.debug("删除A来删除关系!");
		this.getEntityService().deleteEntities(b1);

	}
}
