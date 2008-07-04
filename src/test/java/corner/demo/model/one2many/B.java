package corner.demo.model.one2many;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 *
 * @author jcai
 * @version $Revision:1196 $
 * @since 0.5.2
 * @hibernate.class table="one2manyB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="one2manyB")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class B extends AbstractModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1512980745076899710L;
	
	private A a;
	
	/**
	 * @hibernate.property
	 */
	private String sex;
	
	/**
	 * @hibernate.property
	 */
	private String address;

	/**
	 * @return Returns the a.
	 */
	@ManyToOne
	public A getA() {
		return a;
	}

	/**
	 * @param a The a to set.
	 */
	public void setA(A a) {
		this.a = a;
	}

	/**
	 * @return Returns the sex.
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex The sex to set.
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
