package corner.demo.model.wap.one;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import corner.demo.model.AbstractModel;
import corner.orm.hibernate.v3.MatrixRow;
import corner.service.svn.IVersionable;

/**
 *
 * @author wlh
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="onewapA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="onewapA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WapA extends AbstractModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -1296703401706863951L;

	/**
	 * @hibernate.property type="corner.orm.hibernate.v3.VectorType"
	 */
	
	private String password;
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
