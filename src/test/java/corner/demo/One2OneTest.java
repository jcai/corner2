package corner.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import corner.demo.model.one2one.A;
import corner.demo.model.one2one.B;
import corner.test.AbstractTestCase;
@Test
public class One2OneTest extends AbstractTestCase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(One2OneTest.class);

	public void testA() {
		final A a1 = new A();
		this.getEntityService().saveEntity(a1);

		final B b1 = new B();
		b1.setA(a1);
		this.getEntityService().saveEntity(b1);

		//在删除主的那一端时候必须要删除和其主建相同的one。
		//没有找到级联删除的方法。
		this.getEntityService().deleteEntities(b1);
		this.getEntityService().deleteEntities(a1);

	}
	public void testB() {
		logger.debug("B---------");
		final A a1 = new A();
		this.getEntityService().saveEntity(a1);

		final B b1 = new B();
		b1.setA(a1);
		this.getEntityService().saveEntity(b1);

		this.getEntityService().deleteEntities(b1);

	}
}
