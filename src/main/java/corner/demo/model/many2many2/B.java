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
 * @hibernate.class table="many2many2B"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class B extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7691586213111454181L;
	/**
	 * 和A对象之间的one-2-many的关联
	 * @hibernate.set inverse="true" cascade="all-delete-orphan"
	 * @hibernate.key column="B"
	 * @hibernate.one-to-many class="corner.demo.model.many2many2.A"
	 */
	private Set<A> as;

	/**
	 * @return Returns the as.
	 */
	public Set<A> getAs() {
		return as;
	}

	/**
	 * @param as The as to set.
	 */
	public void setAs(Set<A> as) {
		this.as = as;
	}
}
