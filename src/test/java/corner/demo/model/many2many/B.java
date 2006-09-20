package corner.demo.model.many2many;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 * many to many 的实验类
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="many2manyB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="many2manyB")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
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
	
	private List<A> as;

	/**
	 * @return Returns the as.
	 */
	@ManyToMany
	@JoinTable(name="A_B",joinColumns={@JoinColumn(name="B_ID")},
	        inverseJoinColumns={@JoinColumn(name="A_ID")})
	public List<A> getAs() {
		return as;
	}

	/**
	 * @param as The as to set.
	 */
	public void setAs(List<A> as) {
		this.as = as;
	}


}
