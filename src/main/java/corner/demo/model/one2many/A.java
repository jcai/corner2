//==============================================================================
//file :        A.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2many;

import java.util.Set;

import corner.demo.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 * @hibernate.class table="one2manyA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractModel{

	/**
	 * A和B的一对多关联
	 *
	 * @hibernate.set cascade="none"  lazy="true"
	 * @hibernate.key column="A"
	 * @hibernate.one-to-many class="corner.demo.model.one2many.B"
	 */
	private Set<B> bs;

	/**
	 * @return Returns the bs.
	 */
	public Set<B> getBs() {
		return bs;
	}

	/**
	 * @param bs The bs to set.
	 */
	public void setBs(Set<B> bs) {
		this.bs = bs;
	}

}
