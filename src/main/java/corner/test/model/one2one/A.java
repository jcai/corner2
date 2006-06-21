//==============================================================================
//file :        A.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.test.model.one2one;

import corner.test.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 * @hibernate.class table="one2oneA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractModel {

	/**
	 * 和B的一对一关联。
	 * @hibernate.one-to-one
	 */
	private B b;

	/**
	 * @return Returns the b.
	 */
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
