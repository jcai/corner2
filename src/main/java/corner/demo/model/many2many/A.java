package corner.demo.model.many2many;

import java.util.Set;

import corner.demo.model.AbstractModel;

/**
 *
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="many2manyA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractModel{
	/**
	 *
	 */
	private static final long serialVersionUID = -6860336854997245592L;
	/**
	 * 和B对象之间的many-2-many的关联
	 * @hibernate.set cascade="none" table="AB" lazy="true"
	 * @hibernate.key column="A"
	 * @hibernate.many-to-many class="corner.demo.model.many2many.B" column="B"
	 */
	private Set<B> bs;

	/**
	 * @return Returns the bs.
	 */
	public Set<B> getBs() {
		return bs;
	}

	/**
	 * @param bs The bs to set.
	 */
	public void setBs(Set<B> bs) {
		this.bs = bs;
	}
}
