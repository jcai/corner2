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
 *
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="many2manyA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" 
 */
@Entity(name="many2manyA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
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
	
	private List<B> bs;

	/**
	 * @return Returns the bs.
	 */
	@ManyToMany
	@JoinTable(name="A_B",joinColumns={@JoinColumn(name="A_ID")},
	        inverseJoinColumns={@JoinColumn(name="B_ID")})
	
	public List<B> getBs() {
		return bs;
	}

	/**
	 * @param bs The bs to set.
	 */
	public void setBs(List<B> bs) {
		this.bs = bs;
	}
}
