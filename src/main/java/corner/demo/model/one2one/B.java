//==============================================================================
//file :        B.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2one;

import corner.demo.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 * @hibernate.class table="one2oneB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class B extends AbstractModel {
	/**
	 * @hibernate.id generator-class="foreign"
	 * @hibernate.generator-param name="property" value="a"
	 */
	private String id;
	/**
	 * 和A的一对一关联
	 * @hibernate.one-to-one  constrained="true"
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

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
}
