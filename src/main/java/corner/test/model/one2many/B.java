package corner.test.model.one2many;

import corner.test.model.AbstractModel;

/**
 *
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 * @hibernate.class table="one2manyB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class B extends AbstractModel{
	/**
	 * B和A的many-to-one的关联。
	 * @hibernate.many-to-one  column="A"
	 */
	private A a;

	/**
	 * @return Returns the a.
	 */
	public A getA() {
		return a;
	}

	/**
	 * @param a The a to set.
	 */
	public void setA(A a) {
		this.a = a;
	}
}
