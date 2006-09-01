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
	 * 中文名称
	 * <p>用于检索和显示时候使用</p>
	 * @hibernate.property
	 */
	private String cnName;
	
	/**
	 * 和AB对象之间的one-2-many的关联
	 * @hibernate.set inverse="true" cascade="all-delete-orphan"
	 * @hibernate.key column="BId"
	 * @hibernate.one-to-many class="corner.demo.model.many2many2.AB"
	 */
	private Set<AB> abs;
	/**
	 * @return Returns the abs.
	 */
	public Set<AB> getAbs() {
		return abs;
	}
	/**
	 * @param abs The abs to set.
	 */
	public void setAbs(Set<AB> abs) {
		this.abs = abs;
	}
	/**
	 * @return Returns the cnName.
	 */
	public String getCnName() {
		return cnName;
	}
	/**
	 * @param cnName The cnName to set.
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
