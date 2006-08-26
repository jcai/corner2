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

package corner.orm.tapestry.service.blob;

import org.apache.hivemind.impl.BaseLocatable;

/**
 * 根据class的名称来提供blob服务.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class NameBlobProviderContribution extends BaseLocatable {
	private String name;
	private String providerClassName;
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the providerClassName.
	 */
	public String getProviderClassName() {
		return providerClassName;
	}
	/**
	 * @param providerClassName The providerClassName to set.
	 */
	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}
	
}
