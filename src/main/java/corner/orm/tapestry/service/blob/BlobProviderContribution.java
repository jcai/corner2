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
 * 
 * 对blob的提供者的配置说明.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class BlobProviderContribution extends BaseLocatable {
	private String name;
	private IBlobProvider provider;
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
	 * @return Returns the provider.
	 */
	public IBlobProvider getProvider() {
		return provider;
	}
	/**
	 * @param provider The provider to set.
	 */
	public void setProvider(IBlobProvider provider) {
		this.provider = provider;
	}
}
