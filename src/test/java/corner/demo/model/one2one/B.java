//==============================================================================
//file :        B.java
//
//last change:	date:       $Date:2006-06-21 06:18:45Z $
//           	by:         $Author:jcai $
//           	revision:   $Revision:1196 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2one;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision:1196 $
 * @since 0.5.2
 * @hibernate.class table="one2oneB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class B extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2778316930848407262L;
	
	/**
	 * 和A的一对一关联
	 * @hibernate.one-to-one  constrained="true"
	 */
	
	private A a;

	/**
	 * @return Returns the a.
	 */
	@OneToOne
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
