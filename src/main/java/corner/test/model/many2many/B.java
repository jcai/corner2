package corner.test.model.many2many;

import java.util.Set;

import corner.test.model.AbstractModel;

/**
 * many to many 的实验类
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="many2manyB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class B extends AbstractModel{

	/**
	 * 和A对象之间的many-2-many的关联
	 * @hibernate.set cascade="none" table="AB" lazy="true"
	 * @hibernate.key column="B"
	 * @hibernate.many-to-many class="corner.test.model.many2many.A" column="A"
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
