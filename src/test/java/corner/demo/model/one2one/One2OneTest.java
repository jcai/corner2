package corner.demo.model.one2one;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import corner.demo.model.one2one.A;
import corner.demo.model.one2one.B;
import corner.test.AbstractTestCase;

/**
 * 提供了one-to-one的映射。
 * <p>one-to-one哦那的映射通常为两种。
 * 一种是主建的映射，两个实体共用一个实体。(适用于一个为主，一个为辅)
 * 另外一种是通过一个不重复的外键实现关联。（适用于两个几乎是平级的）
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 */
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
