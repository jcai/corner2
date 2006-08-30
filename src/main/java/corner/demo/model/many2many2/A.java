//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.model.many2many2;

import java.util.Set;

import corner.demo.model.AbstractModel;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 * @hibernate.class table="many2many2A"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" 
 */
public class A extends AbstractModel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3158820663328170073L;
	/**
	 * 和B对象之间的many-2-many的关联
	 * @hibernate.set inverse="true" cascade="all-delete-orphan"
	 * @hibernate.key column="A"
	 * @hibernate.one-to-many class="corner.demo.model.many2many2.B"
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
