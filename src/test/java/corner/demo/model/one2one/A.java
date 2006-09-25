//==============================================================================
//file :        A.java
//
//last change:	date:       $Date:2006-06-21 06:18:45Z $
//           	by:         $Author:jcai $
//           	revision:   $Revision:1196 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2one;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision:1196 $
 * @since 0.5.2
 * @hibernate.class table="one2oneA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="one2oneA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class A extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1825262741332301653L;
	/**
	 * 和B的一对一关联。
	 * @hibernate.one-to-one
	 */
	 
	private B b;

	/**
	 * @return Returns the b.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="b")
	public B getB() {
		return b;
	}

	/**
	 * @param b The b to set.
	 */
	public void setB(B b) {
		this.b = b;
	}
}
