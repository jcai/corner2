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
		//保存A
		this.startTransaction();
		Session session=this.getCurrentSession();
		final A a1=new A();
		session.save(a1);
		this.commitTransaction();
		
		logger.debug("保存B,同时保存两者之间的关系");
		//保存B,同时增加关系
		this.startTransaction();
		session=this.getCurrentSession();
		A a = (A) session.load(A.class, a1.getId());
		B b2=new B();
		b2.setA(a);
		session.saveOrUpdate(b2);
		this.commitTransaction();
		
		logger.debug("确认是否增加关系成功");
		//确认是否增加成功
		this.startTransaction();
		session=this.getCurrentSession();
		A tmp=(A) session.load(A.class,a1.getId());
		assertEquals(1,tmp.getBs().size());
		this.commitTransaction();

		logger.debug("删除A,同时删除A对应的many部分");
		//删除A,同时也把one2many部分的B删除
		this.startTransaction();
		session=this.getCurrentSession();
		tmp=(A) session.load(A.class,a1.getId());
		session.delete(tmp);
		this.commitTransaction();
		
	}
}
