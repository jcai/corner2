package corner.demo.model.many2many;

import java.util.Set;

import corner.demo.model.AbstractModel;

/**
 * many to many 的实验类
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="many2manyB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" schema="many2many"
 */
public class B extends AbstractModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -8263113784790334820L;
	/**
	 * 和A对象之间的many-2-many的关联
	 * @hibernate.set cascade="none" table="AB" lazy="true"
	 * @hibernate.key column="B"
	 * @hibernate.many-to-many class="corner.demo.model.many2many.A" column="A"
	 */
	private Set<A> as;

	/**
	 * @return Returns the as.
	 */
	public Set<A> getAs() {
		return as;
	}

	/**
	 * @param as The as to set.
	 */
	public void setAs(Set<A> as) {
		this.as = as;
	}


}
