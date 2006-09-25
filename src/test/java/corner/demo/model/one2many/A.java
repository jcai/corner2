//==============================================================================
//file :        A.java
//
//last change:	date:       $Date:2006-06-21 06:18:45Z $
//           	by:         $Author:jcai $
//           	revision:   $Revision:1196 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2many;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import corner.demo.model.AbstractModel;

/**
 * @author jcai
 * @version $Revision:1196 $
 * @since 0.5.2
 * @hibernate.class table="one2manyA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="one2manyA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class A extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6581444354115534730L;
	/**
	 * A和B的一对多关联
	 *
	 * @hibernate.set cascade="none"  lazy="true"
	 * @hibernate.key column="A"
	 * @hibernate.one-to-many class="corner.demo.model.one2many.B"
	 */
	
	private List<B> bs;

	/**
	 * @return Returns the bs.
	 */
	@OneToMany(mappedBy="a")
	@JoinColumn(name="A")
	@OnDelete(action=OnDeleteAction.CASCADE)
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
