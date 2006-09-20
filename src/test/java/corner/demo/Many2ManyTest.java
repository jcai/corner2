package corner.demo;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.demo.model.many2many.A;
import corner.demo.model.many2many.B;
import corner.test.AbstractTestCase;

/**
 * 对many2many的测试.
 * <p>
 *  many-to-many的关系适用于虽然是many-to-many但是相对来说，有一端是少些。
 *  譬如：
 *   用户(A)和组(B)。用户(A)相对于组(B)来说是多,所以通常用A来对A和B之间的关系进行增加，以及删除。
 * </p>
 * <p>同时在many-to-many的时候cascade对关系的维护没有任何实际的意义。
 * @author jcai
 * @version $Revision:1196 $
 * @since 0.5.2
 *
 *
 */
import org.testng.annotations.Test;
@Test
public class Many2ManyTest extends AbstractTestCase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(Many2ManyTest.class);



	

	public void testB() {
		logger.debug("----------------测试通过对B对象的操作-------------------");

		
		logger.debug("插入A对象");
		final A a1 = new A();
		this.getEntityService().saveEntity(a1);

		logger.debug("插入B对象");
		final B b1 = new B();
		this.getEntityService().saveEntity(b1);

		logger.debug("通过B来增加A和B之间的关系");
		((HibernateDaoSupport) this.getEntityService().getObjectRelativeUtils())
				.getHibernateTemplate().execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						B b = (B) session.load(B.class, b1.getId());
						b.getAs().add(a1);
						session.saveOrUpdate(b);
						session.flush();
						return null;
					}
				});

		logger.debug("删除B，同时把A和B关联的实体进行删除！");
		b1.delete();
	}
	public void testA() {
		logger.debug("-----------------测试通过对A对象的操作，来维护关系---------------");

		logger.debug("插入A对象");
		final A a1 = new A();
		a1.save();

		logger.debug("插入B对象");
		final B b1 = new B();
		b1.save();

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

		logger.debug("删除A，同时把A和B关联的实体进行删除！");
		a1.delete();
		assertEquals(null,this.getEntityService().getEntity(A.class, a1.getId()));
	}

}
