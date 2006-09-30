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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import corner.demo.model.AbstractModel;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 * @hibernate.class table="many2many2A"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" 
 */
@Entity(name="many2many2A")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class A extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3158820663328170073L;
	/**
	 * 和AB对象之间的one-2-many的关联
	 * @hibernate.set inverse="true" cascade="all-delete-orphan"
	 * @hibernate.key column="AId"
	 * @hibernate.one-to-many class="corner.demo.model.many2many2.AB"
	 */
	private Set<AB> abs;
	/**
	 * @return Returns the abs.
	 */
	@OneToMany(mappedBy="a")
	@Column(length=32, columnDefinition="char(32)")
	@OnDelete(action=OnDeleteAction.CASCADE)
	public Set<AB> getAbs() {
		return abs;
	}
	/**
	 * @param abs The abs to set.
	 */
	public void setAbs(Set<AB> abs) {
		this.abs = abs;
	}
}
